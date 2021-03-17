package dev.map;

import dev.game.entities.Entity;

/**
 * Checks to see if a player is on an exit
 *
 */
public class WinExit extends Winall{

	public WinExit(Map curr) {
		super(curr);
	}

	@Override
	public boolean checkWin() {
		for(Entity e:this.getCurrent().getEntities()) {
			if(e.getType() == Type.player) {
				int[] coor = e.getCoordinate();
				Tile[][] tiles = this.getCurrent().getLand();
				if(tiles[coor[0]][coor[1]].getType() == Type.exit) {
					return true;
				}
			}
		}
		return false;
	}

}
