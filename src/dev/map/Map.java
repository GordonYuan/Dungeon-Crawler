package dev.map;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.PrimitiveIterator.OfDouble;

import dev.game.enemy.Enemy;
import dev.game.entities.*;
import javafx.scene.canvas.GraphicsContext;
/**
 * Class to store a reference to the overall map 
 *
 */
public class Map {
	private ArrayList<Winall> winconditions;
	private ArrayList<Entity> entities;
	private int height;
	private int width;
	private Tile[][] land;
	String name;
	public Map(int width, int height, String name) {
		super();
		this.height = height;
		this.width = width;
		this.land = new Tile[width][height];
		for(int i=0;i<height;i++) {
			for(int k=0;k<width;k++) {
				this.land[k][i] = new Empty();
			}
		}
		this.entities = new ArrayList<Entity>();
		this.winconditions = new ArrayList<Winall>();
		this.name = name;
		
	}
	/**
	 * Deletes the entity from the entity list and the tile
	 * @param entity
	 */
	public void delete(Entity entity) {
		this.removeEntityFromTile(entity);
		this.entities.remove(entity);
	}
	/**
	 * Checks if there is an item at the coordinate
	 * @param coordinate
	 * @return
	 */
	public FloorItem hasItem(int [] coordinate) {
		return this.land[coordinate[0]][coordinate[1]].hasItem();
	}
	
	/**
	 * Adds the entity to the tile, not to the entity list. Used to relocate entities on the map.
	 * @param entity
	 * @return
	 */
	public boolean addEntityToTile(Entity entity) {
		int x = entity.getCoordinate()[0];
		int y = entity.getCoordinate()[1];
		if (!this.isOnMap(entity.getCoordinate())) return false;
		return getTile(x, y).addEntity(entity);
	}
	/**
	 * Removes entity from its tile. Used to relocate entities on the map.
	 * @param e
	 */
	public void removeEntityFromTile(Entity e) {
		if (!this.isOnMap(e.getCoordinate())) {
			return;
		}
		int x = e.getX();
		int y = e.getY();
		getTile(x,y).removeEntity(e);
	}
	/**
	 * Adds entity to the tile and entity list. Used to add a new entity onto the map.
	 * @param e
	 * @return
	 */
	public boolean addEntity(Entity e) {
		if(this.addEntityToTile(e)) {
			this.entities.add(e);
			return true;
		}
		return false;
	}
	/**
	 * Returns the tile at the coordinate.
	 * @param x
	 * @param y
	 * @return
	 */
	public Tile getTile(int x, int y) {
		return land[x][y];
	}
	/**
	 * Checks to make sure the coordinate is not out of bounds
	 * @param coordinate
	 * @return
	 */
	public boolean isOnMap(int[] coordinate) {
		if (coordinate[0] >= this.width) {return false;}
		if (coordinate[1] >= this.height) {return false;}
		if (coordinate[0] < 0) { return false;}
		if (coordinate[1] < 0) { return false;}
		
		return true;
	}
	/**
	 * Checks if a boulder exists on the coordinate
	 * @param coordinate
	 * @return
	 */
	public Boulder isBoulder(int [] coordinate) {
		if (!this.isOnMap(coordinate)) return null;
		return this.land[coordinate[0]][coordinate[1]].isBoulder();
	}
	/**
	 * Removes the previous tile and its entities. Then, add the new tile at the coordinate.
	 * @param tile
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean addTiles(Tile tile, int x, int y) {
		if(x >= width || y >= height || x<0 || y<0) {
			return false;
		}
		ArrayList<Entity> replaced = land[x][y].getEntities();
		for(Entity item: replaced) {
			entities.remove(item);
		} 
		land[x][y].clear();
		land[x][y] = tile;
		return true;
	}
	/**
	 * Deletes the coordinate and all its entities except for the player which is killed. Used by bomb.
	 * @param coordinate
	 * @return
	 */
	public boolean deleteCoor(int[] coordinate) {
		if (this.isOnMap(coordinate)==false) {
			return false;
		}
		Tile t = this.land[coordinate[0]][coordinate[1]];
		Player p = null;
		ArrayList<Entity> e = t.getEntities();
		for (Entity ent:e) {
			if (ent instanceof Player) { 
				((Player) ent).die();
				p = (Player)ent;
			}
			this.delete(ent);
		}
		land[coordinate[0]][coordinate[1]] = new Empty();
		//Add the now dead player back onto the tile
		try {
			this.addEntity(p);
		}
		catch (NullPointerException exp) {
			//If there isn't a player, no worries 
		}
		return true;
	}
	/**
	 * Add a win condition.
	 * @param condition
	 * @return
	 */
	public boolean addWinconditions(Winall condition) {
		this.winconditions.add(condition);
		return true;
	}
	public ArrayList<Winall> getWinconditions() {
		return winconditions;
	}
	public ArrayList<Entity> getEntities() {
		return entities;
	}
	public int getHeight() {
		return height;
	}
	public int getWidth() {
		return width;
	}
	public Tile[][] getLand() {
		return land;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Tick method for the gameloop to update backend.
	 */
	public void tick() {
		for (Entity e: entities) {
			try {
				e.tick();
			}
			catch(ConcurrentModificationException exp) {
				
			}
		}
	}
	/**
	 * Checks if the tile on the coordinate is passable.
	 */
	public boolean isPassable(int [] coordinate) {
		if (!this.isOnMap(coordinate)) {
			return false;
		}
		int x = coordinate[0];
		int y = coordinate[1];
		return (this.land[x][y].isPassable());
	}
	/**
	 * Opens the door if the player key matches the door color
	 * @param coordinate
	 * @param p
	 * @return
	 */
	public boolean openDoor(int [] coordinate, Player p) {
		if (!this.isOnMap(coordinate)) {
			return false;
		}
		//First check if coordinate is actually a door
		if (!(this.land[coordinate[0]][coordinate[1]] instanceof Door)) {
			return false;
		}
		Door d = (Door)this.land[coordinate[0]][coordinate[1]];
		if (p.getColor() == d.getColor()) { //Open the door if it's actually the right key
			d.setPassable(true);
			p.discardKey();
			return true;
		}
		return false;
	}
	/**
	 * Get the type of the tile
	 * @param coordinate
	 * @return
	 */
	public int getTileType(int[] coordinate) {
		if (!this.isOnMap(coordinate)) {
			return -1;
		}
		return this.land[coordinate[0]][coordinate[1]].getType();
	}
	/**
	 * Checks if the coordinate has an entity
	 * @param coordinate
	 * @return
	 */
	public boolean hasEntity(int[] coordinate) {
		if (!this.isOnMap(coordinate)) {
			return false;
		}
		return getTile(coordinate[0], coordinate[1]).hasEntity();
	}
	/**
	 * Checks if the coordinate has an enemy
	 * @param coordinate
	 * @return
	 */
	public Enemy hasEnemy(int [] coordinate) {
		if (!this.isOnMap(coordinate)) {
			return null;
		}
		return getTile(coordinate[0], coordinate[1]).hasEnemy();
	}
	/**
	 * Checks if the coordinate is a pit
	 * @param coordinate
	 * @return
	 */
	public boolean isPit(int [] coordinate) {
		if (!this.isOnMap(coordinate)) {
			return false;
		}
		return (getTile(coordinate[0], coordinate[1]) instanceof Pit);
	}
	/**
	 * Render method to update front end.
	 * @param gc
	 */
	public void render(GraphicsContext gc) {
		for (int i=0; i < this.getHeight(); i++) {
			for (int j=0; j < this.getWidth(); j++) {
				land[j][i].render(gc, j*32, i*32);
			}
		}
		
		for (Entity e: entities) {
			e.render(gc);
		}
	}

	/**
	 * Kill an enemy on the coordinate if they are on it
	 * @param coordinate
	 * @return
	 */
	public boolean killEnemy(int[] coordinate) {
		if (!this.isOnMap(coordinate)) {
			return false;
		}
		Entity e = this.land[coordinate[0]][coordinate[1]].killEnemy(coordinate);
		if (e!=null) {
			this.entities.remove(e);
			return true;
		}
		return false;
	}
	/**
	 * Clear a boulder at the coordinate
	 * @param coordinate
	 */
	public void clearBoulder(int[] coordinate) {
		if (!this.isOnMap(coordinate)) {
			return;
		}
		Entity e = this.land[coordinate[0]][coordinate[1]].clearBoulder();
		if (e!=null) {
			this.entities.remove(e);
		}
	}
	/**
	 * Kill a player at the coordinate
	 * @param coordinate
	 */
	public void killPlayer(int[] coordinate) {
		if (!this.isOnMap(coordinate)) {
			return;
		}
		Entity e = this.land[coordinate[0]][coordinate[1]].killPlayer();
		if (e!=null && !((Player) e).isAlive()) {
			this.entities.remove(e);
		}
	}
	/**
	 * Destroy a wall at the coordiante
	 * @param coordinate
	 */
	public void clearWalls(int[] coordinate) {
		if (!this.isOnMap(coordinate)) {
			return;
		}
		this.land[coordinate[0]][coordinate[1]].clearWalls();
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("width: "+this.width);
		sb.append("\n");
		sb.append("height: "+this.height);
		sb.append("\n");
		for(int i=0;i<this.getWidth();i++) {
			sb.append("\n");
			for(int k=0;k<this.getHeight();k++) {
				sb.append(land[i][k].getType());
				sb.append(' ');
			}
		}
		return sb.toString();
	}
	public Player getPlayer() {
		for(Entity e:entities) {
			if(e instanceof Player) {
				return (Player) e;
			}
		}
		return null;
	}
	/**
	 * Returns the coordinate of a given tile
	 * @param tile
	 * @return
	 */
	public int[] getTileCoordinate(Tile tile) {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (tile.equals(getTile(x, y))) {
					int [] coordinate = new int [2];
					coordinate[0] = x;
					coordinate[1] = y;
					return coordinate;
				}
			}
		}
		return null;
	}
	/**
	 * Activates a switch at the coordinate
	 * @param coordinate
	 */
	public void activateSwitch(int[] coordinate) {
		if (!this.isOnMap(coordinate)) {
			return;
		}
		Tile t = this.land[coordinate[0]][coordinate[1]];
		if (t instanceof Switcher) {
			Switcher s = (Switcher)t;
			s.activate();
		}
	}
	/**
	 * Deactivates a switch at the coordinate
	 * @param coordinate
	 */
	public void deactivateSwitch(int[] coordinate) {
		if (!this.isOnMap(coordinate)) {
			return;
		}
		Tile t = this.land[coordinate[0]][coordinate[1]];
		if (t instanceof Switcher) {
			Switcher s = (Switcher)t;
			s.deactivate();
		}
	}
	/**
	 * Check if all the win conditions have been fulfilled
	 * @return
	 */
	public boolean checkWin() {
		for (Winall w : winconditions) {
			if (!(w.checkWin())) {
				return false;
			}
		}
		return true;
	}
	/**
	 * Used to set all tiles to be rendered again
	 */
	public void reRender() {
		for(int i=0;i<height;i++) {
			for(int k=0;k<width;k++) {
				this.land[k][i].setShouldRender(true);
			}
		}
	}
	/**
	 * Count the number of doors on the map
	 * @return
	 */
	public int doorCount() {
		int count = 0;
		for(int i=0;i<height;i++) {
			for(int k=0;k<width;k++) {
				if (this.land[k][i].getType() == Type.door) {
					count++;
				}
			}
		} 
		return count;
	}
	/**
	 * Count the number of keys on the map
	 * @return
	 */
	public int keyCount() {
		int count = 0;
		for (Entity e: entities) {
			if (e instanceof Key) {
				count++;
			}
		}
		return count;
	}
	/**
	 * Delete all existing win conditions
	 */
	public void deleteConditions() {
		// TODO Auto-generated method stub
		this.winconditions = new ArrayList<Winall>();
	}
	/**
	 * Initialise a player to be the target for all enemies on the map
	 * @param p
	 */
	public void initialisePlayerTarget(Player p) {
		for (Entity e: entities) {
			if (e instanceof Enemy) {
				((Enemy) e).setPlayer(p);
			}
		}
	}
	
}
