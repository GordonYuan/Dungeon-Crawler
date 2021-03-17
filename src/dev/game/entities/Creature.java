package dev.game.entities;

import dev.map.Map;

/**
 * Abstract class for all in-game creatures
 * @author Andy Yu
 *
 */


public abstract class Creature extends Entity {
	//public boolean alive;

	public Creature(int[] coordinate,Map m,int type) {
		super(coordinate,m,type);
	}
	
	/**
	 * Moves a creature while play mode is on
	 * @param  coordinate The coordinate to move to
	 * Uses superclass move method to check for collision, and if the coordinate is actually on the map
	 * GameMove has the added functionality of checking for adjacency
	 */
	public boolean GameMove(int [] coordinate) {
		if (this.isAdjacent(coordinate)!=true) {
			return false;
		}
		else { 
			try {
				super.move(coordinate);
				return true;
			}
			catch (IllegalArgumentException e) {
				return false;
			}
		}
	}
	@Override
	public boolean hasClash(Entity e) {
		if (super.hasClash(e)) { return true;}
		return false;
	}
	/*
	public boolean isAlive() {
		return alive;
	}
	/*
	public void die() {
		this.alive = false;
	}
	*/
}
