package dev.game.entities;

import dev.map.Map;
import dev.map.Type;
/**
 * Bombs that can be placed to blow up surrounding enemies and boulders
 * @author Andy Yu
 *
 */
public class Bomb extends FloorItem {

	public Bomb(int[] coordinate,Map m) {
		super(coordinate,m, Type.bomb);
	}

	@Override
	public void giveToPlayer(Player p) {
		p.giveBomb();
		this.map.delete(this);
	}
	

	@Override
	public String imagePath() {
		return "dev/game/assets/bomb_unlit.png";
	}
}
