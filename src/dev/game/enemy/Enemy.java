package dev.game.enemy;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import dev.game.entities.Creature;
import dev.game.entities.Entity;
import dev.game.entities.Player;
import dev.map.Map;
import dev.map.Tile;
import dev.map.Type;

public abstract class Enemy extends Creature {
	private Player player;
	protected MoveBehaviour moveBehaviour;
	private Instant lastTick;
	private double velocity;
	
	public Enemy(int[] coordinate, Map m, int type, Player player, double velocity) {
		super(coordinate, m, type);
		this.player = m.getPlayer();
		this.velocity = velocity;
		this.lastTick = Instant.now();
	}
	
	/**
	 * Retrieves the next move the enemy should take. If enemy is running, the run algorithm is used.
	 * If other algorithms are used, the pathfinding algorithm findNextMove is used
	 * @return coordinate of next move
	 */
	public int [] getMove() {
		this.checkMoveBehaviour();
		int [] move = new int [2];
		Map map = this.map;
		if (moveBehaviour instanceof Run) {
			move = moveBehaviour.decideDestination(this);
		} else {
			move = findNextMove(moveBehaviour.decideDestination(this));
		}
		if (map.hasEnemy(move) != null || !player.isAlive()) {
			return this.getCoordinate(); // if there is another enemy on tile to move onto, dont move
		} else {
			return move; // move to decided tile
		}
	}
	
	public MoveBehaviour getMoveBehaviour() {
		return moveBehaviour;
	}
	
	public void checkMoveBehaviour() {
	}
	
	@Override
	public boolean hasClash(Entity e) {
		if (super.hasClash(e)) { return true;}
		if (e instanceof Enemy) {
			return true;
		}
		return false;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	/**
	 * Causes the enemy to move dependent on their velocity while the player is alive
	 */
	public void tick() {
		if (player.isAlive()) {
			Duration d = Duration.between(lastTick, Instant.now());
			long ms = d.toMillis();
	        if (ms > 2000/velocity) {
	        	this.GameMove(getMove());
	        	checkDeath();
	        	lastTick = Instant.now();
	        }
		}
	}
	
	/**
	 * Helper function to getMove to find the next move in the shortest path around 
	 * impassable objects and enemies to the destination coordinate passed in.
	 * @param dest, destination coordinate
	 * @return first coordinate in path to get to destination coordinate
	 */
	public int [] findNextMove(int[] dest) {
		Queue<int[]> queue = new LinkedList<>();
		ArrayList<int[]> visited = new ArrayList<>();
		HashMap<int[], int[]> parent = new HashMap<>(); // left is child, right is parent
		boolean pathFound = false;
		Map map = this.getMap();
		int[] start = this.getCoordinate();
		if (Arrays.equals(this.getCoordinate(), dest) || !map.isPassable(dest) || map.hasEnemy(dest) != null) {
			return this.getCoordinate();
		}
		queue.add(start); // add starting point
		
		int ii = 0;
		while(!queue.isEmpty()) { // while there are still points to visit
			System.out.println(ii);
			ii++;
			int[] curr = queue.poll(); // take next item from queue
			visited.add(curr); // add current tile as visited
			
			int [] xOffset = {1, -1, 0, 0};
			int [] yOffset = {0, 0, 1, -1};
			
			// search up down left right coordinates
			for (int i = 0; i < 4; i++) {
				// if within bounds of the map
				int x = curr[0] + xOffset[i];
				int y = curr[1] + yOffset[i];
				if (x < map.getWidth() && x >= 0 && y < map.getHeight() && y >= 0) {
					int[] toVisit = {x,y};
					// if passable and has no enemy AND NOT VISITED add to queue and insert parent child relationship
					if (map.isPassable(toVisit) && map.hasEnemy(toVisit) == null && !checkTileEquality(visited, toVisit)) {
						if (Arrays.equals(toVisit, dest)) {
							parent.put(dest, curr);
							pathFound = true;
							break;
						} else {
							queue.add(toVisit);
							parent.put(toVisit, curr);
						}
					}
				}
			}
			if (pathFound) {
				break;
			}
		}

		if (pathFound) { //trace back the path
			LinkedList<int[]> path = new LinkedList<>();
			int[] coordinate = dest;
			path.addFirst(coordinate);
			while(!parent.get(coordinate).equals(start)) { 
				coordinate = parent.get(coordinate);
				path.addFirst(coordinate);
			}
			return path.getFirst();
		} else {
			// don't move
			return this.getCoordinate();
		}
	}
	
	public boolean checkTileEquality(ArrayList<int[]> visited, int[] toVisit) {
		for (int[] i: visited) {
			if (i[0] == toVisit[0] && i[1] == toVisit[1]) {
				return true;
			}
		}
		return false;
	}
	
	public void checkDeath() {
		if (map.getTileType(this.getCoordinate()) == Type.pit) {
			map.killEnemy(this.getCoordinate());
		}
	}
}