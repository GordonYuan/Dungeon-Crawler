package dev.game.entities;

import dev.map.Map;

/**
 * Interface for teleporting classes, so far only lich
 * @author Andy Yu
 *
 */
public abstract class Teleporter extends Player {

	public Teleporter(int[] coordinate, Map m) {
		super(coordinate, m);
	}
	/**
	 * Teleports a class to a coordinate
	 * @param coordinate
	 */
	public abstract void Teleport(int [] coordinate);
}
