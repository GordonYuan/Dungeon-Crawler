package dev.game.states;

import dev.map.Map;
import javafx.scene.canvas.GraphicsContext;

public interface State {
	
	public abstract void tick();
	public abstract void render(GraphicsContext gc);
	public abstract void setMap(Map map);
}