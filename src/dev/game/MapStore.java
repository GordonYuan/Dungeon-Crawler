package dev.game;

import java.util.ArrayList;

import dev.game.enemy.*;
import dev.game.entities.*;
import dev.map.*;

/**
 * In-memory storage of maps
 *
 */

public class MapStore {
	private ArrayList<Map> maps;
	public MapStore() {
		this.maps = new ArrayList<Map>();
		this.initmaps();
	}
	
	public Map createMap(String name, int x, int y) {
		Map newmap = new Map(x,y,name);
		for(Map map:maps) {
			if(map.getName() == name) {
				return null;
			}
		}
		
		return newmap;
	}
	
	
	/**
	 * A crapton of initial maps. There are 3 maps.
	 */
	private void initmaps() {
		Map map1 =this.createMap("buildin1", 20, 20);
		Map map2 =this.createMap("buildin2", 20, 20);
		Map map3 =this.createMap("buildin3", 20, 20);
		int[] coor = new int[] {1,1};
		int[] coor10 = new int[] {6,6};
		Player player1 = new Player(coor, map1);
		Player player2 = new Player(coor, map2);
		Player player3 = new Lich(coor, map3);
		map1.addEntity(player1);
		Exit exit1 = new Exit();
		map1.addTiles(exit1, 18, 18);
		map2.addEntity(player2);
		map3.addEntity(player3);
		Boulder boulder3 = new Boulder(coor10,map3);
		map3.addEntity(boulder3);
		for(int i=0;i<20;i++) {
			Wall wall1 = new Wall();
			Wall wall2 = new Wall();
			Wall wall3 = new Wall();
			Wall wall4 = new Wall();
			map1.addTiles(wall1, 0, i);
			map1.addTiles(wall2, i, 0);
			map1.addTiles(wall3, 19, i);
			map1.addTiles(wall4, i, 19);
			map2.addTiles(wall1, 0, i);
			map2.addTiles(wall2, i, 0);
			map2.addTiles(wall3, 19, i);
			map2.addTiles(wall4, i, 19);
			map3.addTiles(wall1, 0, i);
			map3.addTiles(wall2, i, 0);
			map3.addTiles(wall3, 19, i);
			map3.addTiles(wall4, i, 19);
		}
		
		for(int i = 8;i<12;i++) {
			Switcher swtich1 = new Switcher();
			int[] coor1 = new int[] {i+1,i};
			Boulder boulder = new Boulder(coor1,map2);
			map2.addTiles(swtich1, i, i);
			map2.addEntity(boulder);
		}
		Arrow arrow1 = new Arrow(coor10, map3);
		map3.addEntity(arrow1);
		Door door1 = new Door(0);
		Pit pit1 = new Pit();
		Wall wall1 = new Wall();
		Wall wall2 = new Wall();
		Wall wall3 = new Wall();
		Wall wall4 = new Wall();
		Wall wall5 = new Wall();
		map3.addTiles(wall1, 4, 6);
		map3.addTiles(wall2, 5, 6);
		map3.addTiles(wall3, 7, 3);
		map3.addTiles(wall4, 7, 5);
		map3.addTiles(wall5, 7, 10);
		map3.addTiles(door1, 7, 4);
		map3.addTiles(pit1, 8, 8);
		int[] coor1 = new int[] {2,2};
		Bomb bomb1 = new Bomb(coor1, map3);
		map3.addEntity(bomb1);
		int[] coor2 = new int[] {2,3};
		HoverPot pot1 = new HoverPot(coor2,map3);
		map3.addEntity(pot1);
		int[] coor3 = new int[] {2,4};
		InvulnPot pot2 = new InvulnPot(coor3,map3);
		map3.addEntity(pot2);
		int[] coor4 = new int[] {2,5};
		Key key = new Key(coor4,map3, 0);
		map3.addEntity(key);
		int[] coor5 = new int[] {2,6};
		Sword sword1 = new Sword(coor5, map3);
		map3.addEntity(sword1);
		int[] coor11 = new int[] {3,3};
		Treasure treasure1 = new Treasure(coor11, map3);
		map3.addEntity(treasure1);
		
		Bomb bomb4 = new Bomb(coor1, map2);
		map2.addEntity(bomb4);
		
		int[] coor6 = new int[] {2,7};
		Coward coward1 = new Coward(coor6,map3, player3);
		map3.addEntity(coward1);
		int[] coor7 = new int[] {2,9};
		Hunter hunter1 = new Hunter(coor7,map3, player3);
		map3.addEntity(hunter1);
		int[] coor8 = new int[] {2,8};
		Hound hound1 = new Hound(coor8,map3, player3);
		map3.addEntity(hound1);
		hound1.setOwner(hunter1);
		Hunter hunter2 = new Hunter(coor7,map3, player3);
		map3.addEntity(hunter2);
		int[] coor9 = new int[] {2,10};
		Strategist stagist = new Strategist(coor9,map3, player3);
		map3.addEntity(stagist);
		WinExit win11 = new WinExit(map1);
		map1.addWinconditions(win11);
		WinPush win22 = new WinPush(map2);
		map2.addWinconditions(win22);
		WinKill win33 = new WinKill(map3);
		map3.addWinconditions(win33);
		WinTreasure win44 = new WinTreasure(map3);
		map3.addWinconditions(win44);
		this.saveMap(map1);
		this.saveMap(map2);
		this.saveMap(map3);
		
	}
	
	public boolean saveMap(Map newmap) {
		this.maps.add(newmap);
		return true;
	}
	public void updateMap(Map newmap,int index) {
		this.maps.set(index, newmap);
	}

	public ArrayList<Map> getMaps() {
		return maps;
	}
}