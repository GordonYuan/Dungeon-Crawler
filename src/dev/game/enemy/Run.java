package dev.game.enemy;

import dev.game.entities.Player;

public class Run implements MoveBehaviour {
	/**
	 * Moves away from the player on the longest axis x or y
	 */
	@Override
	public int [] decideDestination(Enemy enemy) {
		Player player = enemy.getPlayer();
		int [] coordinate = enemy.getCoordinate();
		int [] playerLoc = player.getCoordinate();
		int [] nextMove = {-1,0};
		
		int xDist = playerLoc[0] - coordinate[0];
		int yDist = playerLoc[1] - coordinate[1];
		if (Math.abs(xDist) >= Math.abs(yDist)) {
			if (xDist > 0) { // player is to the right of enemy
				nextMove[0] = coordinate[0] - 1;
				nextMove[1] = coordinate[1];
			} else { // player is to the left of enemy
				nextMove[0] = coordinate[0] + 1;
				nextMove[1] = coordinate[1];
			}
		} else {
			if (yDist > 0) { // player is above enemy
				nextMove[0] = coordinate[0];
				nextMove[1] = coordinate[1] - 1;
			} else { // player is below enemy
				nextMove[0] = coordinate[0];
				nextMove[1] = coordinate[1] + 1;
			}
		}
		if (nextMove[0] == -1) {
			return enemy.getCoordinate();
		}
		return nextMove;
	}
}