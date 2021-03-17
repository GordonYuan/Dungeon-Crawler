package dev.game.controller;

import dev.game.MapStore;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Controller of the win game screen.
 *
 */
public class WinGameController extends Controller{
	@FXML
	private Button mainMenu;
	
	@FXML
	public void initialize() {
	}
	private MapStore maps;
	@FXML
	public void handleMainMenuButton() {
		Screen mainMenuScreen = new Screen(super.getS(), "Crappy 2D Game", "dev/game/display/MainMenu.fxml");
		MainMenuController MainMenuController = new MainMenuController(super.getS(),maps);
		mainMenuScreen.start(MainMenuController);
	}
	public WinGameController(Stage s,MapStore maps) {
		super(s);
		this.maps = maps;
	}
}