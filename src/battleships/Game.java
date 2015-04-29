package battleships;



import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Game extends Application {
	
	private Label nothing=new Label("X");
	private Ship[] ships=new Ship[4];
	
	private Player player0;
	private Player player1;
	
	@Override
	public void start (Stage stage) throws Exception {
		
	BorderPane borderPane= new BorderPane();
	
	borderPane.setTop(addHBox());
	borderPane.setCenter(addGridPane());   

	Group root = new Group();
	
	Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
	
	stage.setScene(new Scene(root, primaryScreenBounds.getWidth(),
			primaryScreenBounds.getHeight()));
	
	Scene scene=new Scene(borderPane);
	stage.setScene(scene);
    stage.show();
	}
	
	public HBox addHBox() {
		
	    HBox hbox = new HBox();
	    

		//player0=new Player(0,"Winston");
		//player1=new Player(1,"Michael");

		Label title=new Label("BattleShips");
		//Label playerName[]= new Label[2];
		
		//playerName[0]=new Label(player0.getName());
		//playerName[1]=new Label(player1.getName());


	    //hbox.getChildren().addAll(title, playerName[0],playerName[1]);
		hbox.getChildren().add(title);
		hbox.setSpacing(10);
		hbox.setAlignment(Pos.CENTER);
	    hbox.setPadding(new Insets(1, 5, 10, 5));

	    return hbox;
	}
	
	public GridPane addGridPane() {
		
		GridPane gridPane= new GridPane();
		
		Label label[][]= new Label[20][50];

		gridPane.setHgap(1); // Set the the height gap between each label
		gridPane.setVgap(1); //Set the width gap between each label
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setPadding(new Insets(5,5,5,5));
		String gridPaneStyle="-fx-border-color: black;\n";
		String squareGridPaneStyle="-fx-border-color: black;\n";
		gridPane.setStyle(gridPaneStyle);

		
		for(int i=0 ; i < 20 ; i++){
			for(int j=0 ; j < 50 ; j++){
				
				if(j == 25){
					label[i][j]=new Label(" | ");
					gridPane.add(label[i][j],j,i+1);
				}
				
				//player0 grid
				if(i > 0 && j < 25){
					label[i][j]=new Label("   ");
					label[i][j].setStyle(squareGridPaneStyle);
					gridPane.add(label[i][j],j,i+1);
				}
				
				//player1 grid
				if(i > 0 && j > 25){
					label[i][j]=new Label("   ");
					label[i][j].setStyle(squareGridPaneStyle);
					gridPane.add(label[i][j],j,i+1);
				}
			}
		}
	    return gridPane;
	}

	public static void main(String[]args){
		launch(args);
		
	}
}
