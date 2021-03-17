package dev.map;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * A pit which kills boulders and non-hovering creatures that are on it
 *
 */
public class Pit extends Tile{

	public Pit() {
		super();
		this.type = Type.pit;
		this.setPassable(true);;
	}
	
	@Override
	public void render(GraphicsContext gc, double x, double y) {
		Image floor = new Image("dev/game/assets/floor.png");
		gc.drawImage(floor, x, y);
		Image pit = new Image("dev/game/assets/pit.png");
		gc.drawImage(pit, x, y);
	}

	@Override
	public String imagePath() { //Not actually used
		return "dev/game/assets/pit.png";
	}
}
