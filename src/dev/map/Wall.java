package dev.map;

/**
 * Impassable tile until destroyed
 *
 */
public class Wall extends Tile{

	public Wall() {
		super();
		this.type = Type.wall;
		this.setPassable(false);
		this.setShouldRender(true);
	}
	

	@Override
	public String imagePath() {
		if (this.isPassable() == false) {
			return "dev/game/assets/wall.png";
		}
		else {
			return "dev/game/assets/pebble_brown6.png";
		}
	}
}
