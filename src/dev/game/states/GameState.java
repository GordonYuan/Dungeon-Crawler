package dev.game.states;

import dev.map.Map;
import javafx.scene.canvas.GraphicsContext;

/**
 * In this state both backend and frontend update by the game loop
 *
 */
public class GameState implements State {
	
	private Map map;
	
	public GameState() {
		this.map = null;
	}
	
	public void setMap(Map map) {
		this.map = map;
	}

	public void tick() {
		map.tick();
	}

	@Override
	public void render(GraphicsContext gc) {
		map.render(gc);
	}
	
}
