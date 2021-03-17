package dev.map;


/**
 * A floor slot with no special tiles on it
 * @author Andy Yu
 *
 */
public class Empty extends Tile{
	
	public Empty() {
		super();
		this.type = Type.empty;
		this.setPassable(true);
	}


	@Override
	public String imagePath() {
		return "dev/game/assets/floor.png";
	}
}