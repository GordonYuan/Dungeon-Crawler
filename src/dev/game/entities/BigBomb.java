package dev.game.entities;


import java.time.Duration;
import java.time.Instant;

import dev.map.Map;
/**
 * Bombs dropped by a bomber - explodes in double the radius and destroys walls
 * @author Andy Yu
 *
 */
public class BigBomb extends LitBomb {

	public BigBomb(int[] coordinate, Map m) {
		super(coordinate, m);	
	}

	@Override
	public void explode() {
		Map m = this.getMap();
		int x = this.getCoordinate()[0];
		int y = this.getCoordinate()[1];
		int[] coordinate = new int[2];

		int dx;
		int dy;
		for (dx=-2;dx<=2;dx++) {
			for (dy=-2;dy<=2;dy++) {
				coordinate[0] = x + dx;
				coordinate[1] = y + dy;
				m.killEnemy(coordinate);
				m.clearBoulder(coordinate);
				m.killPlayer(coordinate);
				m.clearWalls(coordinate);
			}
		}
		m.delete(this);
		Explosion exp = new Explosion(this.getCoordinate(),this.getMap());
		m.addEntity(exp);
	}
	@Override
	public String imagePath() {
		Duration between = Duration.between(Instant.now(), this.explodeTime);
		long seconds = between.getSeconds();
		seconds = Math.abs(seconds);
		if (seconds < 3 && seconds >= 2) {
			return "dev/game/assets/bigbomb_lit_1.png";

		}
		else if (seconds < 2 && seconds >= 1) {
			return "dev/game/assets/bigbomb_lit_2.png";

		}
		else  {
			return "dev/game/assets/bigbomb_lit_3.png";

		}
	}
}

