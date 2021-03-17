package dev.game.controller;

import dev.game.MapStore;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Controller of the lose screen
 *
 */
public class LoseGameController extends Controller{
	@FXML
	private Button mainMenu;
	@FXML
	public void initialize() {
		//BLANK
	}
	
	private MapStore maps;
	@FXML
	public void handleMainMenuButton() {
		Screen mainMenuScreen = new Screen(super.getS(), "Crappy 2D Game", "dev/game/display/MainMenu.fxml");
		MainMenuController MainMenuController = new MainMenuController(super.getS(),maps);
		mainMenuScreen.start(MainMenuController);
	}
	public LoseGameController(Stage s,MapStore maps) {
		super(s);
		this.maps = maps;
	}
}