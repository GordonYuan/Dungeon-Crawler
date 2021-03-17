package dev.game.entities;

import javafx.scene.canvas.GraphicsContext;
/**
 * Interface for renderable objects which move around and store their own location
 * @author Andy Yu
 *
 */
public interface Renderable {
	/**
	 * Gives the location of the sprite of a renderable object
	 * @return The image path for the sprite
	 */
	public abstract String imagePath();
	/**
	 * Renders the object onto the canvas
	 * @param gc The graphics context to render onto
	 */
	public void render(GraphicsContext gc);
}
