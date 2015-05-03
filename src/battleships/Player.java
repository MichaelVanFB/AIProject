package battleships;

import javafx.scene.control.Label;

public class Player {
	private int id;
	private String name;
	private Label[][] grid = new Label [10][10];
	private Ship[] ships= new Ship[5];
	private int score;
	
	public Player(int id, String name, Label[][] grid){
		this.id=id;
		this.name=name;
		this.grid=grid;
	}
	public Player (int id, String name){
		this.id=id;
		this.name=name;
	}
	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name=name;
	}
	public Label[][] getGrid(){
		return this.grid;
	}
	public void setGrid(Label [][] grid){
		this.grid=grid;
	}
	public Ship[] getShips(){
		return ships;
	}
	public void setShips(Ship[] ships){
		this.ships=ships;
	}
	public void setScore(int score){
		this.score=score;
	}
	public int getScore(){
		return this.score;
	}
}
