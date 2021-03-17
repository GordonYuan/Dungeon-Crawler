package dev.game.controller;


import dev.game.Game;
import dev.game.MapStore;
import dev.game.entities.Player;
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
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Select map for the game play mode
 *
 */
public class SelectMapController extends Controller{
	
	 private MapStore maps;
	
	 @FXML
	 private Button map1;
	 @FXML
	 private Button map2;
	 @FXML
	 private Button map3;
	 @FXML
	 private Button map4;
	 @FXML
	 private Button map5;
	 @FXML
	 private Button map6;
	 
	 public SelectMapController(Stage s,MapStore maps) {
			super(s);
			this.maps = maps;
	 }
	 
	 @FXML
	 public void initialize() {
		 switch (maps.getMaps().size()) {
         case 6:  map6.setText(maps.getMaps().get(5).getName());
         case 5:  map5.setText(maps.getMaps().get(4).getName());
         case 4:  map4.setText(maps.getMaps().get(3).getName());
         case 3:  map3.setText(maps.getMaps().get(2).getName());  
         case 2:  map2.setText(maps.getMaps().get(1).getName());
         case 1:  map1.setText(maps.getMaps().get(0).getName());
		 }
         
	 }
	 
	 @FXML
	 private void handlePlayMapButton1() throws CloneNotSupportedException {
		 if(map1.getText().equals("EMPTY")) {
			 return;
		 }
		 Screen gameplayScreen = new Screen(super.getS(), "Crappy 2D Game", "dev/game/display/GameplayView.fxml");
		 GameplayController gameplayController = new GameplayController(super.getS(),  this.maps,0);
		 gameplayScreen.start(gameplayController);
	 }
	 @FXML
	 private void handlePlayMapButton2() throws CloneNotSupportedException {
		 if(map2.getText().equals("EMPTY")) {
			 return;
		 }
		 Screen gameplayScreen = new Screen(super.getS(), "Crappy 2D Game", "dev/game/display/GameplayView.fxml");
		 GameplayController gameplayController = new GameplayController(super.getS(),  this.maps,1);
		 gameplayScreen.start(gameplayController);
	 }
	 @FXML
	 private void handlePlayMapButton3() throws CloneNotSupportedException {
		 if(map3.getText().equals("EMPTY")) {
			 return;
		 }
		 Screen gameplayScreen = new Screen(super.getS(), "Crappy 2D Game", "dev/game/display/GameplayView.fxml");
		 GameplayController gameplayController = new GameplayController(super.getS(),  this.maps,2);
		 gameplayScreen.start(gameplayController);
	 }
	 @FXML
	 private void handlePlayMapButton4() throws CloneNotSupportedException {
		 if(map4.getText().equals("EMPTY")) {
			 return;
		 }
		 Screen gameplayScreen = new Screen(super.getS(), "Crappy 2D Game", "dev/game/display/GameplayView.fxml");
		 GameplayController gameplayController = new GameplayController(super.getS(),  this.maps,3);
		 gameplayScreen.start(gameplayController);
	 }
	 @FXML
	 private void handlePlayMapButton5() throws CloneNotSupportedException {
		 if(map5.getText().equals("EMPTY")) {
			 return;
		 }
		 Screen gameplayScreen = new Screen(super.getS(), "Crappy 2D Game", "dev/game/display/GameplayView.fxml");
		 GameplayController gameplayController = new GameplayController(super.getS(), this.maps,4);
		 gameplayScreen.start(gameplayController);
	 }
	 @FXML
	 private void handlePlayMapButton6() throws CloneNotSupportedException {
		 if(map6.getText().equals("EMPTY")) {
			 return;
		 }
		 Screen gameplayScreen = new Screen(super.getS(), "Crappy 2D Game", "dev/game/display/GameplayView.fxml");
		 GameplayController gameplayController = new GameplayController(super.getS(),  this.maps,5);
		 gameplayScreen.start(gameplayController);
	 }
	 
	 @FXML
	 private void goBack() {
		 Screen mainMenuScreen = new Screen(super.getS(), "Crappy 2D Game", "dev/game/display/MainMenu.fxml");
		 MainMenuController mmController = new MainMenuController(super.getS(),this.maps);
		 mainMenuScreen.start(mmController);
	 }
	 
	
}
