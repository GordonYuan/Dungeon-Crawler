package dev.game.enemy;

import dev.game.entities.*;
import dev.map.*;

/**
 * Hound which tries to position it self to surround you with a hunter, if no hunter it will just 
 * chase the player
 */
public class Hound extends Enemy {
	private Hunter owner;
	
	public Hound(int[] coordinate, Map handler, Player player) {
		super(coordinate, handler, Type.hound, player, 4);
		this.owner = null;
	}
	
	public void setOwner(Hunter hunter) {
		this.owner = hunter;
	}
	
	public Hunter getOwner() {
		return owner;
	}
	
	/**
	 * Try and find a hunter to attach to on the map
	 */
	public void findAHunter() {
		for (Entity e: this.map.getEntities()) {
			if (e instanceof Hunter) {
				if (((Hunter) e).getHoundsAttached() == 0) {
					((Hunter) e).attachHound(this);
					return;
				}
			}
		}
	}
	
	@Override
	public void checkMoveBehaviour() {
		Player player = this.getPlayer();
		if (player.isImmune()) {
			this.moveBehaviour = new Run();
		}
		else if (owner == null) {
			moveBehaviour = new Aggro();
		} else {
			moveBehaviour = new Track();
		}
	}


	@Override
	public String imagePath() {
		return "dev/game/assets/hound.png";
	}
}