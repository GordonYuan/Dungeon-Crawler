package dev.game.entities;

import dev.map.Map;
import dev.map.Type;
/**
 * Can be swung at enemies
 * @author Andy Yu
 *
 */
public class Sword extends FloorItem {
	
	private int durability = 5;

	public Sword(int[] coordinate,Map m) {
		super(coordinate, m,Type.sword);
	}

	@Override
	public void giveToPlayer(Player p) {
		p.setSword(this);
		this.map.delete(this);
	}

	public int getDurability() {
		return durability;
	}

	public void lowerDurability() {
		this.durability--;
	}

	@Override
	public String imagePath() {
		return "dev/game/assets/sword.png";

	}
}
