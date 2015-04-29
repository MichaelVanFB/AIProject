package battleships;

public class Display {
	private Player player1;
	private Player player2;
	private String[][] screenTab= new String[100][100];
	
	Display(Player player1, Player player2){
		this.player1=player1;
		this.player2=player2;
	};
	
	public void setPlayer1(Player player){
		this.player1=player;
	}
	public void setPlayer2(Player player){
		this.player2=player;
	}
	public void displayScreen(){
		System.out.println(screenTab);
	}
}
