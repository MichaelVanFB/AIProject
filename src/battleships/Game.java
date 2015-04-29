package battleships;


import com.sun.javafx.geom.Dimension2D;
import com.sun.javafx.tk.Toolkit;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Game extends Application {
	private Player player0;
	private Player player1;
	@Override
	public void start (Stage stage) throws Exception {
		
	GridPane gridPane= new GridPane();
	
	Label label[][]= new Label[40][40];
	
	player0=new Player(0,"Winston");
	player1=new Player(1,"Michael");

	gridPane.setHgap(1); // Set the the height gap between each label
	gridPane.setVgap(1); //Set the width gap between each label
	gridPane.setAlignment(Pos.CENTER);
	for(int i=0;i<40;i++){
		for(int j=0;j<40;j++){
			//Title label
			if(i==0 && j==20)
				label[i][j]=new Label("BattleShips");
				gridPane.add(label[i][j], j, i+1);
			//player0 name
			if(i==1 && j==0)
				label[i][j]=new Label(player0.getName());
				gridPane.add(label[i][j], j, i+1);
			//player0 grid
			if(i>10 && j < 20){
				label[i][j]=new Label("t1");
				gridPane.add(label[i][j],j,i+1);
			}
			//player1 name
			if(i==1 && j==20)
				label[i][j]=new Label(player1.getName());
				gridPane.add(label[i][j], j, i+1);
			//player1 grid
			if(i>10 && j > 20){
				label[i][j]=new Label("t2");
				gridPane.add(label[i][j],j,i+1);
			}
		}
	}
	
	Group root = new Group();
	
	Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
	
	stage.setScene(new Scene(root, primaryScreenBounds.getWidth(),
			primaryScreenBounds.getHeight()));
	
	Scene scene=new Scene(gridPane);
	stage.setScene(scene);
    stage.show();
	}
	
	public static void main(String[]args){
		launch(args);
		
	}
}
