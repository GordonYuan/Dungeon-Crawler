package dev.game.entities;

import java.time.Instant;

import dev.map.Map;
import dev.map.Type;

/**
 * Afterimage left behind when something explodes
 * @author Andy Yu
 *
 */

public class Explosion extends Entity {
	
	private Instant explosionTime;
	public static final int EXPLOSION_SPRITE_DURATION = 500;
	public Explosion(int[] coordinate, Map m) {
		super(coordinate, m, Type.explosion);
		this.explosionTime = Instant.now().plusMillis(Explosion.EXPLOSION_SPRITE_DURATION);
	}


	@Override
	public boolean hasClash(Entity e) {
		return false;
	}

	@Override
	public void tick() {
		if (Instant.now().isBefore(this.explosionTime)) {
			this.map.delete(this);
		}
	}

	@Override
	public String imagePath() {
		return "dev/game/assets/bomb_lit_4.png";
	}
	
	
}
