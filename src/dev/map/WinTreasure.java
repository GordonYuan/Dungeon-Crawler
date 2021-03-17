package dev.map;

import dev.game.entities.Entity;
import dev.game.entities.Treasure;

/**
 * Checks to see if all the treasure has been picked up
 * @author chenjielin
 *
 */
public class WinTreasure extends Winall {
	public WinTreasure(Map curr) {
		super(curr);
	}

	@Override
	public boolean checkWin() {
		for (Entity e: this.current.getEntities()) {
			if (e instanceof Treasure) {
				return false;
			}
		}
		return true;
	}
}