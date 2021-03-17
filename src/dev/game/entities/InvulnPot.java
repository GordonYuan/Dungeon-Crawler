package dev.game.entities;

import dev.map.Map;
import dev.map.Type;
/**
 * Immunity potion - allows player to run into enemies and kill them instead of dying
 * @author Andy Yu
 *
 */
public class InvulnPot extends FloorItem {

	public InvulnPot(int[] coordinate,Map m) {
		super(coordinate,m,Type.invincibility);
	}

	@Override
	public void giveToPlayer(Player p) {
		p.becomeInvincible();
		this.map.delete(this);
	}

	@Override
	public String imagePath() {
		return "dev/game/assets/invulnPot.png";
	}
}
