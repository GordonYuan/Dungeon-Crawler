package junit;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dev.game.entities.*;
import dev.game.enemy.Hunter;
import dev.game.entities.Archer;
import dev.game.entities.BigBomb;
import dev.game.entities.Bomber;
import dev.game.entities.Boulder;
import dev.game.entities.GameMaster;
import dev.game.entities.Lich;
import dev.map.Map;
import dev.map.Wall;

class testExtensions {
	Map m = new Map(10,5,"testmap"); //Map of width 10 and height 5
	int[] playerCoor = {0,0};
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testLichDieOnce() {
		Lich l = new Lich(playerCoor,m);
		m.addEntity(l);
		int[] move = {4,4};
		l.move(move);
		l.die();
		assertTrue(l.isAlive());
		assertTrue(Arrays.equals(l.getCoordinate(), playerCoor));
	}
	@Test 
	void testLichDieTwice() {
		Lich l = new Lich(playerCoor,m);
		m.addEntity(l);
		int[] move = {4,4};
		l.move(move);
		l.die();
		l.die();
		assertTrue(!l.isAlive());
	}
	@Test
	void testLichTeleportIntoBoulder() {
		Lich l = new Lich(playerCoor,m);
		m.addEntity(l);
		int[] move = {4,4};
		l.move(move);
		Boulder b = new Boulder(playerCoor,m);
		m.addEntity(b);
		l.die();
		assertTrue(!l.isAlive());
	}
	@Test
	void testBigBombWalls() {
		int[] coor1 = {0,1};
		int[] coor2 = {0,2};
		Wall wall1 = new Wall();
		Wall wall2 = new Wall();
		m.addTiles(wall1, coor1[0], coor1[1]);
		m.addTiles(wall2, coor2[0], coor2[1]);
		BigBomb b = new BigBomb(playerCoor,m);
		m.addEntity(b);
		b.explode();
		assertTrue(m.isPassable(coor1));
		assertTrue(m.isPassable(coor2));
	}
	@Test 
	void testGameMasterDie() {
		GameMaster g = new GameMaster(playerCoor,m);
		m.addEntity(g);
		g.die();
		assertTrue(g.isAlive());
	}
	@Test
	void testArcherInitialise() {
		Archer a = new Archer(playerCoor,m);
		assertTrue(a.getArrows()==3);
	}
	@Test
	void testBomberInitialise() {
		Bomber b = new Bomber(playerCoor,m);
		assertTrue(b.getBombs()==3);
	}
	@Test
	void testArcherShootArrow() {
		Archer a = new Archer(playerCoor,m);
		int[] coor1 = {0,1};
		int[] coor2 = {0,2};
		Hunter h1 = new Hunter(coor1,m,a);
		Hunter h2 = new Hunter(coor2,m,a);
		m.addEntity(a);
		m.addEntity(h1);
		m.addEntity(h2);
		a.shootArrow('s');
		for (Entity e:m.getEntities()) {
			if (e.equals(h1) || e.equals(h2)) {
				assertTrue(false);
			}
		}
	}
	@Test
	void testMageShootFireball() {
		Mage mage = new Mage(playerCoor,m);
		int[] coor1 = {0,1};
		Hunter h1 = new Hunter(coor1,m,mage);
		m.addEntity(h1);
		m.addEntity(mage);
		mage.giveArrow();
		mage.shootArrow('s');
		assertFalse(mage.isAlive());
		for (Entity e:m.getEntities()) {
			if (e.equals(h1)) {
				assertTrue(false);
			}
		}
	}
}
