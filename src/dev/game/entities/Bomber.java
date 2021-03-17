package dev.game.entities;

import dev.map.Map;
import dev.map.Type;
/**
 * Starts the game with 3 bombs, and drops big bombs which explode in a much larger radius and
 * actually blow up everything
 * @author Andy Yu
 *
 */

public class Bomber extends Player {
	public static final int NUM_BOMBS = 3;

	public Bomber(int[] coordinate, Map m) {
		super(coordinate, m);
		this.giveBombs(Bomber.NUM_BOMBS);
		this.type = Type.bomber;
	}
	
	public String imagePath() {
		return "dev/game/assets/bomber.png";
	}

	@Override
	public void dropBomb() {
		if (this.getBombs()==0) return;
		LitBomb lBomb = new BigBomb(this.getCoordinate(),this.map);
		this.map.addEntity(lBomb);
		this.giveBombs(-1);
	}
	
}
