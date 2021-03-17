package dev.game.controller;

import dev.game.Game;
import dev.game.enemy.*;

import java.util.Arrays;


import dev.game.*;
import dev.game.entities.*;
import dev.game.entities.Player;
import dev.game.states.CreateMapState;
import dev.map.*;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Controller of the create map mode.
 *
 */
public class MapCreateController extends Controller {

	private MapStore maps;
	private int index;
	private Player currplayer;
	
	private Map currmap;
	@FXML
	private BorderPane bp;
	@FXML
	private StackPane sp;
	@FXML
	private TextField width;
	@FXML
	private TextField height;
	@FXML
	private TextField mapname;
	
	@FXML
	private Button empty;
	@FXML
	private Button door;
	@FXML
	private Button exit;
	@FXML
	private Button pit;
	@FXML
	private Button switcher;
	@FXML
	private Button wall;
	@FXML
	private Button coward;
	@FXML
	private Button hound;
	@FXML
	private Button hunter;
	@FXML
	private Button strategist;
	@FXML
	private Button player;
	@FXML
	private Button arrow;
	@FXML
	private Button bomb;
	@FXML
	private Button boulder;
	@FXML
	private Button hoverpot;
	@FXML
	private Button invulnpot;
	@FXML
	private Button key;
	@FXML
	private Button sword;
	@FXML
	private Button treasure;
	@FXML
	private Button mage;
	@FXML
	private Button bomber;
	@FXML
	private Button gamemaster;
	@FXML
	private Button archer;
	@FXML
	private Button lich;
	
	@FXML
	private Canvas createMapCanvas;
	
	@FXML
	private TextField field1;
	
	private Tile currtile;
	private Entity currentity;
	private Game game;
	private AnimationTimer gameloop;
	private GraphicsContext gc;
	/*
	private static int LOCKED = 1;
	private static int UNLOCKED = 0;
	protected int lockedDown = UNLOCKED;
	*/
	
	public MapCreateController(Stage s,MapStore maps,int index) {
		super(s);
		this.maps = maps;
		this.index = index;
		this.currentity = null;
		this.currtile = null;
		this.currplayer = null;
		this.currmap = null;
		this.game = null;
		this.gc = null;
	}
	
	@FXML
	public void initialize() {
		Image i1 = new Image("dev/game/assets/floor.png");
		empty.setGraphic(new ImageView(i1));
		Image i2 = new Image("dev/game/assets/door.png");
		door.setGraphic(new ImageView(i2));
		Image i3 = new Image("dev/game/assets/exit.png");
		exit.setGraphic(new ImageView(i3));
		Image i4 = new Image("dev/game/assets/pit.png");
		pit.setGraphic(new ImageView(i4));
		Image i5 = new Image("dev/game/assets/switcher.png");
		switcher.setGraphic(new ImageView(i5));
		Image i6 = new Image("dev/game/assets/wall.png");
		wall.setGraphic(new ImageView(i6));
		Image i7 = new Image("dev/game/assets/coward.png");
		coward.setGraphic(new ImageView(i7));
		Image i8 = new Image("dev/game/assets/hound.png");
		hound.setGraphic(new ImageView(i8));
		Image i9 = new Image("dev/game/assets/strategist.png");
		strategist.setGraphic(new ImageView(i9));
		Image i10 = new Image("dev/game/assets/hunter.png");
		hunter.setGraphic(new ImageView(i10));
		Image i11 = new Image("dev/game/assets/human_new.png");
		player.setGraphic(new ImageView(i11));
		Image i12 = new Image("dev/game/assets/arrow.png");
		arrow.setGraphic(new ImageView(i12));
		Image i13 = new Image("dev/game/assets/bomb_unlit.png");
		bomb.setGraphic(new ImageView(i13));
		Image i14 = new Image("dev/game/assets/boulder.png");
		boulder.setGraphic(new ImageView(i14));
		Image i15 = new Image("dev/game/assets/hoverpot.png");
		hoverpot.setGraphic(new ImageView(i15));
		Image i16 = new Image("dev/game/assets/invulnPot.png");
		invulnpot.setGraphic(new ImageView(i16));
		Image i17 = new Image("dev/game/assets/key.png");
		key.setGraphic(new ImageView(i17));
		Image i19 = new Image("dev/game/assets/sword.png");
		sword.setGraphic(new ImageView(i19));
		Image i20 = new Image("dev/game/assets/Treasure.png");
		treasure.setGraphic(new ImageView(i20));
		Image i21 = new Image("dev/game/assets/orc_wizard.png");
		mage.setGraphic(new ImageView(i21));
		Image i22 = new Image("dev/game/assets/bomber.png");
		bomber.setGraphic(new ImageView(i22));
		Image i23 = new Image("dev/game/assets/angel.png");
		gamemaster.setGraphic(new ImageView(i23));
		Image i24 = new Image("dev/game/assets/centaur.png");
		archer.setGraphic(new ImageView(i24));
		Image i25 = new Image("dev/game/assets/lich.png");
		lich.setGraphic(new ImageView(i25));
		
		
		//initialising the current map
		if(maps.getMaps().size() > index) {
			currmap = maps.getMaps().get(index);
		} else {
			currmap = new Map(10,10,"DEFAULT_MAP");
			maps.saveMap(currmap);
			
		}
		mapname.setText(currmap.getName());
		width.setText(String.valueOf(currmap.getWidth()));
		height.setText(String.valueOf(currmap.getHeight()));
		bp.setCenter(table(currmap.getWidth(),currmap.getHeight()));
		CreateMapState cms = new CreateMapState();
		cms.setMap(currmap);
		
		game = new Game(currmap);
		game.setState(cms);
		
		gc = createMapCanvas.getGraphicsContext2D();
		Timeline gameLoop = new Timeline();
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        
        gameloop = new AnimationTimer() {
			private long lastRender = 0;
			@Override
            public void handle(long now) {
				if (now - lastRender >= 33_000_000) {
					game.render(gc);
            		lastRender = now;
            	}
            }
        };
        gameloop.start();
	}
	
	
	@FXML
	private void generateMap() {
		int mapwidth = Integer.parseInt(width.getText());
		int mapheight = Integer.parseInt(height.getText());
		String mapn = mapname.getText();
		
		if(maps.getMaps().size() > index) {
			if(mapwidth == currmap.getWidth() && mapheight == currmap.getHeight())
			{
				currmap.setName(mapn);
			}
			else
			{
				gc.clearRect(0, 0, 640, 640);
				Map newmap = new Map(mapwidth, mapheight, mapn);
				maps.updateMap(newmap, index);
				currmap = maps.getMaps().get(index);
			}
		}
		else {
			this.index = maps.getMaps().size();
			Map newmap;
			if((newmap = maps.createMap(mapn, mapwidth, mapheight))!= null) {
				gc.clearRect(0, 0, 640, 640);
				maps.updateMap(newmap, index);
			}
			else {
				//name duplicate
				//need to handle
				
			}
		}
		currmap = maps.getMaps().get(index);
		CreateMapState cms = new CreateMapState();
		cms.setMap(currmap);
		game.setState(cms);
		bp.setCenter(table(mapwidth,mapheight));
	}
	
	/**
	 * Used to add tiles and entities depending on where you click on the screen
	 * @param width
	 * @param height
	 * @return
	 */
	private StackPane table(int width, int height) {
		createMapCanvas = new Canvas(640,640);
		
		GridPane grid = new GridPane();
		grid.setPrefSize(640, 640);
	     // never size the gridpane larger than its preferred size:
	    grid.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		for(int i=0; i<20; i++){
			for(int k=0;k<20;k++) {
				Button button = new Button();
				button.setStyle("-fx-background-color: transparent;");
				button.setPrefSize(640/20,640/20);
				button.setMinSize(640/20,640/20);
				button.setMaxSize(640/20,640/20);
				button.addEventHandler(MouseEvent.MOUSE_CLICKED, 
					    new EventHandler<MouseEvent>() {
							int x;
							int y;
							@Override public void handle(MouseEvent e) {
					        	x = grid.getColumnIndex((Button) e.getSource());
				            	y = grid.getRowIndex((Button) e.getSource());
					            if(currtile != null) {
					            	switch(currtile.getType()) {
					            	case(Type.empty):{
					            		Empty newtile = new Empty();
					            		currmap.addTiles(newtile, x, y);
					            		break;
					            	}
					            	case(Type.exit):{
					            		Exit newtile = new Exit();
					            		currmap.addTiles(newtile, x, y);
					            		break;
					            	}
					            	case(Type.pit):{
					            		Pit newtile = new Pit();
					            		currmap.addTiles(newtile, x, y);
					            		break;
					            	}
					            	case(Type.door):{
					            		Door newtile = new Door(currmap.doorCount());
					            		currmap.addTiles(newtile, x, y);
					            		break;
					            	}
					            	case(Type.wall):{
					            		Wall newtile = new Wall();
					            		currmap.addTiles(newtile, x, y);
					            		break;
					            	}
					            	case(Type.floorswitch):{
					            		Switcher newtile = new Switcher();
					            		currmap.addTiles(newtile, x, y);
					            		break;
					            	}
					            	}
					            	
					            }
					            else if(currentity != null){
					            	int[] coor = new int[] {x,y};
					            	Entity newen = null;
					            	switch(currentity.getType()) {
					            	case(Type.arrow):{
					            		newen = new Arrow(coor, currmap);
					            		break;
					            	}
					            	case(Type.bomb):{
					            		newen = new Bomb(coor, currmap);
					            		break;
					            	}
					            	case(Type.coward):{
					            		newen = new Coward(coor, currmap, currplayer);
					            		break;
					            	}
					            	case(Type.hound):{
					            		newen = new Hound(coor, currmap, currplayer);
					            		//Tries to find an existing hunter on the map to attach to
					            		((Hound) newen).findAHunter();
					            		break;
					            	}
					            	case(Type.hunter):{
					            		newen = new Hunter(coor, currmap, currplayer);
					            		break;
					            	}
					            	case(Type.strategist):{
					            		newen = new Strategist(coor, currmap, currplayer);
					            		break;
					            	}
					            	case(Type.boulder):{
					            		newen = new Boulder(coor, currmap);
					            		break;
					            	}
					            	case(Type.hover):{
					            		newen = new HoverPot(coor, currmap);
					            		break;
					            	}
					            	case(Type.invincibility):{
					            		newen = new InvulnPot(coor, currmap);
					            		break;
					            	}
					            	case(Type.key):{
					            		newen = new Key(coor, currmap, currmap.keyCount());
					            		break;
					            	}
					            	case(Type.sword):{
					            		newen = new Sword(coor, currmap);
					            		break;
					            	}

					            	case(Type.lich):{
					            		if (currmap.getPlayer() != null) {
					            			currmap.delete(currmap.getPlayer());
					            		}
					            		newen = new Lich(coor, currmap);
					            		currplayer = (Player) newen;
					            		currmap.initialisePlayerTarget((Player) newen);
					            		break;
					            	}
					            	case(Type.treasure):{
					            		newen = new Treasure(coor, currmap);
					            		break;
					            	}
					            	case(Type.mage):{
					            		if (currmap.getPlayer() != null) {
					            			currmap.delete(currmap.getPlayer());
					            		}
					            		newen = new Mage(coor, currmap);
					            		currplayer = (Player) newen;
					            		currmap.initialisePlayerTarget((Player) newen);
					            		break;
					            	}
					            	case(Type.bomber):{
					            		if (currmap.getPlayer() != null) {
					            			currmap.delete(currmap.getPlayer());
					            		}
					            		newen = new Bomber(coor, currmap);
					            		currplayer = (Player) newen;
					            		currmap.initialisePlayerTarget((Player) newen);
					            		break;
					            	}
					            	case(Type.gamemaster):{
					            		if (currmap.getPlayer() != null) {
					            			currmap.delete(currmap.getPlayer());
					            		}
					            		newen = new GameMaster(coor, currmap);
					            		currplayer = (Player) newen;
					            		currmap.initialisePlayerTarget((Player) newen);
					            		break;
					            	}
					            	case(Type.archer):{
					            		if (currmap.getPlayer() != null) {
					            			currmap.delete(currmap.getPlayer());
					            		}
					            		newen = new Archer(coor, currmap);
					            		currplayer = (Player) newen;
					            		currmap.initialisePlayerTarget((Player) newen);
					            		break;
					            	}
					            	case(Type.player):{
					            		if (currmap.getPlayer() != null) {
					            			currmap.delete(currmap.getPlayer());
					            		}
					            		newen = new Player(coor, currmap);
					            		currplayer = (Player) newen;
					            		currmap.initialisePlayerTarget((Player) newen);
					            		break;
					            	}
					            	}
					          
					            	currmap.addEntity(newen);
					            }
					            else {
					            	Empty empty = new Empty();
					            	currmap.addTiles(empty, x, y);
					            }
					        }
					});
				grid.add(button, i, k);
			}
	     }
		ObservableList<Node> list = this.sp.getChildren();

		list.addAll(createMapCanvas, grid); 
		return this.sp;
		
	}
	

	@FXML
	public void assignDefaultMap() {
		Map newmap = new Map(10, 10, "DEFAULT_NAME");
		currmap = newmap;
	}
	@FXML
	public void emptyClick() {
		Empty empty = new Empty();
		this.currtile = empty;
		this.currentity = null;
	}
	@FXML
	public void exitClick() {
		Exit empty = new Exit();
		this.currtile = empty;
		this.currentity = null;
	}
	@FXML
	public void doorClick() {
		Door empty = new Door(1);
		this.currtile = empty;
		this.currentity = null;
	}
	@FXML
	public void pitClick() {
		Pit empty = new Pit();
		this.currtile = empty;
		this.currentity = null;
	}
	@FXML
	public void switcherClick() {
		Switcher empty = new Switcher();
		this.currtile = empty;
		this.currentity = null;
	}
	@FXML
	public void wallClick() {
		Wall empty = new Wall();
		this.currtile = empty;
		this.currentity = null;
	}
	
	@FXML
	public void cowardClick() {
		int[] coor = new int[] {1,1};
		Coward ent = new Coward(coor, currmap, this.currplayer);
		this.currentity = ent;
		this.currtile = null;
	}
	@FXML
	public void houndClick() {
		int[] coor = new int[] {1,1};
		Hound ent = new Hound(coor, currmap, this.currplayer);
		this.currentity = ent;
		this.currtile = null;
	}
	@FXML
	public void strategistClick() {
		int[] coor = new int[] {1,1};
		Strategist ent = new Strategist(coor, currmap, this.currplayer);
		this.currentity = ent;
		this.currtile = null;
	}
	@FXML
	public void hunterClick() {
		int[] coor = new int[] {1,1};
		Hunter ent = new Hunter(coor, currmap, this.currplayer);
		this.currentity = ent;
		this.currtile = null;
	}
	@FXML
	public void playerClick() {
		int[] coor = new int[] {1,1};
		Player ent = new Player(coor, currmap);
		this.currentity = ent;
		this.currtile = null;
	}
	@FXML
	public void arrowClick() {
		int[] coor = new int[] {1,1};
		Arrow ent = new Arrow(coor, currmap);
		this.currentity = ent;
		this.currtile = null;
	}
	@FXML
	public void bombClick() {
		int[] coor = new int[] {1,1};
		Bomb ent = new Bomb(coor, currmap);
		this.currentity = ent;
		this.currtile = null;
	}
	@FXML
	public void boulderClick() {
		int[] coor = new int[] {1,1};
		Boulder ent = new Boulder(coor, currmap);
		this.currentity = ent;
		this.currtile = null;
	}
	@FXML
	public void hoverpotClick() {
		int[] coor = new int[] {1,1};
		HoverPot ent = new HoverPot(coor, currmap);
		this.currentity = ent;
		this.currtile = null;
	}
	@FXML
	public void invulnpotClick() {
		int[] coor = new int[] {1,1};
		InvulnPot ent = new InvulnPot(coor, currmap);
		this.currentity = ent;
		this.currtile = null;
	}
	@FXML
	public void keyClick() {
		int[] coor = new int[] {1,1};
		Key ent = new Key(coor, currmap, 1);
		this.currentity = ent;
		this.currtile = null;
	}
	
	@FXML
	public void swordClick() {
		int[] coor = new int[] {1,1};
		Sword ent = new Sword(coor, currmap);
		this.currentity = ent;
		this.currtile = null;
	}
	
	@FXML
	public void treasureClick() {
		int[] coor = new int[] {1,1};
		Treasure ent = new Treasure(coor, currmap);
		this.currentity = ent;
		this.currtile = null;
	}
	
	@FXML
	public void mageClick() {
		int[] coor = new int[] {1,1};
		Mage ent = new Mage(coor, currmap);
		this.currentity = ent;
		this.currtile = null;
	}
	
	@FXML
	public void bomberClick() {
		int[] coor = new int[] {1,1};
		Bomber ent = new Bomber(coor, currmap);
		this.currentity = ent;
		this.currtile = null;
	}
	
	@FXML
	public void gamemasterClick() {
		int[] coor = new int[] {1,1};
		GameMaster ent = new GameMaster(coor, currmap);
		this.currentity = ent;
		this.currtile = null;
	}
	
	@FXML
	public void archerClick() {
		int[] coor = new int[] {1,1};
		Archer ent = new Archer(coor, currmap);
		this.currentity = ent;
		this.currtile = null;
	}
	
	@FXML
	public void lichClick() {
		int[] coor = new int[] {1,1};
		Lich ent = new Lich(coor, currmap);
		this.currentity = ent;
		this.currtile = null;
	}
	
	
	public static boolean contains(final int[] arr, final int key) {
	    return Arrays.stream(arr).anyMatch(i -> i == key);
	}
	/**
	 * Saves the map and its changes
	 */
	@FXML
	public void saveClick() {
		//Conditions which must be met before you can save the map
		if (currmap.keyCount() != currmap.doorCount()) {
			field1.setText("No. keys != No. Doors");
			return;
		}
		if (currmap.getPlayer() == null) {
			field1.setText("Add a player!");
			return;
		}
		this.gameloop.stop();
		//Since the tile state is preserved, isRender for each tile would be false. reRender() simply
		//sets them all to true again.
		currmap.reRender();
		
		Screen mapselectScreen = new Screen(super.getS(), "Crappy 2D Game", "dev/game/display/createMap.fxml");
		CreateMapSelector selectcontroll = new CreateMapSelector(super.getS(), maps);
		mapselectScreen.start(selectcontroll);
	}
	
	@FXML
	public void addKill() {
		WinKill wink = new WinKill(currmap);
		currmap.addWinconditions(wink);
	}
	@FXML
	public void addPush() {
		WinPush winp = new WinPush(currmap);
		currmap.addWinconditions(winp);
		
	}
	@FXML
	public void addTreasure() {
		WinTreasure wint = new WinTreasure(currmap);
		currmap.addWinconditions(wint);
		
	}
	@FXML
	public void addExit() {
		WinExit wine = new WinExit(currmap);
		currmap.addWinconditions(wine);
	}
	@FXML
	public void clearConditions() {
		currmap.deleteConditions();
	}
}