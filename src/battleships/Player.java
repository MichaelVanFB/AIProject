package battleships;

public class Player {
	private int id;
	private String name;
	private String[][] grid = new String [10][10];
	private Ship[] ships= new Ship[5];
	private int score;
	
	public Player(int id, String name, String[][] grid){
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
	public void setGrid(String [][] grid){
		this.grid=grid;
	}
}
