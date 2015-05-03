package battleships;




import java.util.Random;

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
	
	Case[][] casesPlayer0=new Case[10][10];
	Case[][] casesPlayer1=new Case[10][10];

	
	
	/**
	 * whole variables for CSS setting
	 */
	private String backgroundNull="-fx-background-color: transparent;\n";
	private String backgroundSelect="-fx-background-color: gray;\n";
	private String orientationChoiceBackgroung="-fx-background-color: gray;\n";
	private String shipPickingSquareGridPaneStyleOnClick="-fx-background-color: gray;\n";

	private String gridPaneStyle="-fx-border-color: black;\n";
	private String squareGridPaneStyle="-fx-border-color: black;\n";
	private String squareGridPaneStyleOnClick="-fx-background-color: transparent;\n";
	private String squareGridPaneStyleOnTouch="-fx-background-color: gray;\n";
	private String scoreStyle[]={"-fx-background-color: red;\n","-fx-background-color: green;\n"};
	
	private Label[][] gridPlayer0= new Label[10][10];
	private Label[][] gridPlayer1= new Label[10][10];
	
	private Player player0;
	private Player player1;
	
	private Label shipSelectPosLabelPlayer0[][]= new Label[10][10];
	private Label shipSelectPosLabelPlayer1[][]= new Label[10][10];
	/**
	 * Variable created to know on which ship the user has realized his click during the ship setting section
	 */
	int shipSelected=-1;
	
	char shipOrientation='n';
	
	int shipPosLabelIJ[]=new int[2];

	
	@Override
	public void start (Stage stage) throws Exception {
		
		setGame(stage);
		
		/**
	     * ship initialization
		 * 
		 *   DESTROYER (2),
		 *   CRUISER1 (3),
		 *   CRUISER2 (3),
		 *   BATTLESHIP(4),
		 *   CARRIER(5);
		 */
			
		for(int i = 0; i < 2; i++){
				ships[i][0]=new Ship(ShipType.DESTROYER);
				ships[i][1]=new Ship(ShipType.CRUISER1);
				ships[i][2]=new Ship(ShipType.CRUISER2);
				ships[i][3]=new Ship(ShipType.BATTLESHIP); 
				ships[i][4]=new Ship(ShipType.CARRIER);
		}
		/**
		 * Cases initializations
		 */
		for(int i = 0 ; i < 10 ; i++){
			for(int j = 0 ; j < 10 ; j++){
				casesPlayer0[i][j]=new Case(CaseContents.UNKNOWN);
				casesPlayer1[i][j]=new Case(CaseContents.UNKNOWN);
			}
		}
	}

	
	/** 
	 * call this method to display the optionsGameSetting as one player or multi player
	 * @param stage
	 * @return
	 */
	public void setGame(Stage stage){
		
		BorderPane gameSettingBorderPane=new BorderPane();
		
		/**
		 * gameSettingBorderPane window handling
		 */
		gameSettingBorderPane.setTop(addTitle("BattleShips"));
		gameSettingBorderPane.setCenter(addPlayerModePane(stage));
		gameSettingBorderPane.setPadding(new Insets(5,5,5,5));
		
		Scene gameSettingScene=new Scene(gameSettingBorderPane);
		stage.setScene(gameSettingScene);
	    stage.show();
		
	}
	public static void main(String[]args){
		launch(args);
		
	}
	/**
	 * method called by setGame to display the ships board setting on the grid
	 * @param stage
	 * @return
	 */
	public VBox addPlayerModePane(Stage stage){
		
		VBox settingPane=new VBox();
		settingPane.setSpacing(10);
	    settingPane.setAlignment(Pos.CENTER);
	    
		HBox buttons= new HBox();
		buttons.setPadding(new Insets(1, 15, 1, 15)); //top right bottom left
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
				
				BorderPane shipSettingBorderPane=new BorderPane();
				
				
				shipSettingBorderPane.setTop(addTitle("BattleShips"));
				
				shipSettingBorderPane.setCenter(addSingleGridPane(stage));
				
				/**
				 * Launch the game by clicking on the play button
				 * if all ships have been set, hence, the game will start
				 */
				Button playButton =new Button ("play");
				playButton.setAlignment(Pos.CENTER);
				
				VBox play=new VBox();
				play.setAlignment(Pos.CENTER);
				play.setPadding(new Insets(15,5,15,5)); //top right bottom left
				play.getChildren().add(playButton);
				
				playButton.setOnAction(new EventHandler<ActionEvent>(){
				
					int shipId=0;
					char randomShipOrientation;
					
					@Override
					public void handle(ActionEvent arg0) {
						
						boolean playAction=false;
						/**
						 * know if all ships have been set
						 * and if it is, launch the game
						 */
						for(int j=0 ; j < 5 ;j++){
							if(ships[0][j].getSet()==false){
								playAction=false;
								break;
							}
							else {
								playAction=true;
							}
						}
						if(playAction==true){
							
							player0=new Player(0,"player1",shipSelectPosLabelPlayer0);
							player1=new Player(1,"computer",shipSelectPosLabelPlayer1);
							
							BorderPane gameBorderPane= new BorderPane();
							
							/**
							 * gameBorderPane handling
							 */
							gameBorderPane.setTop(addTitle("BattleShips"));
							gameBorderPane.setCenter(addDoubleMixGridPane(gameBorderPane));   
							gameBorderPane.setLeft(setPlayer(0));
							gameBorderPane.setRight(setPlayer(1));
							gameBorderPane.setPadding(new Insets(5,5,5,5));
									
							Group root = new Group();
							
							Scene gameScene=new Scene(gameBorderPane);
							stage.setScene(gameScene);
						    stage.show();
						}
						/**
						 * 
						 * 
						 * Setting computer ships positions by random
						 * 
						 */
						
						/**
						 * refresh and create the labels
						 */
						for(int i=0 ; i < 10 ; i++){
							for(int j=0 ; j < 10 ; j++){
								
								shipSelectPosLabelPlayer1[i][j]=new Label("   ");
								shipSelectPosLabelPlayer1[i][j].setStyle(squareGridPaneStyle);
							}
				    	}
						boolean setRandomlyAction=false;
						
						while(setRandomlyAction==false){
							/**
							 * know if all ships have been set
							 * and if it is, launch the game
							 */
							for(int j=0 ; j < 5 ;j++){
								if(ships[1][j].getSet()==false){
									setRandomlyAction=false;
									break;
								}
								else {
									setRandomlyAction=true;
								}
							}
							for(shipId = 0; shipId < 5 ; shipId++){
								/** 
								 * set orientation randomly
								 */
								Random r=new Random();
								
								int randomOrientation=r.nextInt(2);
								
								if(randomOrientation==0)
									randomShipOrientation='v';
								if(randomOrientation==1)
									randomShipOrientation='h';
								
								if(randomShipOrientation!='n' && shipId!=-1 && ships[1][shipId].getSet()==false){
									/** 
									 * find x and y positions at random
									 */
									Random r2=new Random();

									int randomPosX=r2.nextInt(10);
									int randomPosY=r2.nextInt(10);
									
									if(isEmpty(randomPosX,randomPosY)==true){
										if(randomShipOrientation=='v'){
											/**
											 * 
											 *   DESTROYER (2),
											 *   CRUISER1 (3),
											 *   CRUISER2 (3),
											 *   BATTLESHIP(4),
											 *   CARRIER(5);
											 */
											/**
											 * check if the case is reachable
											 */	
											boolean persistentAction=false;
											
											
											for(int i=randomPosX;
													i < randomPosX+ships[1][shipId].getSize() && i < 10
													&& randomPosX+ships[1][shipId].getSize() -1 < 10 ; i++){
												
												int j=randomPosY;
												shipSelectPosLabelPlayer1[i][j].setText("||||");
												shipSelectPosLabelPlayer1[i][j].setStyle(backgroundSelect);
												setCaseContents(ships[1][shipId],i,j);
												persistentAction=true;
											}
											if(persistentAction==true)
												setShip(1,shipId,randomPosX,randomPosY);
										}
										
										if(randomShipOrientation=='h'){
											/**
											 * 
											 *   DESTROYER (2),
											 *   CRUISER1 (3),
											 *   CRUISER2 (3),
											 *   BATTLESHIP(4),
											 *   CARRIER(5);
											 */
											/**
											 * check if the case is reachable
											 */
											boolean persistentAction=false;
											
											for(int j=randomPosY;
													j < randomPosY+ships[1][shipId].getSize() && j < 10
													&& randomPosY+ships[1][shipId].getSize() -1 < 10 ; j++){
												
												int i=randomPosX;
												shipSelectPosLabelPlayer1[i][j].setText("||||");
												shipSelectPosLabelPlayer1[i][j].setStyle(backgroundSelect);
												setCaseContents(ships[1][shipId],i,j);
												persistentAction=true;
											}
											if(persistentAction==true)
												setShip(1,shipId,randomPosX,randomPosY);
										}
									}
								}
							}
						}
					}

					/** 
					 * method to set each ship case's type contained in the grid
					 * @param ship
					 * @param i
					 * @param j
					 */
					public void setCaseContents(Ship ship, int i, int j){

						if(ship.getShipType()==ShipType.DESTROYER)
							casesPlayer1[i][j].setContents(CaseContents.DESTROYER_CASE);
						
						if(ship.getShipType()==ShipType.CRUISER1)
							casesPlayer1[i][j].setContents(CaseContents.CRUISER1_CASE);

						if(ship.getShipType()==ShipType.CRUISER2)
							casesPlayer1[i][j].setContents(CaseContents.CRUISER2_CASE);
						
						if(ship.getShipType()==ShipType.BATTLESHIP)
							casesPlayer1[i][j].setContents(CaseContents.BATTLESHIP_CASE);
						
						if(ship.getShipType()==ShipType.CARRIER)
							casesPlayer1[i][j].setContents(CaseContents.CARRIER_CASE);
					}
					public boolean isEmpty(int randomPosX, int randomPosY){
						if(randomShipOrientation=='v'){
							for(int i=randomPosX;
									i < randomPosX+ships[1][shipId].getSize() && i < 10
									&& randomPosX+ships[1][shipId].getSize() -1 < 10 ; i++){
								
								int j= randomPosY;
								if(shipSelectPosLabelPlayer1[i][j].getText().toString().compareTo("||||")==0)
									return false;
							}
						}
						if(randomShipOrientation=='h'){
							for(int j= randomPosY;
									j <  randomPosY+ships[1][shipId].getSize() && j < 10
									&&  randomPosY+ships[1][shipId].getSize() -1 < 10 ; j++){
								
								int i=randomPosX;
								if(shipSelectPosLabelPlayer1[i][j].getText().toString().compareTo("||||")==0)
									return false;
							}
						}
						return true;
					}
					/**
					 * adding of ships parameters which are orientation, posX, posY
					 * @param shipOwner
					 * @param shipNumber
					 */
					public void setShip(int shipOwner, int shipNumber, int randomPosX, int randomPosY ){
						System.out.println("here"+randomPosX+"eddd"+randomPosY);
						ships[shipOwner][shipNumber].setOrientation(randomShipOrientation);
						ships[shipOwner][shipNumber].setPosX(randomPosX);
						ships[shipOwner][shipNumber].setPosY(randomPosY);
						ships[shipOwner][shipNumber].setSet();
					}
				
				});
				
				shipSettingBorderPane.setBottom(play);
				shipSettingBorderPane.setPadding(new Insets(5,5,5,5));
				
				Scene shipSettingScene=new Scene(shipSettingBorderPane);
				stage.setScene(shipSettingScene);
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
			vbox.getChildren().add(new Label("Computer"));
		}
		
		/**
		 * label d'initialization
		 */
		Label score[]=new Label[5];
		for(int i = 0 ; i < 5 ; i++){
			score[i]=new Label();
		}
		
		/**
		 * this HBox has been created to contains the information labels
		 * of the current player, which are displayed on the screen
		 */
		HBox scoreBox= new HBox();
		scoreBox.setPadding(new Insets(5, 5, 5, 5)); //top right bottom left
	    scoreBox.setSpacing(10);
	    
	    /**
	     * this labels just show to the player how many time he stroke a ship
	     * if in a first time, he succeeded to hit one 
	     */
		for(int i = 0 ; i < 5 ; i++){
			/**
			 * 
			 */
			if(ships[player][i].getShotCount() > 0){
				score[i].setText(String.valueOf(ships[player][i].getShotCount()));
				score[i].setStyle(scoreStyle[0]);
				score[i].setPadding(new Insets(2,2,2,2));
				scoreBox.getChildren().add(score[i]);
			}
			if(ships[player][i].getShotCount() == ships[player][i].getSize()){
				score[i].setStyle(scoreStyle[1]);
			}
		}
		
		vbox.getChildren().add(scoreBox);
		vbox.setAlignment(Pos.CENTER);
		vbox.setPadding(new Insets(0,20,0,20)); //top right bottom left
		return vbox;
	}
	
	
	public HBox addSingleGridPane(Stage stage){
		
		
		GridPane gridPane= new GridPane();
		
		Label[][] shipsSelectionLabel= new Label[5][5];
		
		GridPane[] shipsGridPaneSelection = new GridPane[5];
		
		/** 
		 * General addShipSetting box who contains the gridPane and the shipSelectionBox
		 */
		HBox addShipSettingBox = new HBox();
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setPadding(new Insets(1,1,1,1)); //top right bottom left
		
		/**
		 * select a ship then pick an orientation, vertical, horizontal, size
		 */
		VBox shipSelectionBox= new VBox();
		shipSelectionBox.setPadding(new Insets(5,5,5,5)); //top right bottom left
		
		Label pickAShip=new Label("Pick a ship");
		pickAShip.setPadding(new Insets(5,0,5,0));
		shipSelectionBox.getChildren().add(pickAShip);
		
		
		
		for(int i = 0 ; i < 5 ; i++){
			
			shipsGridPaneSelection[i]=new GridPane();
			// Set the the height gap between each label
			shipsGridPaneSelection[i].setHgap(1);
							
			//Set the width gap between each label
			shipsGridPaneSelection[i].setVgap(1);
							
			shipsGridPaneSelection[i].setAlignment(Pos.CENTER);
			shipsGridPaneSelection[i].setPadding(new Insets(1,1,1,1));
			shipsGridPaneSelection[i].setStyle(gridPaneStyle);
			
			/** 
			 * Retrieve each ship size to set exactly its case case according to its size
			 */
			for(int j=0 ; j < ships[0][i].getSize() ; j++){
				
				shipsSelectionLabel[i][j]=new Label("   ");
				shipsSelectionLabel[i][j].setStyle(squareGridPaneStyle);
				shipsGridPaneSelection[i].add(shipsSelectionLabel[i][j],j,i+1);
				
				/**
				 * handle each click which has been realized on a case of the ship selection labels
				 */
				final int IJ[]={i,j};
				shipsSelectionLabel[i][j].setOnMouseClicked(new EventHandler<MouseEvent>(){
					@Override
					public void handle(MouseEvent mouseEvent) {
						shipSelected=IJ[0];

						labelEffectShipPicking(IJ);
					}
					public void labelEffectShipPicking(int[] labelPosition){
						/**
						 * before withdraw selection of another ship if there is
						 */
						for(int i = 0 ; i < 5 ; i++){
							for(int j = 0 ; j < ships[0][i].getSize() ; j++){
								shipsSelectionLabel[i][j].setStyle(squareGridPaneStyle);
							}
						}
						for(int j = 0 ; j < ships[0][IJ[0]].getSize() ; j++){
							shipsSelectionLabel[IJ[0]][j].setStyle(shipPickingSquareGridPaneStyleOnClick);
						}
					}
					
				});
			}
			shipSelectionBox.getChildren().add(shipsGridPaneSelection[i]);
		}
		
		Label orientation=new Label("Pick an orientation");
		orientation.setPadding(new Insets(5,0,5,0)); //top right bottom left
		shipSelectionBox.getChildren().add(orientation);
		
		/** 
		 * orientation choice handling inside a HBox 
		 */
		HBox shipOrientationBox=new HBox();
		shipOrientationBox.setAlignment(Pos.CENTER);
		shipOrientationBox.setPadding(new Insets(5,5,5,5));
		
		Label verticalArrow=new Label("\u2193");
		Label horizontalArrow=new Label("\u2192");
		
		horizontalArrow.setPadding(new Insets(5,5,5,5));
		verticalArrow.setPadding(new Insets(5,5,5,5));
		
		/**
		 * handle the click on the orientation arrow in order to
		 * set the orientation char variable v
		 */
		verticalArrow.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				labelEffectShipOrientationPicking();
				shipOrientation='v';
			}
			public void labelEffectShipOrientationPicking(){
				/**
				 * before withdraw selection of the over orientation it's set
				 */
				horizontalArrow.setStyle(backgroundNull);
				verticalArrow.setStyle(orientationChoiceBackgroung);
			}
			
		});
		horizontalArrow.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				labelEffectShipOrientationPicking();
				shipOrientation='h';
			}
			public void labelEffectShipOrientationPicking(){
				/**
				 * before withdraw selection of the over orientation it's set
				 */
				verticalArrow.setStyle(backgroundNull);
				horizontalArrow.setStyle(orientationChoiceBackgroung);
			}
			
		});
		
		shipOrientationBox.getChildren().addAll(verticalArrow,horizontalArrow);
		
		/**
		 * main object add in ship add VBox
		 */
		Button addButton = new Button("add");
		addButton.setAlignment(Pos.CENTER);
		
		VBox add=new VBox();
		add.setAlignment(Pos.CENTER);
		add.setPadding(new Insets(5,5,5,5));
		add.getChildren().add(addButton);
		addButton.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				if(shipOrientation!='n' && shipSelected!=-1 && ships[0][shipSelected].getSet()==false){
					if(isEmpty()==true){
						if(shipOrientation=='v'){
							/**
							 * 
							 *   DESTROYER (2),
							 *   CRUISER1 (3),
							 *   CRUISER2 (3),
							 *   BATTLESHIP(4),
							 *   CARRIER(5);
							 */
							/**
							 * check if the case is reachable
							 */	
							boolean persistentAction=false;
							for(int i=shipPosLabelIJ[0];
									i < shipPosLabelIJ[0]+ships[0][shipSelected].getSize() && i < 10
									&& shipPosLabelIJ[0]+ships[0][shipSelected].getSize() -1 < 10 ; i++){
								
								int j=shipPosLabelIJ[1];
								shipSelectPosLabelPlayer0[i][j].setText("||||");
								shipSelectPosLabelPlayer0[i][j].setStyle(backgroundSelect);
								setCaseContents(ships[0][shipSelected],i,j);
								persistentAction=true;
							}
							if(persistentAction==true)
								setPersistent();
						}
						
						if(shipOrientation=='h'){
							/**
							 * 
							 *   DESTROYER (2),
							 *   CRUISER (3),
							 *   BATTLESHIP(4),
							 *   CARRIER(5);
							 */
							/**
							 * check if the case is reachable
							 */
							boolean persistentAction=false;
							for(int j=shipPosLabelIJ[1];
									j < shipPosLabelIJ[1]+ships[0][shipSelected].getSize() && j < 10
									&& shipPosLabelIJ[1]+ships[0][shipSelected].getSize() -1 < 10 ; j++){
								
								int i=shipPosLabelIJ[0];
								shipSelectPosLabelPlayer0[i][j].setText("||||");
								shipSelectPosLabelPlayer0[i][j].setStyle(backgroundSelect);
								setCaseContents(ships[0][shipSelected],i,j);
								persistentAction=true;
							}
							if(persistentAction==true)
								setPersistent();
						}
					}
				}
			}
			/** 
			 * method to set each ship case's type contained in the grid
			 * @param ship
			 * @param i
			 * @param j
			 */
			public void setCaseContents(Ship ship, int i, int j){

				if(ship.getShipType()==ShipType.DESTROYER)
					casesPlayer0[i][j].setContents(CaseContents.DESTROYER_CASE);
				
				if(ship.getShipType()==ShipType.CRUISER1)
					casesPlayer0[i][j].setContents(CaseContents.CRUISER1_CASE);

				if(ship.getShipType()==ShipType.CRUISER2)
					casesPlayer0[i][j].setContents(CaseContents.CRUISER2_CASE);
				
				if(ship.getShipType()==ShipType.BATTLESHIP)
					casesPlayer0[i][j].setContents(CaseContents.BATTLESHIP_CASE);
				
				if(ship.getShipType()==ShipType.CARRIER)
					casesPlayer0[i][j].setContents(CaseContents.CARRIER_CASE);
			}
			public void setPersistent(){
				for(int shipNumber = 0; shipNumber < 5; shipNumber++){
					if(shipSelected == shipNumber)
						setShip(0,shipNumber);
				}
				
			}
			public boolean isEmpty(){
				if(shipOrientation=='v'){
					for(int i=shipPosLabelIJ[0];
							i < shipPosLabelIJ[0]+ships[0][shipSelected].getSize() && i < 10
							&& shipPosLabelIJ[0]+ships[0][shipSelected].getSize() -1 < 10 ; i++){
						
						int j=shipPosLabelIJ[1];
						if(shipSelectPosLabelPlayer0[i][j].getText().toString().compareTo("||||")==0)
							return false;
					}
				}
				if(shipOrientation=='h'){
					for(int j=shipPosLabelIJ[1];
							j < shipPosLabelIJ[1]+ships[0][shipSelected].getSize() && j < 10
							&& shipPosLabelIJ[1]+ships[0][shipSelected].getSize() -1 < 10 ; j++){
						
						int i=shipPosLabelIJ[0];
						if(shipSelectPosLabelPlayer0[i][j].getText().toString().compareTo("||||")==0)
							return false;
					}
				}
				return true;
			}
			/**
			 * adding of ships parameters which are orientation, posX, posY
			 * @param shipOwner
			 * @param shipNumber
			 */
			public void setShip(int shipOwner, int shipNumber){
				
				ships[shipOwner][shipNumber].setOrientation(shipOrientation);
				ships[shipOwner][shipNumber].setPosX(shipPosLabelIJ[0]);
				ships[shipOwner][shipNumber].setPosY(shipPosLabelIJ[1]);
				ships[shipOwner][shipNumber].setSet();
			}
		});
		shipSelectionBox.getChildren().addAll(shipOrientationBox,add);
		
		/**
		 * handle the ship's setting position on the grid
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
				
				shipSelectPosLabelPlayer0[i][j]=new Label("   ");
				shipSelectPosLabelPlayer0[i][j].setStyle(squareGridPaneStyle);
				gridPane.add(shipSelectPosLabelPlayer0[i][j],j,i+1);
				
				/**
				 * handle each click which has been realized on a case
				 */
				final int IJ[]={i,j};
				shipSelectPosLabelPlayer0[i][j].setOnMouseClicked(new EventHandler<MouseEvent>(){
					@Override
					public void handle(MouseEvent mouseEvent) {
						
						/** set label to selected with a color */
						shipLabelEffect(IJ);
						/**
						 * set the current position of the selected label
						 */
						shipPosLabelIJ=IJ;
					}
					public void shipLabelEffect(int[] labelPosition){
						/**
						 * before withdraw selection of another ship if there is
						 */
						for(int i=0;i<10;i++){
							for(int j=0;j<10;j++){
								shipSelectPosLabelPlayer0[i][j].setStyle(squareGridPaneStyle);
							}
						}
						shipSelectPosLabelPlayer0[labelPosition[0]][labelPosition[1]].setStyle(backgroundSelect);
					}
				});
			}
		}
		
		addShipSettingBox.getChildren().addAll(shipSelectionBox,gridPane);
	    return addShipSettingBox;
	}
	
	public void setComputerShip(){
		
	}
	
	public HBox addDoubleMixGridPane(BorderPane gameBorderPane) {
		
		HBox grids=new HBox();
		grids.setAlignment(Pos.CENTER);
		grids.setPadding(new Insets(5,5,5,5));
		
		GridPane player0GridPane= new GridPane();
		GridPane player1GridPane= new GridPane();

		/**
		 * Set the the height gap between each label
		 * Set the width gap between each label
		 */
		
		// Set the the height gap between each label
		player0GridPane.setHgap(1); 
		player1GridPane.setHgap(1); 

		//Set the width gap between each label
		player0GridPane.setVgap(1); 
		player1GridPane.setVgap(1); 

		player0GridPane.setAlignment(Pos.CENTER);
		player1GridPane.setAlignment(Pos.CENTER);

		player0GridPane.setPadding(new Insets(5,5,5,5)); //top right bottom left
		player1GridPane.setPadding(new Insets(5,5,5,5)); //top right bottom left

		player0GridPane.setStyle(gridPaneStyle);
		player1GridPane.setStyle(gridPaneStyle);


		
		for(int i=0 ; i < 10 ; i++){
			for(int j=0 ; j < 10 ; j++){
				//player0 grid
				//from label[0][0] to label [10][9] included
				
				gridPlayer0[i][j]=player0.getGrid()[i][j];
				gridPlayer0[i][j].setStyle(squareGridPaneStyle);
				player0GridPane.add(gridPlayer0[i][j],j,i+1);
			
				//player1 grid
				//from label[0][11] to label [10][20] included
				
				gridPlayer1[i][j]=new Label("   ");
				gridPlayer1[i][j].setStyle(squareGridPaneStyle);
				player1GridPane.add(gridPlayer1[i][j],j,i+1);
				
				/**
				 * handle each click which has been realized on a case
				 */
				final int IJ[]={i,j};
				gridPlayer0[i][j].setOnMouseClicked(new EventHandler<MouseEvent>(){
					@Override
					public void handle(MouseEvent mouseEvent) {
							/**
							 * no matter whether the case clicked got a ship or not
							 * set firstly its color to transparent
							 */
							labelEffect(IJ);
	
							/**
							 * know if the label clicked belongs to a ship
							 * then make options
							 */
							if(casesPlayer0[IJ[0]][IJ[1]].getContents()!=CaseContents.UNKNOWN ){
								/** 
								 * if there is a ship touched and its shot number isn't already reached
								 * get the touch ship by the caseContents 
								 */
								ShipType shipType= casesPlayer0[IJ[0]][IJ[1]].getContents().getShipType();
								
								int shipTypeId=-1;
								if(shipType==ShipType.DESTROYER)
									shipTypeId=0;
								if(shipType==ShipType.CRUISER1)
									shipTypeId=1;
								if(shipType==ShipType.CRUISER2)
									shipTypeId=2;
								if(shipType==ShipType.BATTLESHIP)
									shipTypeId=3;
								if(shipType==ShipType.CARRIER)
									shipTypeId=4;
								
								if(ships[0][shipTypeId].getShotCount() < ships[0][shipTypeId].getSize()){
									/**
									 * increase the shotCount of the touched ship
									 * if the square was a ship square, set its color
									 */
									ships[0][shipTypeId].setShotCount(ships[0][shipTypeId].getShotCount()+1);
											
									gridPlayer0[IJ[0]][IJ[1]].setStyle(squareGridPaneStyleOnTouch);
		
								}
								/**
								 * if the ship hit has already completely been found out
								 * then set it to sinked
								 */
								if(ships[0][shipTypeId].getShotCount() >= ships[0][shipTypeId].getSize()){
									ships[0][shipTypeId].setSinked();
								}
								gameBorderPane.setLeft(setPlayer(0));
							}
					}
					public void labelEffect(int[] labelPosition){
						
						gridPlayer0[labelPosition[0]][labelPosition[1]].setStyle(squareGridPaneStyleOnClick);
					}
					
				});
				gridPlayer1[i][j].setOnMouseClicked(new EventHandler<MouseEvent>(){
					@Override
					public void handle(MouseEvent mouseEvent) {
						/**
						 * no matter whether the case clicked got a ship or not
						 * set firstly its color to transparent
						 */
						labelEffect(IJ);

						/**
						 * know if the label clicked belongs to a ship
						 * then make options
						 */
						if(casesPlayer1[IJ[0]][IJ[1]].getContents()!=CaseContents.UNKNOWN ){
							/** 
							 * if there is a ship touched and its shot number isn't already reached
							 * get the touch ship by the caseContents 
							 */
							ShipType shipType= casesPlayer1[IJ[0]][IJ[1]].getContents().getShipType();
							
							int shipTypeId=-1;
							
							if(shipType==ShipType.DESTROYER)
								shipTypeId=0;
							if(shipType==ShipType.CRUISER1)
								shipTypeId=1;
							if(shipType==ShipType.CRUISER2)
								shipTypeId=2;
							if(shipType==ShipType.BATTLESHIP)
								shipTypeId=3;
							if(shipType==ShipType.CARRIER)
								shipTypeId=4;
							
							if(ships[1][shipTypeId].getShotCount() < ships[1][shipTypeId].getSize()){
								/**
								 * increase the shotCount of the touched ship
								 * if the square was a ship square, set its color
								 */
								ships[1][shipTypeId].setShotCount(ships[1][shipTypeId].getShotCount()+1);
										
								gridPlayer1[IJ[0]][IJ[1]].setStyle(squareGridPaneStyleOnTouch);
	
							}
							/**
							 * if the ship hit has already completely been found out
							 * then set it to sinked
							 */
							if(ships[1][shipTypeId].getShotCount() >= ships[1][shipTypeId].getSize()){
								ships[1][shipTypeId].setSinked();
							}
							gameBorderPane.setRight(setPlayer(1));
						}
				}
					public void labelEffect(int[] labelPosition){
						
						gridPlayer1[labelPosition[0]][labelPosition[1]].setStyle(squareGridPaneStyleOnClick);
					}
					
				});
			}
		}
		grids.getChildren().addAll(player0GridPane,player1GridPane);
		
	    return grids;
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
	
}
