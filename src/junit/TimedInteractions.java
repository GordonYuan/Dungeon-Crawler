package junit;

import dev.game.*;
import dev.map.*;
import dev.game.entities.*;
import dev.game.enemy.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
//import java.util.concurrent.*;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TimedInteractions {
	Map m = new Map(10,5,"testmap"); //Map of width 10 and height 5
	//Handler h = new Handler(m);
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
	void testInvulnDuration() {
		p.move(invulnPotLocation);
		System.out.println("Giving invuln pot to player");
		p.collisionDetection();
		assertTrue(p.isImmune());
		try {
			Thread.sleep(11000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		p.checkInvuln();
		assertFalse(p.isImmune());
	}
	
	@Test
	void testDropBombAndExplode() {
		int[] bombLocation = {2,2};
		int[] boulderLocation = {3,2};
		Boulder boulder = new Boulder(boulderLocation,m);
		m.addEntity(boulder);
		Bomb b = new Bomb(bombLocation,m);
		m.addEntity(b);
		p.move(bombLocation);
		p.collisionDetection();
		assertTrue(p.getBombs()==1);
		p.dropBomb();
		
		int[] bombVictimLocation1 = {2,1};
		int[] bombVictimLocation2 = {1,2};
		Hunter bombVictim = new Hunter(bombVictimLocation1,m,p);
		m.addEntity(bombVictim);		
		p.move(bombVictimLocation2);
		assertTrue(Arrays.equals(p.getCoordinate(), bombVictimLocation2));
		assertTrue(p.isAdjacent(bombLocation));
		LitBomb lit = null;
	
		for (Entity e:m.getEntities()) {
			assertFalse(e instanceof Bomb);
			if (e instanceof LitBomb && Arrays.equals(e.getCoordinate(), bombLocation)) {
				lit = (LitBomb)e;
			}
		}
		
		try {
			Thread.sleep(11000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		lit.tick();
		assertFalse(p.isAlive());
		assertTrue(m.hasEnemy(bombVictimLocation1)==null);
		assertTrue(m.hasEntity(boulderLocation)==false);
	}
}
