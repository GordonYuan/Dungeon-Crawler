package dev.game.entities;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

import dev.game.enemy.Enemy;
import dev.map.*;
import dev.map.Map;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Class for player
 * @author Andy Yu
 * Constructor requires a list of enemies to be passed in 
 */

public class Player extends Creature {
	ArrayList<FloorItem> Inventory;
	//sint nTreasures;
	private Instant lastMove;
	private Hovering hovering;
	private Immune immune;
	private MapEditingMode mapEditing;
	private int bombs=0;
	private int arrows=0;
	private Key key=null;
	private Sword sword=null;
	private boolean isAlive = true;

	public boolean isAlive() {
		return isAlive;
	}
	
	public int getBombs() {
		return bombs;
	}
	public void giveBomb() {
		this.bombs++;
	}
	public void giveBombs(int num) {
		this.bombs = this.bombs + num;
	}
	public int getArrows() {
		return arrows;
	}
	public void giveArrow() {
		this.arrows++;
	}
	/**
	 * Adds on an integer to a player's number of arrows
	 * @param num The integer to be added, can be negative
	 */
	public void giveArrows(int num) {
		this.arrows = this.arrows + num;
	}
	public Key getKey() {
		return key;
	}
	/**
	 * Gets the player's key color
	 * @return The player's key color if they have a key, -1 otherwise
	 */
	public int getColor() {
		try {
			return key.getColor();
		}
		catch (NullPointerException n) {
			return -1;
		}
	}
	/**
	 * Gives a key to a player
	 * @param key - The key to give to the player
	 * @return True if successfully given, false if they already had a key
	 */
	public boolean setKey(Key key) {
		if (this.key==null) {
			this.key = key;
			return true;
		}
		return false;
	}
	public Sword getSword() {
		return sword;
	}
	/**
	 * Overrides a player's sword as per specs
	 * @param sword - The sword to give to the player
	 */
	public void setSword(Sword sword) {
		this.sword = sword;
	}

	public Player(int[] coordinate,Map m) {
		super(coordinate,m,Type.player);
		this.Inventory = new ArrayList<FloorItem>();
		//this.nTreasures = 0;
		this.hovering = new Hovering();
		this.immune = new Immune();
		this.mapEditing = new MapEditingMode();
		this.lastMove = Instant.now();
		this.map = m;
	}
	/**
	 * @return How long the player should stay immune for
	 */
	public long invulnDurationRemaining() {
		return immune.immuneTime();
	}
	/**
	 * Checks for collision and invulnerability timer
	 */
	public void tick () {
		this.checkInvuln();
		this.collisionDetection();
	}
	/**
	 * Check to see if the player's immunity should continue - if not it deactivates
	 */
	public void checkInvuln() {
		if (this.isImmune()) {
			if (this.immune.shouldDeactivate()) {
				this.immune.deactivate();
			}
		}
	}
	@Override
	public boolean GameMove(int [] coordinate) {
		Duration between = Duration.between(this.lastMove, Instant.now());
		long ms = between.toMillis();
		if (ms<250) return false;
		if (this.isAdjacent(coordinate)!=true) {
			return false;
		}
		else { 
			try {
				super.move(coordinate);
				this.lastMove = Instant.now();
				return true;
			}
			catch (IllegalArgumentException e) {
				return false;
			}
		}
	}
	/**
	 * Overriden terrain check to account for boulder movement
	 */
	@Override
	public boolean terrainCheck(int[] coordinate) {
		if (this.map.isOnMap(coordinate)==false) {return false;}
		//First check if the coordinate is a door
		if (this.map.openDoor(coordinate, this)==true) {
			//If it is a door, open door will automatically open the door
			//We want player to move into the open door so pass the terrain check
			return true;
		}
		//Then perform boulder check
		else if(this.map.isBoulder(coordinate)!=null) {
			//Attempt to move the boulder
			Boulder b = this.map.isBoulder(coordinate);
			if (!b.pushBoulder(this)) { //Try to push the boulder
				return false; //Something in the way of boulder, it can't be pushed
			}
			else {
				return true; //Successfully pushed boulder as our move
			}
		}
		//Finally, we check for other types of immovable terrain
		return (super.terrainCheck(coordinate));
	}
	public Instant getLastMove() {
		return lastMove;
	}
	public boolean isImmune() {
		return this.immune.isActivated();
	}
	public boolean isHovering() {
		return this.hovering.isActivated();
	}
	public boolean isMapEditing() {
		return this.mapEditing.isActivated();
	}
	public void becomeInvincible() {
		this.immune.activate();
	}
	public void becomeHovering() {
		this.hovering.activate();
	}
	public void enterMapEditMode() {
		this.mapEditing.activate();
	}
	public void enterPlayMode() {
		this.mapEditing.deactivate();
	}
	/**
	 * Shoot an arrow in a target direction (directions are reversed from actual keybinds)
	 * @param direction 'a' for right, 'w' for down, 's' for up, 'd' for left
	 */
	public void shootArrow(char direction) {
		if (this.arrows==0) return;
		int i;
		int [] coordinate = new int[2];
		coordinate[0] = this.getCoordinate()[0];
		coordinate[1] = this.getCoordinate()[1];
		if (direction=='w') { //Shoot an arrow down
			for (i=-1;this.getCoordinate()[1]+i>=0;i--) {
				coordinate[1] = this.getCoordinate()[1] + i;
				if(this.shootArrowHelper(coordinate)) {
					break;
				}
			}
		}
		else if (direction=='a') { //Shoot an arrow left (x coord decreases, y coord stays same)
			for (i=-1;this.getCoordinate()[0]+i>=0;i--) {
				coordinate[0] = this.getCoordinate()[0] + i;
				if(this.shootArrowHelper(coordinate)) {break;}
			}
		}
		else if (direction=='s') { //Shoot an arrow up (x coord stays same, y coord increases)
			for (i=1;this.getCoordinate()[1]+i<this.map.getHeight();i++) {
				coordinate[1] = this.getCoordinate()[1] + i;
				if(this.shootArrowHelper(coordinate)) {
					break;
				}
			}
		}
		else if (direction=='d') { //Shoot an arrow up (y coord stays same, x coord increases)
			for (i=1;this.getCoordinate()[0]+i<this.map.getWidth();i++) {
				coordinate[0] = this.getCoordinate()[0] + i;
				if(this.shootArrowHelper(coordinate)) {break;}
			}
		}
		this.arrows--;
	}
	/**
	 * Helper method for shooting arrows, checks if the arrow should stop at a coordinate
	 * @param coordinate - The coordinate to check
	 * @return True if the arrow hits something, false otherwise
	 */
	public boolean shootArrowHelper(int [] coordinate) {
		if (!this.map.isPassable(coordinate)) return true; //Check for wall blocks
		if (this.map.isBoulder(coordinate)!=null) return true; //Check for boulder blocks
		else if (this.map.hasEnemy(coordinate)!=null) { 
			this.map.killEnemy(coordinate); //Check for enemy hits
			return true;
		}
		return false;
	}
	/**
	 * Swings a sword as given by a direction
	 * @param direction The direction to swing the sword in (w down,s up, a left, d right)
	 * These directions are the reverse of actual game controls
	 */
	public void swingSword(char direction) {
		int x = this.getCoordinate()[0];
		int y = this.getCoordinate()[1];
		if (this.sword==null) return;
		switch (direction) {
		case 'w': 
			y--;
			break;
		case 'a': 
			x--;
			break;
		case 's': 
			y++;
			break;
		case 'd': 
			x++;
			break;
		}
		int[] atkCoordinate = {x,y};
		//If there was an enemy on the coordinate, kill the enemy and decrement sword
		if (this.map.killEnemy(atkCoordinate)) {
			this.sword.lowerDurability();
		}
	}
	public void discardKey() {
		this.key=null;
	}
	/**
	 * Called in tick - sees if player should collide into anything, and performs
	 * the appropriate collision action
	 */
	public void collisionDetection() {
		Map map = this.map;
		Enemy e = map.hasEnemy(this.getCoordinate());
		if (e != null) {
			if (this.isImmune()) {
				map.killEnemy(this.getCoordinate());
			}
			else {
				this.die();
				return;
			}
		}
		FloorItem it = map.hasItem(this.getCoordinate());
		if (it!=null) {
			it.giveToPlayer(this);
		}
		if (map.getTileType(this.getCoordinate()) == Type.pit && this.isHovering() == false) {
			this.die();
		}
	}
	/**
	 * Drop a lit bomb on top of the player
	 */
	public void dropBomb () {
		if (this.bombs==0) return;
		LitBomb lBomb = new LitBomb(this.getCoordinate(),this.map);
		this.map.addEntity(lBomb);
		this.bombs--;
	}
	public void die() {
		this.isAlive=false;
	}
	@Override
	public void render(GraphicsContext gc) {
		if (!this.isAlive) {
			Image dead = new Image("dev/game/assets/blood_red.png");
			gc.drawImage(dead, this.getCoordinate()[0]*32, this.getCoordinate()[1]*32);
			return;
		}
		if (this.isImmune()) {
			if (this.invulnDurationRemaining() > Immune.IMMUNITY_TIME/5) {
				Image indicator = new Image("dev/game/assets/invulnStatus.png");

				gc.drawImage(indicator, this.getCoordinate()[0]*32, this.getCoordinate()[1]*32);
			}
			else {  
				Image indicator = new Image("dev/game/assets/invulnStatus1.png");

				gc.drawImage(indicator, this.getCoordinate()[0]*32, this.getCoordinate()[1]*32);
			}
		}
		Image body = new Image(this.imagePath());
		gc.drawImage(body, this.getCoordinate()[0]*32, this.getCoordinate()[1]*32);
		if (this.isHovering()) {
			Image cloud = new Image("dev/game/assets/cloud_black_smoke.png");
			gc.drawImage(cloud, this.getCoordinate()[0]*32, this.getCoordinate()[1]*32);
		}
	}
	
	public String imagePath() {
		return "dev/game/assets/human_new.png";
	}
	
}
