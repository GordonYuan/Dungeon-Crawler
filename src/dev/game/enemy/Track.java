package dev.game.enemy;

import dev.map.Map;
import dev.game.entities.Player;
import java.util.Arrays;

import dev.game.enemy.Hound;

public class Track implements MoveBehaviour {
	/**
	 * Creates vector with the position of the hunter and the player and returns the hunter
	 * position on the other side of the player relative to the hunter and player.
	 */
	@Override
	public int [] decideDestination(Enemy enemy) {
		Hound hound = (Hound) enemy;
		Player player = enemy.getPlayer();
		Hunter owner = hound.getOwner();
		int [] desiredL = new int [2];
		int [] playerL = player.getCoordinate();
		int [] ownerL = owner.getCoordinate();
		Map map = enemy.getMap();
		
		if (Arrays.equals(enemy.getCoordinate(), playerL)) {
			return enemy.getCoordinate();
		}
		for (int i = 1; i < 4; i++) {
			if (playerL[0] == ownerL[0]) { // infinite gradient
				if (playerL[1] < ownerL[1]) { // if player is below hunter, hound must be below player
					if (playerL[1] - i >= 0) {
						desiredL[0] = playerL[0];
						desiredL[1] = playerL[1] - i;
					}
				} else if (playerL[1] > ownerL[1]) { // if player is above hunter, hound must be above player
					if (playerL[1] + i < map.getHeight()) {
						desiredL[0] = playerL[0];
						desiredL[1] = playerL[1] + i;
					}
				}
			} else if (playerL[1] == ownerL[1]) {
				if (playerL[0] < ownerL[0]) {
					if (playerL[0] - i >= 0) {
						desiredL[0] = playerL[0] - i;
						desiredL[1] = playerL[1];
					}
				} else if (playerL[0] > ownerL[0]) {
					if (playerL[0] + i < map.getWidth()) {
						desiredL[0] = playerL[0] + i;
						desiredL[1] = playerL[1];
					}
				}
			} else if (playerL[0] > ownerL[0] && playerL[1] > ownerL[1]) {
				if (playerL[0] + i < map.getWidth() && playerL[1] + i < map.getHeight()) {
					desiredL[0] = playerL[0] + i;
					desiredL[1] = playerL[1] + i;
				}
			} else if (playerL[0] < ownerL[0] && playerL[1] < ownerL[1]) {
				if (playerL[0] - i >= 0 && playerL[1] + i >= 0) {
					desiredL[0] = playerL[0] - i;
					desiredL[1] = playerL[1] - i;
				}
			} else if (playerL[0] > ownerL[0] && playerL[1] < ownerL[1]) {
				if (playerL[0] + i < map.getWidth() && playerL[1] - i >= 0) {
					desiredL[0] = playerL[0] + i;
					desiredL[1] = playerL[1] - i;
				}
			} else if (playerL[0] < ownerL[0] && playerL[1] > ownerL[1]) {
				if (playerL[0] - i >= 0 && playerL[1] + i < map.getHeight()) {
					desiredL[0] = playerL[0] - i;
					desiredL[1] = playerL[1] + i;
				}
			}
			
			if (map.isPassable(desiredL) && map.hasEnemy(desiredL) == null) {
				return desiredL;
			}
		}
		return enemy.getCoordinate();
	}
}