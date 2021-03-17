package dev.game.enemy;

import dev.game.entities.Player;

public class Aggro implements MoveBehaviour {
	/**
	 * This function returns the enemy's desired location as the player's location.
	 * It is the base movement behaviour of the hunter
	 */
	@Override
	public int [] decideDestination(Enemy enemy) {
		Player player = enemy.getPlayer();
		return player.getCoordinate();
	}
}