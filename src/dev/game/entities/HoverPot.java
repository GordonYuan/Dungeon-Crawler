package dev.game.entities;

import dev.map.Map;
import dev.map.Type;
/**
 * When walked over allows player to fly over pits without dying
 * @author Andy Yu
 *
 */
public class HoverPot extends FloorItem {

	public HoverPot(int[] coordinate,Map m) {
		super(coordinate,m,Type.hover);
	}

	@Override
	public void giveToPlayer(Player p) {
		p.becomeHovering();
		this.map.delete(this);
	}
	

	@Override
	public String imagePath() {
		return "dev/game/assets/hoverpot.png";

	}

}
