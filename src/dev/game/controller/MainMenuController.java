package dev.game.controller;

import dev.game.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Controller of the main menu screen
 *
 */
public class MainMenuController extends Controller {
	
	private MapStore maps;
	@FXML
	private Button exitButton;
	
	@FXML
	private Button playButton;
	
	@FXML
	private Button createButton;
	
	@FXML
	public void initialize() {
		//BLANK
	}
	
	public MainMenuController(Stage s) {
		super(s);
		this.maps = new MapStore();
	}
	
	public MainMenuController(Stage s,MapStore maps) {
		super(s);
		this.maps = maps;
	}
	
	@FXML
	 public void handlePlayGameButton() {
		Screen mapselectScreen = new Screen(super.getS(), "Crappy 2D Game", "dev/game/display/playMap.fxml");
		
		SelectMapController selectcontroll = new SelectMapController(super.getS(), maps);
		mapselectScreen.start(selectcontroll);
	 }
	  
	@FXML
	public void handleCreateMapButton() {
		Screen mapselectScreen = new Screen(super.getS(), "Crappy 2D Game", "dev/game/display/createMap.fxml");
		
		CreateMapSelector selectcontroll = new CreateMapSelector(super.getS(), maps);
		mapselectScreen.start(selectcontroll);
	}
	
	@FXML
	public void handleExitButton() {
		Stage stage = (Stage) exitButton.getScene().getWindow();
		stage.close();
	}
}