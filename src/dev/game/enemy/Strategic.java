package dev.game.enemy;

import dev.game.entities.Player;
import dev.map.Map;
import dev.map.Tile;
import dev.map.Type;

public class Strategic implements MoveBehaviour {
	/**
	 * Returns the closest important coordinates to the player as places to go to.
	 * These include floorswitches, invincibility potions, swords, keys, arrows.
	 */
	@Override
	public int [] decideDestination(Enemy enemy) {
		int [] closestTile = new int [2];
		boolean tileFound = false;
		int minDist = Integer.MAX_VALUE;
		Map map = enemy.getMap();
		
		for (int x = 0; x < map.getWidth(); x++) {
			for (int y = 0; y < map.getHeight(); y++) {
				Tile tile = map.getTile(x, y);
				int [] coordinate = new int [2];
				if (tile.isPassable() && (tile.hasEnemy() == null || tile.hasEnemy().equals(enemy))) {
					if (tile.getType() == Type.floorswitch || tile.containVitals()) {
						coordinate[0] = x;
						coordinate[1] = y;
						Player player = enemy.getPlayer();
						int d = (Math.abs(player.getX() - coordinate[0]) + Math.abs(player.getY() - coordinate[1]));
						if (d < minDist) {
							minDist = d;
							closestTile = coordinate;
							tileFound = true;
						}
					}
				}
			}
		}
		if (tileFound) {
			return closestTile;
		} else {
			return enemy.getPlayer().getCoordinate();
		}
	}
}