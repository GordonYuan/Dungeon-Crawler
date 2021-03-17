package dev.map;

import dev.game.enemy.Enemy;
import dev.game.entities.Entity;

/**
 * Checks to see if the player has killed all the enemies
 *
 */
public class WinKill extends Winall{
	public WinKill(Map curr) {
		super(curr);
	}

	@Override
	public boolean checkWin() {
		for(Entity e:this.getCurrent().getEntities()) {
			if(e instanceof Enemy) {
				return false;
			}
		}
		return true;
	}
}
