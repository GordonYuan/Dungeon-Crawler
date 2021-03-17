package dev.game.controller;


import dev.game.Game;
import dev.game.MapStore;
import dev.game.entities.Player;
import dev.map.*;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Controller of the gameplay mode
 *
 */
public class GameplayController extends Controller {

	@FXML
	private Canvas gameplayCanvas;
	
	@FXML
	private TextField field1;
	@FXML
	private TextField field2;
	@FXML
	private TextField field3;
	@FXML
	private TextField field4;
	@FXML
	private TextField field5;
	@FXML
	private TextField field6;
	
	//Toggle values
	private static final int EQUIPSWORD = 0;
	private static final int EQUIPARROW = 1;
	
	private Player player;
	private MapStore maps;
	private Map map;
	private int index;
	private int weaponToggle;
	private AnimationTimer gameloop;
	
	public GameplayController(Stage s,MapStore maps,int index) {
		super(s);
		this.player = null;
		this.maps = maps;
		this.index = index;
		this.map = maps.getMaps().get(index);
		this.weaponToggle = EQUIPSWORD;
		this.gameloop = null;
	}
	
	@FXML
	public void initialize() {
		GraphicsContext gc = gameplayCanvas.getGraphicsContext2D();
		Timeline gameLoop = new Timeline();
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        
        this.map.reRender();
        Game game = new Game(this.map);
		game.init();
		this.player = game.getPlayer();
		
		//This is the game loop
		gameloop = new AnimationTimer() {
			private long lastRender = 0;
			private long lastTick = 0;
			@Override
            public void handle(long now) {
				//100ms per tick. 10 ticks per second
            	if (now - lastTick >= 100_000_000) {
            		game.tick();
            		updateFields();
            		lastTick = now;
            	}
            	//33ms per render. 30 FPS
				if (now - lastRender >= 33_000_000) {
            		game.render(gc);
            		lastRender = now;
            	}
            	if (!player.isAlive()) {
            		switchLoseGameScreen();
            	}
            	if (map.checkWin()) {
            		switchWinGameScreen();
            	}
            }
        };
        gameloop.start();
	}
	
	@FXML
	public void handleQuitGame() {
		this.gameloop.stop();
		this.map.reRender();
		Screen mainMenuScreen = new Screen(super.getS(), "Crappy 2D Game", "dev/game/display/MainMenu.fxml");
		MainMenuController mmController = new MainMenuController(super.getS(),maps);
		mainMenuScreen.start(mmController);
	}
	
	@FXML
	public void updateFields() {
		if (player.getSword() != null) {
			field1.setText(String.valueOf(player.getSword().getDurability()));
		} else {
			field1.setText(String.valueOf(0));
		}
		field2.setText(String.valueOf(player.getArrows()));
		if (weaponToggle == EQUIPSWORD) {
			field1.setText(field1.getText() + " (EQUIPPED)");
		} else if (weaponToggle == EQUIPARROW) {
			field2.setText(field2.getText() + " (EQUIPPED)");
		}
		
		field3.setText(String.valueOf(player.getBombs()));
		
		if (player.isImmune()) {
			field4.setText("TRUE");
		} else {
			field4.setText("FALSE");
		}
		if (player.isHovering()) {
			field5.setText("TRUE");
		} else {
			field5.setText("FALSE");
		}
		if (player.getKey() != null) {
			field6.setText("HOLDING KEY");
		} else {
			field6.setText("NO KEY");
		}
		
	}
 	
	@FXML
	public void handleKeyboardInput() {
		Scene scene = gameplayCanvas.getScene();
		
		//Game inputs for the Gameplay Mode
		//TODO: Actually map the keyinputs to backend updates
		//      -  This should be done by calling handleMoveUp(), etc. functions when a key is pressed
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
            	int x = player.getX();
            	int y = player.getY();
            	switch (event.getCode()) {
            	case W:
					y--;
					break;
				case A:
					x--;
					break;
				case S:
					y++;
					break;
				case D:
					x++;
					break;
				}
            	int[] coordinate = {x,y};
            	player.GameMove(coordinate);
            }
        });
		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
            	char direction = '.';
            	switch (event.getCode()) {
            	case UP:
					direction = 'w';
					break;
				case LEFT:
					direction = 'a';
					break;
				case DOWN:
					direction = 's';
					break;
				case RIGHT:
					direction = 'd';
					break;
				case E:
					if (weaponToggle == EQUIPSWORD) {
						weaponToggle = EQUIPARROW;
					} else if (weaponToggle == EQUIPARROW) {
						weaponToggle = EQUIPSWORD;
					}
					break;
				case Q:
					player.dropBomb();
					break;
				}
            	
            	if (weaponToggle == EQUIPSWORD && direction != '.') {
            		player.swingSword(direction);
            	} else if (weaponToggle == EQUIPARROW && direction != '.') {
            		player.shootArrow(direction);
            	}
            }
        });
	}

	@FXML
	public void switchWinGameScreen() {
		this.gameloop.stop();
		this.map.reRender();
		Screen winGameScreen = new Screen(super.getS(), "Crappy 2D Game", "dev/game/display/WinGame.fxml");
		WinGameController winGameController = new WinGameController(super.getS(),maps);
		winGameScreen.start(winGameController);
	}
	
	@FXML
	public void switchLoseGameScreen() {
		this.gameloop.stop();
		this.map.reRender();
		Screen loseGameScreen = new Screen(super.getS(), "Crappy 2D Game", "dev/game/display/LoseGame.fxml");
		LoseGameController loseGameController = new LoseGameController(super.getS(),maps);
		loseGameScreen.start(loseGameController);
	}
}