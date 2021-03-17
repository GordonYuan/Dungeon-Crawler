package junit;

import dev.game.*;
import dev.map.*;
import dev.game.entities.*;
import dev.game.enemy.*;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class testPlayerItemInteractions {
	Map m = new Map(10,5,"testmap"); //Map of width 10 and height 5
	int[] playerCoordinate = {0,0};
	Player p = new Player(playerCoordinate,m);
	int[] enemy1coordinate = {0,1};
	int[] enemy4coordinate = {0,2};
	int[] enemy3coordinate = {0,3};
	int[] enemy2coordinate = {5,0};
	int[] invulnPotLocation = {4,4};
	Sword s = new Sword(enemy1coordinate,m);
	InvulnPot ip = new InvulnPot(invulnPotLocation,m);
	Arrow a = new Arrow(enemy3coordinate,m);

	Hunter hunter = new Hunter(enemy1coordinate,m,p);
	Hunter swordTarget = new Hunter(enemy4coordinate,m,p);
	
	
	@BeforeEach
	void setUp() {
		m.addEntity(p);
		m.addEntity(s);
		m.addEntity(ip);
	}
	
	@Test
	void testAddPlayerToMap() {
	
		for (Entity e: m.getEntities()) {
			if (e.equals(p)) {
				assertTrue(true);
				System.out.println("Found player");
				return;
			}
		}
		assertTrue(false);

	}

	@Test
	void testAddSwordToMap() {
		for (Entity e: m.getEntities()) {
			if (e.equals(s)) {
				assertTrue(true);
				System.out.println("Found sword");
				return;
			}
		}
	}

	@Test
	void testSwordPickup() {
		p.move(enemy1coordinate);
		p.collisionDetection();
		assertTrue(p.getSword().equals(s));
		for (Entity e:m.getEntities()) {
			if (e.equals(s)) { assertTrue(false);}
		}
	}
	@Test
	void testAddHunterToMap(){ 
		m.addEntity(hunter);
		for (Entity e:m.getEntities()) {
			if (e.equals(hunter)) {
				assertTrue(e.getCoordinate()[0]==enemy1coordinate[0] && e.getCoordinate()[1]==enemy1coordinate[1]);
				return;
			}
		}
		assertTrue(false);
	}
	@Test
	void testMovePlayer() {
		p.move(enemy1coordinate);
		assertTrue(p.getCoordinate()[0]==enemy1coordinate[0] && p.getCoordinate()[1]==enemy1coordinate[1]);
	}

	@Test
	void testDieToEnemy() {
		m.addEntity(hunter);
		p.move(enemy1coordinate);
		p.collisionDetection();
		assertFalse(p.isAlive());
	}
	@Test
	void testInvulnPotPickup() {
		p.move(invulnPotLocation);
		System.out.println("Giving invuln pot to player");
		p.collisionDetection();
		assertTrue(p.isImmune());
	}
	@Test 
	void testUseInvulnPot() {
		m.addEntity(hunter);
		p.move(invulnPotLocation);
		p.collisionDetection();
		p.move(enemy1coordinate);
		p.collisionDetection();
		assertTrue(p.isAlive());
		for (Entity e:m.getEntities()) {
			if (e.equals(hunter)) { assertTrue(false);}
		}
	}
	
	@Test 
	void testUseSword() {
		p.move(enemy1coordinate);
		p.collisionDetection();
		m.addEntity(swordTarget);
		p.swingSword('s');
		for (Entity e:m.getEntities()) {
			if (e.equals(swordTarget)) {assertTrue(false);}
		}
		assertTrue(p.getSword().getDurability()==4);
	}
	@Test
	void testUseArrow() {
		m.addEntity(a);
		p.move(enemy3coordinate);
		p.collisionDetection();
		m.addEntity(swordTarget);
		p.shootArrow('w');
		for (Entity e:m.getEntities()) {
			if (e.equals(swordTarget)) {assertTrue(false);}
		}
		assertTrue(p.getArrows()==0);
	}
	@Test
	void testShootArrowThroughBoulder() {
		m.addEntity(a);
		p.move(enemy3coordinate);
		m.addEntity(hunter);
		Boulder block = new Boulder(enemy4coordinate,m);
		m.addEntity(block);
		p.shootArrow('s');
		for (Entity e:m.getEntities()) {
			if (e.equals(hunter)) {assertTrue(true); return;}
		}
		assertTrue(false);
	}
	@Test
	void testShootArrowThroughWall() {
		m.addEntity(a);
		p.move(enemy3coordinate);
		m.addEntity(hunter);
		Tile wall = new Wall();
		m.addTiles(wall, enemy4coordinate[0], enemy4coordinate[1]);
		p.shootArrow('s');
		for (Entity e:m.getEntities()) {
			if (e.equals(hunter)) {assertTrue(true); return;}
		}
		assertTrue(false);
	}
	
	@Test
	void testShootArrowThroughEnemy() {
		m.addEntity(a);
		p.move(enemy3coordinate);
		m.addEntity(hunter);
		Hunter block = new Hunter(enemy4coordinate,m,p);
		m.addEntity(block);
		p.shootArrow('s');
		for (Entity e:m.getEntities()) {
			if (e.equals(hunter)) {assertTrue(true); return;}
		}
		assertTrue(false);
	}
	
	@Test 
	void testOpenDoor() {
		int[] keyLocation = {2,2};
		int[] doorLocation = {4,0};
		Key k = new Key(keyLocation,m,2);
		Door d = new Door(2);
		m.addEntity(k);
		p.move(keyLocation);
		p.collisionDetection();
		m.addTiles(d, 4, 0);
		p.move(doorLocation);
		assertTrue(Arrays.equals(p.getCoordinate(), doorLocation));
	}
	@Test
	void testPickUpBomb() {
		int[] bombLocation = {2,2};
		Bomb b = new Bomb(bombLocation,m);
		m.addEntity(b);
		p.move(bombLocation);
		p.collisionDetection();
		assertTrue(p.getBombs()==1);
	}
	@Test
	void testDropBomb() {
		int[] bombLocation = {2,2};
		Bomb b = new Bomb(bombLocation,m);
		m.addEntity(b);
		p.move(bombLocation);
		p.collisionDetection();
		assertTrue(p.getBombs()==1);
		p.dropBomb();
		for (Entity e:m.getEntities()) {
			if (e.getType()==Type.litbomb && Arrays.equals(e.getCoordinate(), bombLocation)) {
				assertTrue(true);
				return;
			}
		}
		assertTrue(false);
	}
	@Test 
	void testPushBoulderEmptyTile() {
		int[] start = {0,1}; //Spawn to the left of boulder
		p.move(start);
		int[] boulderLocation = {1,1};
		int[] boulderLocation2 = {2,1}; //Pushed to right
		Boulder b = new Boulder(boulderLocation,m);
		m.addEntity(b);
		p.move(boulderLocation);
		assert(Arrays.equals(b.getCoordinate(), boulderLocation2));
		
	}
	@Test 
	void testPushBoulderOffMap() {
		int[] start = {0,3}; 
		p.move(start);
		int[] boulderLocation = {0,4};
		Boulder b = new Boulder(boulderLocation,m);
		m.addEntity(b);
		try {
			p.move(boulderLocation); //Should raise exception
			assertTrue(false);
		}
		catch (IllegalArgumentException e) {;}
		assertTrue(Arrays.equals(b.getCoordinate(), boulderLocation));
		
	}
	@Test 
	void testPushBoulderIntoAnotherBoulder() {
		int[] start = {0,1}; //Spawn to the left of boulder
		p.move(start);
		int[] boulderLocation = {1,1};
		int[] secondBoulderLocation = {2,1};
		int[] boulderLocation2 = {1,1}; //Doesn't move
		Boulder b = new Boulder(boulderLocation,m);
		Boulder secondBoulder = new Boulder(secondBoulderLocation,m);
		m.addEntity(secondBoulder);
		m.addEntity(b);
		try {
			p.move(boulderLocation); //Should raise exception
			assertTrue(false);
		}
		catch (IllegalArgumentException e) {;}
		assertTrue(Arrays.equals(b.getCoordinate(), boulderLocation2));
	}
	@Test
	void testPitDeath() {
		int[] pitLocation = {1,1};
		Pit pit = new Pit();
		assertTrue(m.addTiles(pit, 1, 1));
		p.move(pitLocation);
		assertTrue(Arrays.equals(p.getCoordinate(), pitLocation));
		System.out.println(m.getTileType(pitLocation));
		assertTrue(m.getTileType(pitLocation)==Type.pit);
		p.collisionDetection();
		assertTrue(!p.isAlive());
	}
	@Test
	void testPushBoulderIntoPit() {
		int[] start = {0,1}; //Spawn to the left of boulder
		p.move(start);
		int[] boulderLocation = {1,1};
		int[] pitLocation = {2,1};
		Boulder boulder = new Boulder(boulderLocation,m);
		Pit pit = new Pit();
		m.addTiles(pit, 2, 1);
		p.move(boulderLocation);
		assertFalse(m.hasEntity(pitLocation));
		for (Entity e:m.getEntities()) {
			if (e.equals(boulder)) { assertTrue(false);}
		}
		//Just for good measure check the put is actually there
		
	}
	@Test
	void testHovering() {
		int[] hoverLocation = {1,1};
		HoverPot hov = new HoverPot(hoverLocation,m);
		m.addEntity(hov);
		p.move(hoverLocation);
		p.collisionDetection();
		assertTrue(p.isHovering());
		Pit pit = new Pit();
		m.addTiles(pit, 2, 2);
		int[] pitLocation = {2,2};
		p.move(pitLocation);
		p.collisionDetection();
		assertTrue(p.isAlive());
	}
}

