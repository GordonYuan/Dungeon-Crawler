package dev.game.entities;

import dev.map.*;

import java.time.Duration;
import java.time.Instant;
/**
 * Bomb that has been placed and is about to explode
 * @author Andy Yu
 *
 */
public class LitBomb extends FloorItem {

	private final int fuse = 3;
	private Instant setTime;
	protected Instant explodeTime;

	public LitBomb(int[] coordinate,Map m) {
		super(coordinate,m,Type.litbomb);
		this.setTime = Instant.now();
		this.explodeTime = Instant.now().plusSeconds(fuse);
	}

	public int getFuse() {
		return fuse;
	}

	public Instant getSetTime() {
		return setTime;
	}

	public Instant getExplodeTime() {
		return explodeTime;
	}
	public void setExplodeTime(Instant explodeTime) {
		this.explodeTime = explodeTime;
	}

	public void explode() {
		Map m = this.getMap();
		int x = this.getCoordinate()[0];
		int y = this.getCoordinate()[1];
		int[] coordinate = new int[2];

		int dx;
		int dy;
		for (dx=-1;dx<=1;dx++) {
			for (dy=-1;dy<=1;dy++) {
				coordinate[0] = x + dx;
				coordinate[1] = y + dy;
				m.killEnemy(coordinate);
				m.clearBoulder(coordinate);
				m.killPlayer(coordinate);
			}
		}
		m.delete(this);
		Explosion exp = new Explosion(this.getCoordinate(),this.getMap());
		m.addEntity(exp);
	}
	@Override
	public boolean hasClash(Entity e) {
		return false;
	}

	public void tick() {
		if (this.explodeTime.isBefore(Instant.now())) {
			this.explode();
		}
	}
	
	@Override
	public void giveToPlayer(Player p) {
		
	}
	

	@Override
	public String imagePath() {
		Duration between = Duration.between(Instant.now(), this.explodeTime);
		long seconds = between.getSeconds();
		seconds = Math.abs(seconds);
		if (seconds < 3 && seconds >= 2) {
			return "dev/game/assets/bomb_lit_1.png";

		}
		else if (seconds < 2 && seconds >= 1) {
			return "dev/game/assets/bomb_lit_2.png";

		}
		else  {
			return "dev/game/assets/bomb_lit_3.png";

		}
	}
}
