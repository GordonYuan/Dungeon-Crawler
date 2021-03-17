package dev.map;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * A switch which can be triggered and is a win condition for the game
 */
public class Switcher extends Tile{
	boolean triggered;
	public  Switcher() {
		super();
		this.type = Type.floorswitch;
		this.setPassable(true);;
		this.triggered = false;
	}
	public boolean isTriggered() {
		return this.triggered;
	}
	public void setfulled(boolean condition) {
		this.triggered = condition;
	}
	@Override
	public void render(GraphicsContext gc, double x, double y) {
		Image floor = new Image("dev/game/assets/floor.png");
		gc.drawImage(floor, x, y);
		Image switcher = new Image("dev/game/assets/switcher.png");
		gc.drawImage(switcher, x, y);
	}
	@Override
	public String imagePath() { //Not actually used
		return "dev/game/assets/floor.png";
	}
	public void activate() {
		this.triggered = true;
	}
	public void deactivate() {
		this.triggered = false;
	}
}
