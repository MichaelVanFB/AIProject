package battleships;

public class Player {
	private int id;
	private String name;
	private String[][] grid = new String [20][20];
	private int score;
	
	public Player(int id, String name, String[][] grid){
		this.id=id;
		this.name=name;
		this.grid=grid;
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
