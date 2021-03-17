package dev.game.entities;

import dev.map.*;

/**
 * Treasure which can be collected by the player
 *
 */
public class Treasure extends FloorItem {
	public Treasure(int[] coordinate,Map m) {
		super(coordinate,m, Type.treasure);
	}
	
	@Override
	public void giveToPlayer(Player p) {
		this.map.delete(this);
	}
	
	@Override
	public String imagePath() {
		return "dev/game/assets/treasure.png";
	}
}