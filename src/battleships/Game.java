package battleships;



import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Game extends Application {
	
	private Ship[][] ships=new Ship[2][5];
	
	
	/**
	 * whole variables for CSS setting
	 */
	private String gridPaneStyle="-fx-border-color: black;\n";
	private String squareGridPaneStyle="-fx-border-color: black;\n";
	private String squareGridPaneStyleOnClick="-fx-background-color: transparent;\n";
	private String squareGridPaneStyleOnTouch="-fx-background-color: gray;\n";
	private String scoreStyle[]={"-fx-background-color: red;\n","-fx-background-color: green;\n"};
	
	private Label label[][]= new Label[10][21];
	
	
	private Player player0;
	private Player player1;
	
	
	
	
	
	@Override
	public void start (Stage stage) throws Exception {
		
		boolean gameSet=setGame(stage);
		
		if(gameSet==true){
			BorderPane gameBorderPane= new BorderPane();
			
			/**
			 * gameBorderPane handling
			 */
			gameBorderPane.setTop(addTitle("BattleShips"));
			gameBorderPane.setCenter(addGridPane());   
			gameBorderPane.setLeft(setPlayer(0));
			gameBorderPane.setRight(setPlayer(1));
			gameBorderPane.setPadding(new Insets(5,5,5,5));
					
			Group root = new Group();
			
			//Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
			
			//stage.setScene(new Scene(root, primaryScreenBounds.getWidth(),
			//		primaryScreenBounds.getHeight()));
			
			Scene gameScene=new Scene(gameBorderPane);
			stage.setScene(gameScene);
		    stage.show();
		}
	}
	
	
	/**
	 * call this method to set a title to the window but inside the borderPage object
	 * @param title
	 * @return
	 */
	public VBox addTitle(String title) {
		
	    VBox vbox = new VBox();
	    
		Label titleLabel=new Label(title);
		
		vbox.getChildren().add(titleLabel);
		vbox.setSpacing(10);
		vbox.setAlignment(Pos.CENTER);
	    vbox.setPadding(new Insets(1, 5, 10, 5)); //top right bottom left

	    return vbox;
	}
	
	/**
	 * call this function to set player informations/labels in the display
	 * @param player
	 * @return
	 */
	public VBox setPlayer(int player){
		
		VBox vbox= new VBox();
		
		/**
		 * set the player name according to the current player selected as parameter
		 */
		if(player == 0){
			vbox.getChildren().add(new Label("Player 1"));
		}
		
		if(player == 1){
			vbox.getChildren().add(new Label("Player 2"));
		}
		
		/**
		 * label d'initialization
		 */
		Label score[]=new Label[5];
		for(int i=0;i<5;i++){
			score[i]=new Label();
		}
		
		/**
		 * this HBox has been created to contains the information labels
		 * of the current player, which are displayed on the screen
		 */
		HBox scoreBox= new HBox();
		scoreBox.setPadding(new Insets(5, 5, 5, 5));
	    scoreBox.setSpacing(10);
	    
	    /**
	     * this labels just show to the player how many time he stroke a ship
	     * if in a first time, he succeeded to hit one 
	     */
		for(int i=0;i<5;i++){
			if(ships[player][i].getShotCount() > 0){
				score[i].setText(String.valueOf(ships[player][i]));
				score[i].setStyle(scoreStyle[0]);
				scoreBox.getChildren().add(score[i]);
			}
		}
		
		vbox.getChildren().add(scoreBox);
		vbox.setAlignment(Pos.CENTER);
		vbox.setPadding(new Insets(0,20,0,20));
		return vbox;
	}
	
	public void labelEffect(int[] labelPosition){
		
		label[labelPosition[0]][labelPosition[1]].setStyle(squareGridPaneStyleOnClick);
	}
	
	public HBox addSpecialGridPane(Stage stage){
		
		Label label[][]= new Label[10][10];
		
		GridPane gridPane= new GridPane();
		
		/** 
		 * General addShipSetting box who contains the gridPane and the shipSelectionBox
		 */
		HBox addShipSettingBox = new HBox();
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setPadding(new Insets(15,15,15,15));
		
		/**
		 * select a ship then pick an orientation, vertical, horizontal, size
		 */
		VBox shipSelectionBox= new VBox();
		shipSelectionBox.setPadding(new Insets(5,5,5,5));
		
		shipSelectionBox.getChildren().add(new Label("Pick a ship"));
		char shipOrientation='v';
		int shipSelected=0;
		
		
		Label[][] shipsToPickLabel= new Label[5][5];
		
		GridPane[] shipsGridPaneToPick = new GridPane[5];
		
		for(int i=0 ; i < 5 ; i++){
			
			shipsGridPaneToPick[i]=new GridPane();
			// Set the the height gap between each label
			shipsGridPaneToPick[i].setHgap(1);
							
			//Set the width gap between each label
			shipsGridPaneToPick[i].setVgap(1);
							
			shipsGridPaneToPick[i].setAlignment(Pos.CENTER);
			shipsGridPaneToPick[i].setPadding(new Insets(1,1,1,1));
			shipsGridPaneToPick[i].setStyle(gridPaneStyle);
			
			int numberCase=0;
			
			if(i==0)
				numberCase=2;
			if(i==1)
				numberCase=3;
			if(i==2)
				numberCase=3;
			if(i==3)
				numberCase=4;
			if(i==4)
				numberCase=5;
			
			for(int j=0 ; j < numberCase ; j++){
				
				shipsToPickLabel[i][j]=new Label("   ");
				shipsToPickLabel[i][j].setStyle(squareGridPaneStyle);
				shipsGridPaneToPick[i].add(shipsToPickLabel[i][j],j,i+1);
				/**
				 * handle each click which has been realized on a case of the ship selection labels
				 */
				final int IJ[]={i,j};
				shipsToPickLabel[i][j].setOnMouseClicked(new EventHandler<MouseEvent>(){
					@Override
					public void handle(MouseEvent mouseEvent) {
						//shipSelected=IJ[0];
							
					}
					
				});
			}
			shipSelectionBox.getChildren().add(shipsGridPaneToPick[i]);
		}
		shipSelectionBox.getChildren().add(new Label("Pick an orientation"));
		
		
		/**
		 * Set the the height gap between each label
		 * Set the width gap between each label
		 */
		// Set the the height gap between each label
		gridPane.setHgap(1);
		
		//Set the width gap between each label
		gridPane.setVgap(1);
		
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setPadding(new Insets(5,5,5,5));
		gridPane.setStyle(gridPaneStyle);

		
		for(int i=0 ; i < 10 ; i++){
			for(int j=0 ; j < 10 ; j++){
				
				label[i][j]=new Label("   ");
				label[i][j].setStyle(squareGridPaneStyle);
				gridPane.add(label[i][j],j,i+1);
				
				/**
				 * handle each click which has been realized on a case
				 */
				final int IJ[]={i,j};
				label[i][j].setOnMouseClicked(new EventHandler<MouseEvent>(){
					@Override
					public void handle(MouseEvent mouseEvent) {
						/**
						 * 
						 *   DESTROYER (2),
						 *   CRUISER (3),
						 *   BATTLESHIP(4),
						 *   CARRIER(5);
						 */
							
						for(int i=0; i<2; i++){
							if(shipSelected==0)
								ships[i][0]=new Ship(ShipType.DESTROYER,shipOrientation,IJ[0],IJ[1]);
							if(shipSelected==1)
								ships[i][1]=new Ship(ShipType.CRUISER,shipOrientation,IJ[0],IJ[1]);
							if(shipSelected==2)
								ships[i][2]=new Ship(ShipType.CRUISER,shipOrientation,IJ[0],IJ[1]); 
							if(shipSelected==3)
								ships[i][3]=new Ship(ShipType.BATTLESHIP,shipOrientation,IJ[0],IJ[1]); 
							if(shipSelected==4)
								ships[i][4]=new Ship(ShipType.CARRIER,shipOrientation,IJ[0],IJ[1]); 
						}
					}
					
				});
			}
		}
		
		addShipSettingBox.getChildren().addAll(shipSelectionBox,gridPane);
	    return addShipSettingBox;
	}
	
	
	public GridPane addGridPane() {
		
		GridPane gridPane= new GridPane();
		
		/**
		 * Set the the height gap between each label
		 * Set the width gap between each label
		 */
		
		// Set the the height gap between each label
		gridPane.setHgap(1); 
		
		//Set the width gap between each label
		gridPane.setVgap(1); 
		
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setPadding(new Insets(5,5,5,5));
		gridPane.setStyle(gridPaneStyle);

		
		for(int i=0 ; i < 10 ; i++){
			for(int j=0 ; j < 21 ; j++){
				
				/** 
				 * Spit the grid in both
				 */
				if(j == 10){
					label[i][j]=new Label(" | ");
					gridPane.add(label[i][j],j,i+1);
				}
				
				//player0 grid
				//from label[0][0] to label [10][9] included
				if(j < 10 ){
					label[i][j]=new Label("   ");
					label[i][j].setStyle(squareGridPaneStyle);
					gridPane.add(label[i][j],j,i+1);
				}
				
				//player1 grid
				//from label[0][11] to label [10][20] included
				if(j > 10 ){
					label[i][j]=new Label("   ");
					label[i][j].setStyle(squareGridPaneStyle);
					gridPane.add(label[i][j],j,i+1);
				}
				/**
				 * handle each click which has been realized on a case
				 */
				final int IJ[]={i,j};
				label[i][j].setOnMouseClicked(new EventHandler<MouseEvent>(){
					@Override
					public void handle(MouseEvent mouseEvent) {
						/**
						 * know then set the current player
						 */
						int currentPlayerId=-1;
						if(IJ[1] < 10)
							currentPlayerId=0;
						if(IJ[1] > 10)
							currentPlayerId=1;
						/**
						 * no matter whether the case clicked got a ship or not
						 * set firstly its color to transparent
						 */
						labelEffect(IJ);

						for(int i=0;i<5;i++){
							/**
							 * know if the label clicked belongs to a ship
							 * then make options
							 */
							if(IJ[0]==ships[currentPlayerId][i].getPosX() && IJ[1]==ships[currentPlayerId][i].getPosY()){
								/** 
								 * if there is a ship touched and its shot number isn't already reached 
								 */
								if(ships[currentPlayerId][i].getShotCount() < ships[currentPlayerId][i].getSize()){
									/**
									 * increase the shotCount of the touched ship
									 * if the square was a ship square, set its color
									 */
									ships[currentPlayerId][i].setShotCount(ships[currentPlayerId][i].getShotCount()+1);
									
									label[IJ[0]][IJ[1]].setStyle(squareGridPaneStyleOnTouch);

								}
								/**
								 * if the ship hit has already completely been found out
								 * then set it to sinked
								 */
								if(ships[currentPlayerId][i].getShotCount() >= ships[currentPlayerId][i].getSize()){
									ships[currentPlayerId][i].setSinked();
								}
								setPlayer(currentPlayerId);
							}
						}
					}
					
				});
			}
		}
		
	    return gridPane;
	}
	
	/**
	 * Call this function to know if someone has already won the game
	 */
	public void gameDone(){
		int sinkedShipsCount=0;
		/**
		 * know how many ship have been touched by each player 
		 * then stop the game if this number is equals to the ships number
		 */
		for(int i=0;i<5;i++){
			if(ships[0][i].getSinked()==true){
				sinkedShipsCount++;
			}
		}
		if(sinkedShipsCount==5){
			System.out.println("End");
			
		}
	}
	public void initializeShip(){
		for(int i=0 ; i < 2; i++){
			
		}
	}
	public VBox addSettingPane(Stage stage){
		
		VBox settingPane=new VBox();
		settingPane.setSpacing(10);
	    settingPane.setAlignment(Pos.CENTER);
	    
		HBox buttons= new HBox();
		buttons.setPadding(new Insets(1, 15, 1, 15));
	    buttons.setSpacing(10);
	    buttons.setAlignment(Pos.CENTER);
	    
	    Button onePlayer=new Button();
	    Button multiPlayer=new Button();
	    onePlayer.setText("one player");
	    multiPlayer.setText("multi player");
	    
	    /**
	     * setOnAction handling for onePlayer button
	     */
	    onePlayer.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				settingPane.getChildren().add(new Label("Please"));
				
				BorderPane gameSettingBorderPane=new BorderPane();
				
				gameSettingBorderPane.setTop(addTitle("BattleShips"));
				
				gameSettingBorderPane.setCenter(addSpecialGridPane(stage));
				gameSettingBorderPane.setPadding(new Insets(5,5,5,5));
				
				Scene gameSettingScene=new Scene(gameSettingBorderPane);
				stage.setScene(gameSettingScene);
			    stage.show();
			}
			
	    });
	    /**
	     * setOnAction handling for multiPlayer button
	     */
	    multiPlayer.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				
			}
	    	
	    });
	    
	    buttons.getChildren().addAll(onePlayer, multiPlayer);
	    
	    Label consign=new Label ("Please set the player number");
	    settingPane.getChildren().addAll(consign,buttons);
	    
	    return settingPane;
	    
	}
	
	/** 
	 * call this method to display the optionsGameSetting as one player or multi player
	 * @param stage
	 * @return
	 */
	public boolean setGame(Stage stage){
		
		BorderPane gameSettingBorderPane=new BorderPane();
		
		/**
		 * gameSettingBorderPane handling
		 */
		gameSettingBorderPane.setTop(addTitle("BattleShips"));
		gameSettingBorderPane.setCenter(addSettingPane(stage));
		gameSettingBorderPane.setPadding(new Insets(5,5,5,5));
		
		Scene gameSettingScene=new Scene(gameSettingBorderPane);
		stage.setScene(gameSettingScene);
	    stage.show();
		
	    
	    return false;
	}
	public static void main(String[]args){
		launch(args);
		
	}
}
