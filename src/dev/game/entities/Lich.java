package dev.game.entities;

import java.util.Arrays;

import dev.map.Map;
import dev.map.Type;
/**
 * Magical powers grants one additional life the first time the lich would die
 * The additional life teleports the lich back to the respawn point (which is where it spawned in)
 * If the respawn point is no longer passable, the lich is dead for good
 * @author Andy Yu
 *
 */

public class Lich extends Teleporter {

	private int [] respawn;
	private boolean secondLife;
	public Lich(int[] coordinate, Map m) {
		super(coordinate, m);
		this.respawn = Arrays.copyOf(coordinate, 2);
		this.secondLife = true;
		this.type = Type.lich;
	}
	@Override
	public void die() {
		if (!secondLife) {
			super.die();
		}
		else {
			this.secondLife = false;
			this.Teleport(respawn);
		}
	}
	
	@Override
	public void Teleport(int[] coordinate) {
		if (!this.map.isPassable(coordinate) || this.map.isBoulder(coordinate)!=null) {
			this.die(); //Need a separate check for boulder to make sure boulder is not pushed away
		}
		else {
			this.move(coordinate);
		}
	}
	public String imagePath() {
		return "dev/game/assets/lich.png";
	}
	
	

}
