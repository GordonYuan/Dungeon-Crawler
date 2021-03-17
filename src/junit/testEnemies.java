package junit;

import dev.map.*;
import dev.game.enemy.*;
import dev.game.entities.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class testEnemies {
	Map m = new Map(10,5,"testmap"); //Map of width 10 and height 
	int[] playerCoordinate = {0,0};
	Player p = new Player(playerCoordinate, m);
	
	int[] enemy1coordinate = {0,1};
	Hunter h = new Hunter(enemy1coordinate, m, p);
	Coward c = new Coward(enemy1coordinate, m, p);

	int[] enemy2coordinate = {0,3};
	Coward c1 = new Coward(enemy2coordinate, m, p);
	
	int[] strategistCoordinate = {1,1};
	Strategist strategist = new Strategist(strategistCoordinate, m, p);
	
	
	@BeforeEach
	void setUp() {
		m.addEntity(p);
	}
	
	// hunter starts at (0,1) moves onto player (0,0) and player dies
	@Test
	void testHunterMove() {
		p.GameMove(playerCoordinate);
		int[] hunterCoordinate = {9,4};
		Hunter h = new Hunter(hunterCoordinate, m, p);
		m.addEntity(h);
		assertEquals(true, h.GameMove(h.getMove()));
		assertEquals(true, h.GameMove(h.getMove()));
		assertEquals(true, h.GameMove(h.getMove()));
		assertEquals(true, h.GameMove(h.getMove()));
		assertEquals(true, h.GameMove(h.getMove()));
		assertEquals(true, h.GameMove(h.getMove()));
		assertEquals(true, h.GameMove(h.getMove()));
		assertEquals(true, h.GameMove(h.getMove()));
		assertEquals(true, h.GameMove(h.getMove()));
		assertEquals(true, h.GameMove(h.getMove()));
		assertEquals(true, h.GameMove(h.getMove()));
		assertEquals(true, h.GameMove(h.getMove()));
		assertEquals(true, h.GameMove(h.getMove()));
		assertEquals(true, h.getMoveBehaviour() instanceof Aggro);
		assertArrayEquals(playerCoordinate, h.getCoordinate());
		p.collisionDetection();
		assertEquals(false, p.isAlive());
	}
	
	// coward starts at (0,1) and ends up at (0,2), running away from (0,0)
	@Test
	void testCowardRun() { 
		m.addEntity(c);
		int[] endPosition = {0,2};
		c.GameMove(c.getMove());
		assertEquals(true, h.getMoveBehaviour() instanceof Aggro);
		p.collisionDetection();
		assertEquals(true, p.isAlive());
		assertArrayEquals(endPosition, c.getCoordinate());
	}

	// move towards player, from (0,3) to (0,2) as coward is more than 2 blocks out of range
	@Test
	void testCowardAttack() {
		int[] endPosition = {0,2};
		p.GameMove(playerCoordinate);
		m.addEntity(c1);
		c1.GameMove(c1.getMove());
		assertEquals(true, c1.getMoveBehaviour() instanceof Aggro);
		p.collisionDetection();
		assertEquals(true, p.isAlive());
		assertArrayEquals(endPosition, c1.getCoordinate());
	}
	
	@Test
	void testHoundMove() {
		int[] hunterCoordinate = {4,4};
		int[] playerCoordinate = {3,3};
		int[] houndCoordinate = {0,0};
		int[] endPosition = {2,2};
		Player p = new Player(playerCoordinate, m);
		Hunter h = new Hunter(hunterCoordinate, m, p);
		Hound hound = new Hound(houndCoordinate, m, p);
		hound.setOwner(h);
		m.addEntity(p);
		m.addEntity(h);
		m.addEntity(hound);
		assertEquals(h, hound.getOwner());
		hound.GameMove(hound.getMove());
		hound.GameMove(hound.getMove());
		hound.GameMove(hound.getMove());
		hound.GameMove(hound.getMove());
		assertArrayEquals(endPosition, hound.getCoordinate());
		assertEquals(true, hound.getMoveBehaviour() instanceof Track);
	}
	
	// Strategist starts on {1,1} and player moves from {0,0} to {0,1}
	@Test
	void testStrategistMove() {
		m.addEntity(strategist);
		m.addEntity(strategist);
		Door door = new Door(1);
		Switcher switcher = new Switcher();
		m.addTiles(switcher, 2, 2);
		p.GameMove(enemy1coordinate);
		strategist.GameMove(strategist.getMove());
		strategist.GameMove(strategist.getMove());
		assertEquals(true, strategist.getMoveBehaviour() instanceof Strategic);
		p.collisionDetection();
		int[] endPoint = {2,2};
		assertArrayEquals(endPoint, strategist.getCoordinate());
	}

	// test hound change in behaviour after owner death
	@Test
	void testHoundDeath() {
		int[] hunterCoordinate = {4,4};
		int[] playerCoordinate = {3,3};
		int[] houndCoordinate = {0,0};
		Player p1 = new Player(playerCoordinate, m);
		Hunter h = new Hunter(hunterCoordinate, m, p1);
		Hound hound = new Hound(houndCoordinate, m, p1);
		hound.setOwner(h);;
		assertEquals(h, hound.getOwner());
		m.addEntity(p1);
		m.addEntity(h);
		m.addEntity(hound);
		m.killEnemy(hunterCoordinate);
		h.detatchHound(hound);
		hound.checkMoveBehaviour();
		assertEquals(true, hound.getMoveBehaviour() instanceof Aggro);
	}
	
	@Test
	void testEnemyPit() {
		Map map = m;
		Pit pit = new Pit();
		m.addTiles(pit, 1, 1);
		h.move(strategistCoordinate);
		assertEquals(true, h.terrainCheck(h.getCoordinate()));
		assertEquals(null, map.hasEnemy(strategistCoordinate));
	}
}