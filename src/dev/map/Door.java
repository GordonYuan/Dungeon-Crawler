package dev.map;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Can be opened by a player with a key
 *
 */
public class Door extends Tile{

	private int color;
	public Door(int color) {
		super();
		this.type = Type.door;
		this.setPassable(false);
		this.color = color;
	}
	public int getColor() {
		return color;
	}
	
	@Override
	public void render(GraphicsContext gc, double x, double y) {

		if (this.isPassable()) {
			Image floor = new Image("dev/game/assets/floor.png");
			gc.drawImage(floor, x, y);
			Image door = new Image("dev/game/assets/open_door.png");
			gc.drawImage(door, x, y);
		}
		else {
			Image door = new Image("dev/game/assets/door.png");
			gc.drawImage(door, x, y);
		}
	
	}
	@Override
	public String imagePath() { //Don't use this anymore
		if (!this.isPassable()) {
			return "dev/game/assets/door.png";
		}
		else {
			return "dev/game/assets/open_door.png";
		}
	}

}
