package dev.game.enemy;

import dev.game.entities.Player;
import dev.map.*;

public class Coward extends Enemy {
	public Coward(int[] coordinate, Map handler, Player player) {
		super(coordinate, handler, Type.coward, player, 1);
	}
	
	@Override
	public void checkMoveBehaviour() {
		Player player = this.getPlayer();
		if (player.isImmune()) {
			this.moveBehaviour = new Run();
		} else if (this.inRange() == true) {// check range
			moveBehaviour = new Run();
		} else {
			moveBehaviour = new Aggro();
		}
	}
	
	/**
	 * Detects if the player is within an area of 2 x 2 around the player
	 * @return true if player is within the area. false if otherwise
	 */
	public Boolean inRange() {
		int [] position = this.getCoordinate();
		int [] coordinate = this.getPlayer().getCoordinate();
		int xDist = Math.abs(coordinate[0] - position[0]);
		int yDist = Math.abs(coordinate[1] - position[1]);
		int distThreshold = 3;
		if (xDist < distThreshold && yDist < distThreshold) {
			return true;
		}
		return false;
	}

	@Override
	public String imagePath() {
		return "dev/game/assets/coward.png";
	}

}