package dev.map;

import java.util.ArrayList;
import java.util.Arrays;


import dev.game.enemy.Enemy;
import dev.game.entities.Arrow;
import dev.game.entities.BigBomb;
import dev.game.entities.Bomb;
import dev.game.entities.Boulder;
import dev.game.entities.Creature;
import dev.game.entities.Entity;
import dev.game.entities.Explosion;
import dev.game.entities.FloorItem;
import dev.game.entities.HoverPot;
import dev.game.entities.InvulnPot;
import dev.game.entities.Key;
import dev.game.entities.LitBomb;
import dev.game.entities.Player;
import dev.game.entities.Sword;
import dev.game.entities.Treasure;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Represents a tile on the map
 *
 */
public abstract class Tile implements staticRenderable{
	protected int type;
	private boolean passable;
	private ArrayList<Entity> entities;
	private boolean shouldRender;
	
	/**
	 * Checks if the tile should render again. Usually happens after something on the tile is updated
	 * @return
	 */
	public boolean isShouldRender() {
		for (Entity e:this.getEntities()) {
			if (e instanceof LitBomb || e instanceof Explosion) {
				return true;
			}
		}
		return shouldRender;
	}

	public void setShouldRender(boolean shouldRender) {
		this.shouldRender = shouldRender;
	}

	public Tile() {
		this.passable = true;
		this.entities = new ArrayList<Entity>();
		this.shouldRender = true;
	}

	public ArrayList<Entity> getEntities() {
		return entities;
	}
	
	public boolean addEntity(Entity entity) {
		if (!this.passable) { return false;}
		for (Entity e:this.entities) {
			if (entity.hasClash(e)) { 
				return false;
			}
		}
		this.entities.add(entity);
		this.shouldRender = true;
		return true;
	}
	/**
	 * Removes all flooritems from a location
	 */
	public void removeFloorItems() {
		ArrayList<Entity> remove = new ArrayList<Entity>();
		for (Entity e:this.entities) {
			if (e instanceof FloorItem) {
				this.shouldRender = true;
				remove.add(e);
			}
		}
		for (Entity r:remove) {
			this.removeEntity(r);
		}
	}
	public void removeCreatures() {
		ArrayList<Entity> remove = new ArrayList<Entity>();
		for (Entity e:this.entities) {
			if (e instanceof Creature) {
				this.shouldRender = true;
				remove.add(e);
			}
		}
		for (Entity r:remove) {
			this.removeEntity(r);
		}
	}
	
	public void removeEntity(Entity e) {
		this.entities.remove(e);
		this.shouldRender = true;
	}
	public void clear() {
		this.entities.clear();
		this.shouldRender = true;
	}
	
	public FloorItem hasItem() {
		for (Entity e:this.entities) {
			if (e instanceof FloorItem) {
				return (FloorItem)e;
			}
		}
		return null;
	}
	
	public boolean hasTreasure() {
		for (Entity e:this.entities) {
			if (e instanceof Treasure) {
				return true;
			}
		}
		return false;
	}
	
	public boolean hasEntity() {
		if (entities.isEmpty()) {
			return false;
		}
		return true;
	}
	
	public Enemy hasEnemy() {
		for (Entity e:this.entities) {
			if (e instanceof Enemy) {
				return (Enemy) e;
			}
		}
		return null;
	}

	public int getType() {
		return type;
	}

	public boolean isPassable() {
		return passable;
	}

	public void setPassable(boolean passable) {
		if (passable!=this.passable) {
			this.shouldRender = true;
		}
		this.passable = passable;
	}


	public Boulder isBoulder() {
		for (Entity e:this.entities) {
			if (e instanceof Boulder) {
				return (Boulder)e;
			}
		}
		return null;
	}

	public Boulder clearBoulder() {
		for (Entity e:this.entities) {
			if (e instanceof Boulder) {
				this.removeEntity(e);
				this.shouldRender = true;
				return (Boulder) e;
			}
		}
		return null;
	}

	public Player killPlayer() {
		for (Entity e:this.entities) {
			if (e instanceof Player) {
				Player p = (Player)e;
				if (p.isImmune()) {
					return null;
				}
				p.die();
				this.shouldRender = true;
				return (Player) e;
			}
		}
		return null;
	}

	public Enemy killEnemy(int[] coordinate) {
		for (Entity e: entities) {
			if (e instanceof Enemy) {
				entities.remove(e);
				this.shouldRender = true;
				return (Enemy)e;
			}
		}
		return null;
	}
	
	@Override
	public void render(GraphicsContext gc, double x, double y) {
		if (!this.shouldRender) return;

		Image door = new Image(this.imagePath());
		gc.drawImage(door, x, y);
		this.shouldRender = false;
	}

	/**
	 * Used to 'destroy' a wall
	 */
	public void clearWalls() {
		this.shouldRender = true;
		this.passable = true;
	}
	
	/**
	 * If the tile contains an important item of some sort
	 */
	public boolean containVitals() {
		for (Entity e : entities) {
			if (e instanceof Sword || e instanceof InvulnPot || e instanceof Bomb
					|| e instanceof Treasure || e instanceof Key || e instanceof Arrow) {
				return true;
			}
		}
		return false;
	}
}
