package battleships;

public class Player {
	private int id;
	private String name;
	private int score;
	
	public Player(int id, String name){
		this.id=id;
		this.name=name;
	}
	public void setScore(int score){
		this.score=score;
	}
	public int getScore(){
		return this.score;
	}
}
