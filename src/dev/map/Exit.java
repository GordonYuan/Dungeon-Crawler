package dev.map;

/**
 * The dungeon exit
 *
 */
public class Exit extends Tile{

	public Exit() {
		super();
		this.type = Type.exit;
		this.setPassable(true);
	}
	

	@Override
	public String imagePath() {
		return "dev/game/assets/exit.png";
	}
}
