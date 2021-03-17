package dev.map;

import javafx.scene.canvas.GraphicsContext;

public interface staticRenderable {
	public void render(GraphicsContext gc, double x, double y);
	public String imagePath();
}
