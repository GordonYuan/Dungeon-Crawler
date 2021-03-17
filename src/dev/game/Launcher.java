package dev.game;

import dev.game.controller.Screen;
import dev.game.controller.MainMenuController;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Launches the game
 *
 */
public class Launcher extends Application {
	
	@Override
	public void start(Stage stage) {
		Screen mainMenuScreen = new Screen(stage, "Crappy 2D Game", "dev/game/display/MainMenu.fxml");
		MainMenuController mmController = new MainMenuController(stage);
		mainMenuScreen.start(mmController);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}