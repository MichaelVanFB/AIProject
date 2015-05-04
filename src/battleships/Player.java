package battleships;

import javafx.scene.control.Label;

public class Player {
	private int id;
	private String name;
	private Label[][] gridLabel = new Label [10][10];
	private Case[][] gridCase = new Case [10][10];
	private Ship[] ships= new Ship[5];
	private int score;
	
	public Player(int id, String name, Label[][] gridLabel){
		this.id=id;
		this.name=name;
		this.gridLabel=gridLabel;
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
	public Label[][] getGridLabel(){
		return this.gridLabel;
	}
	public void setGridLabel(Label [][] grid){
		this.gridLabel=grid;
	}
	public Case[][] getGridCase(){
		return this.gridCase;
	}
	public void setGridCase(Case[][] gridCase){
		this.gridCase=gridCase;
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
