package dev.game.enemy;

import dev.game.entities.Player;
import dev.map.*;

/**
 * Chases after the player
 *
 */
public class Hunter extends Enemy {
	private int houndsAttached = 0;
	
	public Hunter(int[] coordinate, Map handler, Player player) {
		super(coordinate, handler, Type.hunter, player, 2);
		moveBehaviour = new Aggro();
	}
	
	/**
	 * Set itself as the owner of a hound
	 */
	public void attachHound(Hound h) {
		h.setOwner(this);
		houndsAttached++;
	}

	public void detatchHound(Hound h) {
		h.setOwner(null);
	}
	
	@Override
	public void checkMoveBehaviour() {
		Player player = this.getPlayer();
		if (player.isImmune()) {
			this.moveBehaviour = new Run();
		} else {
			moveBehaviour = new Aggro();
		}
	}


	@Override
	public String imagePath() {
		return "dev/game/assets/hunter.png";
	}
	
	public int getHoundsAttached() {
		return houndsAttached;
	}
}