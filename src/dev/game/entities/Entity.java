package dev.game.entities;
import dev.map.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


import java.util.Arrays;

/**
 * Abstract class for all in-game entities
 * @author Andy Yu
 *
 */

public abstract class Entity implements Renderable {

	private int[] coordinate;
	protected int type;
	protected Map map;
	public Entity(int [] coordinate,Map m,int type) {
		this.type = type;
		this.coordinate = new int[2];
		this.coordinate[0] = coordinate[0];
		this.coordinate[1] = coordinate[1];
		this.map = m;
	}
	
	public int getX() {
		return this.coordinate[0];
	}
	
	public int getY() {
		return this.coordinate[1];
	}
	
	public int[] getCoordinate() {
		return coordinate;
	}
	public Map getMap() {
		return this.map;
	}
	/*
	public Game getGame() { 
		return this.handler.getGame();
	}
	
	public Handler getHandler() {
		return this.handler;
	}
	*/
	public int getType() {
		return type;
	}

	public void tick() {
	}

	/**
	 * Checks to see if destination is immediately to the left,right,above,below or on top of an entity
	 * @param destination - the destination coordinate to check
	 * @return true if adjacent, false otherwise
	 */
	public boolean isAdjacent(int[] destination) {
		int dx = this.coordinate[0] - destination[0];
		int dy = this.coordinate[1] - destination[1];
		if (dx==0 && dy==0) { return true;} //Check if destination is on top of entity
		else if (dx==0 && Math.abs(dy)==1) {return true;} //Check to see if directly above or below
		else if (dy==0 && Math.abs(dx)==1) {return true;} //Check to see if directly to the left or right

		return false;
	}


	/**
	 * Moves an entity
	 * @param coordinate
	 * @throws IllegalArgumentException if coordinate not on map or in a wall
	 */
	public void move(int [] coordinate) throws IllegalArgumentException {
		if (!this.terrainCheck(coordinate)) {
			throw new IllegalArgumentException("Moved into impassable terrain");
		}
		this.map.removeEntityFromTile(this); //remove from old coordinate
		this.coordinate = Arrays.copyOf(coordinate, 2); 
		this.map.addEntityToTile(this); //add to new coordinate
	}
	/**
	 * Template method pattern for checking a coordinate's passability
	 * @param coordinate - coordinate to be checked
	 * @return True if passable, false otherwise
	 * Overriden by player as they need to handle boulders
	 */
	public boolean terrainCheck(int [] coordinate) {
		if (this.map.isOnMap(coordinate)==false) {return false;}
		
		if (this.map.isPit(coordinate)) {
			if (!(this instanceof Player)) {
				this.map.delete(this);
				return true; //Player has collision detection of its own
			}
		}
		return (this.map.isPassable(coordinate) && this.map.isBoulder(coordinate)==null);
		//Checks that the terrain is not a wall tile or a boulder
	}
	public boolean hasClash(Entity e) {
		if (e instanceof Boulder) {
			return true;
		}
		else {
			return false;
		}
	}


	@Override
	public void render(GraphicsContext gc) {
		double x = this.getCoordinate()[0]*32;
		double y = this.getCoordinate()[1]*32;
		Image im = new Image(this.imagePath());
		gc.drawImage(im, x, y);
	}

	public void setCoordinate(int[] coordinate) {
		this.coordinate = coordinate;
	}
	
	
}
