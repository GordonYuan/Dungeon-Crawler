package dev.game.entities;

import dev.map.Map;
import dev.map.Type;
/**
 * Variation of the standard player class
 * The archer class has arrows which pierce through enemies
 * The archer class also starts with 3 arrows
 * @author andyyu
 *
 */

public class Archer extends Player {
	public static final int STARTING_ARROWS = 3;

	public Archer(int[] coordinate, Map m) {
		super(coordinate, m);
		this.giveArrows(Archer.STARTING_ARROWS);
		this.type = Type.archer;
	}
	//We use template method pattern to introduce new arrow behaviours
	@Override
	public boolean shootArrowHelper(int[] coordinate) {
		if (!this.map.isPassable(coordinate)) return true; //Check for wall blocks
		if (this.map.isBoulder(coordinate)!=null) return true; //Check for boulder blocks
		if (this.map.hasEnemy(coordinate)!=null) {  
			this.map.killEnemy(coordinate); //Check for enemy hits
			return false; //But don't actually stop the arrow
		}
		return false;
	}

	public String imagePath() {
		return "dev/game/assets/centaur.png";
	}
}
