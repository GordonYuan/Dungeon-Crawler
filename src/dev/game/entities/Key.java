package dev.game.entities;

import dev.map.*;
/**
 * Can be used to open doors
 * @author Andy Yu
 *
 */
public class Key extends FloorItem {

	
	private int color;
	public Key(int[] coordinate,Map m,int color) {
		super(coordinate,m, Type.key);
		this.color = color;

	}

	@Override
	public void giveToPlayer(Player p) {
		if (p.getKey() == null) {
			p.setKey(this);
			this.map.delete(this);
		}
	}

	public int getColor() {
		return color;
	}

	@Override
	public String imagePath() {
		return "dev/game/assets/key.png";
	}

}
