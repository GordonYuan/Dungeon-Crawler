package dev.game.states;

import dev.game.Game;
import dev.map.Map;
import javafx.scene.canvas.GraphicsContext;

/**
 * In this state, only the front end is updated by the game loop
 *
 */
public class CreateMapState implements State {
	
	Map map;
	
	public CreateMapState() {
		map = null;
	}

	public void tick() {
	}

	@Override
	public void setMap(Map map) {
		this.map = map;
	}

	@Override
	public void render(GraphicsContext gc) {
		map.render(gc);
	}
}
