package dev.game.enemy;

import dev.game.entities.Player;
import dev.map.*;

/**
 * Tries to predict where the player is going. Mostly tries to camp key objectives, if there are
 * none left it will simple chase the player
 *
 */
public class Strategist extends Enemy {
	public Strategist(int[] coordinate, Map handler, Player player) {
		super(coordinate, handler, Type.strategist, player, 2);
		moveBehaviour = new Strategic();
	}
	
	@Override
	public void checkMoveBehaviour() {
		Player player = this.getPlayer();
		if (player.isImmune()) {
			this.moveBehaviour = new Run();
		} else {
			moveBehaviour = new Strategic();
		}
	}
	

	@Override
	public String imagePath() {
		return "dev/game/assets/strategist.png";

	}
	
}