package dev.game.enemy;

public interface MoveBehaviour {
	/**
	 * Decides the desired destination of an enemy
	 * @param enemy which is deciding move
	 * @return desired location to reach in the long run
	 */
	int [] decideDestination(Enemy enemy);
}