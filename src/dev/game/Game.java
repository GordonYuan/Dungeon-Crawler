package dev.game;

import java.util.ArrayList;

import dev.game.enemy.*;
import dev.game.entities.*;
import dev.game.entities.Player;
import dev.game.states.CreateMapState;
import dev.game.states.GameState;
import dev.game.states.State;
import dev.map.*;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
/**
 * Manages the game states
 *
 */
public class Game {	
	//States
	private State gameState;
	private State createMapState;
	private Map map;
	private State currentState = null;
	private Player player;
	
	public Game (Map map) {
		this.map = map;
		this.player = null;
	}
	
	
	public void init() {
		gameState = new GameState();
		createMapState = new CreateMapState();
		
		gameState.setMap(this.map);
		this.player = this.map.getPlayer();
		this.setState(gameState);
	}
	public void tick() {
		if (this.currentState != null) {
			this.currentState.tick();
		}
	}
	public void render(GraphicsContext gc) {
		if (this.currentState != null) {
			this.currentState.render(gc);
		}
	}
	public void setState (State state) {
		this.currentState = state;
	}
	public Player getPlayer() {
		return this.player;
	}
}