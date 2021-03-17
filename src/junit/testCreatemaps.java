package junit;

import dev.map.*;
import dev.map.Switcher;
import dev.game.enemy.*;
import dev.game.entities.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class testCreatemaps {
	
	@Test
	//test create an empty map
	public void test1() {
		Map newmap = new Map(400,300,"my map");
		assertEquals(300,newmap.getHeight());
		assertEquals(400,newmap.getWidth());
		assertEquals("my map",newmap.getName());
		Tile[][] tiles = newmap.getLand();
		for(int i=0;i<400;i++) {
			for(int k=0;k<300;k++) {
				assertEquals(Type.empty,tiles[i][k].getType());
			}
		}
	}
	@Test
	//test add tiles outside map
	public void test2() {
		Map newmap = new Map(5,5,"my map");
		Door door = new Door(1);
		
		assertEquals(false,newmap.addTiles(door, 5, 5));
		
		
	}
	@Test
	//test add tiles inside map
	public void test3() {
		Map newmap = new Map(5,5,"my map");
		Switcher switcher = new Switcher();
		assertEquals(true,newmap.addTiles(switcher, 2, 2));
		assertEquals(true,newmap.addTiles(switcher, 1, 1));
		Tile[][] tiles = newmap.getLand();
		tiles = newmap.getLand();
		
		assertEquals(Type.floorswitch,tiles[1][1].getType());
		assertEquals(Type.floorswitch,tiles[2][2].getType());
	}
	@Test
	// test add entity
	public void test4() {
		Map newmap = new Map(5,5,"my map");
		
		int[] coor = new int[] {1,1};
		Bomb bomb= new Bomb(coor, null);
		Sword sword = new Sword(coor, null);
		Player player = new Player(coor, null);
		Coward coward = new Coward(coor, null, player);
		//newmap.addTiles(door, 1, 1);
		newmap.addEntity(player);
		newmap.addEntity(coward);
		newmap.addEntity(bomb);
		assertEquals(3,newmap.getEntities().size());
		assertEquals(Type.player,newmap.getEntities().get(0).getType());
		assertEquals(Type.coward,newmap.getEntities().get(1).getType());
		assertEquals(Type.bomb,newmap.getEntities().get(2).getType());
		

		
	}
	@Test
	// test insert clash
	public void test55() {
		Map newmap = new Map(5,5,"my map");
		
		int[] coor = new int[] {1,1};
		Bomb bomb= new Bomb(coor, null);
		Sword sword = new Sword(coor, null);
		Player player = new Player(coor, null);
		Coward coward = new Coward(coor, null, player);
		//newmap.addTiles(door, 1, 1);
		newmap.addEntity(player);
		newmap.addEntity(coward);
		newmap.addEntity(bomb);
		newmap.addEntity(sword);
		assertEquals(3,newmap.getEntities().size());
		
		
		
	}
	
	@Test
	// test win kill
	public void test66() {
		Map newmap = new Map(5,5,"my map");
		
		int[] coor = new int[] {1,1};
		Bomb bomb= new Bomb(coor, null);
		Sword sword = new Sword(coor, null);
		Player player = new Player(coor, null);
		Coward coward = new Coward(coor, null, player);
		//newmap.addTiles(door, 1, 1);
		newmap.addEntity(player);
		newmap.addEntity(bomb);
		newmap.addEntity(sword);
		Winall winkill = new WinKill(newmap);
		newmap.addWinconditions(winkill);
		assertEquals(winkill.checkWin(),true);
		newmap.addEntity(coward);
		newmap.addWinconditions(winkill);
		assertEquals(winkill.checkWin(),false);
		
		
	}
	@Test
	// test win kill success
	public void test77() {
		Map newmap = new Map(5,5,"my map");
		Exit exit = new Exit();
		newmap.addTiles(exit, 1, 1);
		int[] coor = new int[] {1,1};
		Bomb bomb= new Bomb(coor, null);
		Sword sword = new Sword(coor, null);
		Player player = new Player(coor, null);
		Coward coward = new Coward(coor, null, player);
		//newmap.addTiles(door, 1, 1);
		newmap.addEntity(player);
		newmap.addEntity(bomb);
		newmap.addEntity(sword);
		Winall winext = new WinExit(newmap);
		newmap.addWinconditions(winext);
		assertEquals(winext.checkWin(),true);
		
		
	}
	
	@Test
	// test win kill flase
	public void test88() {
		Map newmap = new Map(5,5,"my map");
		int[] coor = new int[] {1,1};
		Bomb bomb= new Bomb(coor, null);
		Sword sword = new Sword(coor, null);
		Player player = new Player(coor, null);
		//newmap.addTiles(door, 1, 1);
		newmap.addEntity(player);
		newmap.addEntity(bomb);
		newmap.addEntity(sword);
		Winall winext = new WinExit(newmap);
		newmap.addWinconditions(winext);
		assertEquals(winext.checkWin(),false);
		
		
	}
	
	@Test
	// test win push false cos no boulder
	public void test9() {
		Map newmap = new Map(5,5,"my map");
		
		int[] coor = new int[] {1,1};
		Bomb bomb= new Bomb(coor, null);
		Sword sword = new Sword(coor, null);
		Player player = new Player(coor, null);
		//newmap.addTiles(door, 1, 1);
		newmap.addEntity(player);
		newmap.addEntity(bomb);
		newmap.addEntity(sword);
		Winall winpush = new WinPush(newmap);
		newmap.addWinconditions(winpush);
		assertEquals(winpush.checkWin(),false);
		
		
	}
	
	@Test
	// test win push false cos no boulder
	public void test10() {
		Map newmap = new Map(5,5,"my map");
		Winall winpush = new WinPush(newmap);
		newmap.addWinconditions(winpush);
		assertEquals(winpush.checkWin(),false);
		
		
	}
	
	@Test
	// test win push false cos  boulder not trigered
	public void test11() {
		Map newmap = new Map(5,5,"my map");
		Switcher switcher = new Switcher();
		newmap.addTiles(switcher, 2, 2);
		Winall winpush = new WinPush(newmap);
		newmap.addWinconditions(winpush);
		assertEquals(winpush.checkWin(),false);
		
		
	}
	
	public void test12() {
		Map newmap = new Map(5,5,"my map");
		Switcher switcher = new Switcher();
		newmap.addTiles(switcher, 2, 2);
		int[] coor = new int[] {2,2};
		Boulder bouder = new Boulder(coor, newmap);
		
		Winall winpush = new WinPush(newmap);
		newmap.addWinconditions(winpush);
		assertEquals(winpush.checkWin(),true);
		
		
	}
	
}