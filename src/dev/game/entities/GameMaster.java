package dev.game.entities;

import dev.map.Map;
import dev.map.Type;
/**
 * Class for people who don't feel like using their brains very much
 * @author Andy Yu
 *
 */


public class GameMaster extends Player {

	public GameMaster(int[] coordinate, Map m) {
		super(coordinate, m);
		this.type = Type.gamemaster;
	}

	@Override
	public void die() {
		map.killEnemy(this.getCoordinate());
		System.out.println("Foolish creatures, I cannot be killed!");
	}

	@Override
	public String imagePath() {
		return "dev/game/assets/angel.png";
	}
	
}
