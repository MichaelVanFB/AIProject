package battleships;

public class Ship {
	private ShipType type;
	private char orientation;
	private int posX;
	private int posY;
	private int size;
	private int shotCount=0;
	private boolean sinked=false;
	
	public Ship(ShipType type, char orientation, int posX,int posY){
		this.type=type;
		this.orientation=orientation;
		this.posX=posX;
		this.posY=posY;
		this.size=type.getLength();
	}
	public int getShotCount(){
		return shotCount;
	}
	public void setShotCount(int shotCount){
		this.shotCount=shotCount;
	}
	public int getPosX(){
		return this.posX;
	}
	public int getPosY(){
		return this.posY;
	}
	public int getSize(){
		return size;
	}
	public boolean getSinked(){
		return this.sinked;
	}
	public void setSinked(){
		this.sinked=true;
	}
	
}

enum ShipType {
    DESTROYER (2),
    CRUISER (3),
    BATTLESHIP(4),
    CARRIER(5);
    
    private final int length;
    
    ShipType(int length) {
        this.length = length;
    }
    
    int getLength() {
        return this.length;
    }
}
