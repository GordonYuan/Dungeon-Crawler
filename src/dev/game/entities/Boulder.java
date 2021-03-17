package dev.game.entities;

import java.util.Arrays;

import dev.map.Map;
import dev.map.Type;
/**
 * Can be pushed onto switches, or otherwise pushed around
 * @author Andy Yu
 *
 */
public class Boulder extends Entity {

	public Boulder(int[] coordinate,Map m) {
		super(coordinate,m, Type.boulder);
	}

	/**
	 * Tries to get a player to push a boulder
	 * @pre Assumes done as part of a valid game movement - i.e. the player is already
	 * adjacent to the boulder
	 * @param p - The player pushing the boulder
	 * @return True if boulder can be pushed, false otherwise
	 */
	public boolean pushBoulder(Player p) { //Tries to get a player to push the boulder
		//If the player is in map editing mode they can't push the boulder
		if (p.isMapEditing()) {
			return false;
		}
		int [] previousLocation = Arrays.copyOf(this.getCoordinate(), 2);
		//First determine the direction the boulder should be pushed
		int[] pushDirection = new int[2];
		pushDirection[0] = this.getCoordinate()[0] - p.getCoordinate()[0]; 
		if (pushDirection[0] > 1) {
			pushDirection[0] = 1;
		}
		else if (pushDirection[0] < -1) {
			pushDirection[0] = -1;
		}
		pushDirection[1] = this.getCoordinate()[1] - p.getCoordinate()[1];
		if (pushDirection[1] > 1) {
			pushDirection[1] = 1;
		}
		else if (pushDirection[1] < -1) {
			pushDirection[1] = -1;
		}
		int[] destination = new int[2];
		destination[0] = this.getCoordinate()[0] + pushDirection[0];
		destination[1] = this.getCoordinate()[1] + pushDirection[1];
		try {
			this.move(destination);
			//If no exception occurred need to turn off/on switches
			map.deactivateSwitch(previousLocation);
			map.activateSwitch(this.getCoordinate());
			if (this.map.isPit(destination)) {this.map.delete(this);}
			return true;
		}
		catch (IllegalArgumentException e) {
			return false;
		}
		//The above pushes boulder away from player
	}
	@Override
	public boolean terrainCheck(int[] coordinate) {
		//Boulder can't be pushed onto any entity
		if (!this.map.isOnMap(coordinate)) return false;
		if (this.map.hasEntity(coordinate)) {
			return false;
		}
		if (!this.map.isPassable(coordinate)) return false;
		
		return true;
	}

	@Override
	public boolean hasClash(Entity e) {
		if (e!=null) return true;
		return false;
	}


	@Override
	public String imagePath() {
		return "dev/game/assets/boulder.png";
	}
	
	
	
}
