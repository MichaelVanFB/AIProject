package battleships;

public class Ship {
	private ShipType type;
	private String orientation;
	private int posx;
	private int posy;
	private int size;
	
	public Ship(ShipType type, String orientation, int posx,int posy){
		this.type=type;
		this.orientation=orientation;
		this.posx=posx;
		this.posy=posy;
		this.size=type.getLength();
	}
}
