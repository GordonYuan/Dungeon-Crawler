package dev.game.entities;

import dev.map.Map;
/**
 * Abstract class for encapsulating items which can be picked up or otherwise sit on the floor
 * @author andyyu
 *
 */

public abstract class FloorItem extends Entity{
	/**
	 * @pre Constructor assumes the incoming item type is actually a basic item, i.e. the client is trying to instantiate the right object class
	 * @param incomingType - The incoming item type
	 */
	public FloorItem(int[] coordinate,Map m,int incomingType) {
		super(coordinate,m,incomingType);
	}
	
	public abstract void giveToPlayer(Player p);

	@Override
	public boolean hasClash(Entity e) {
		if(super.hasClash(e)) return true;
		else {
			if (e instanceof FloorItem) return true;
		}
		return false;
	}

	
	
	
}
