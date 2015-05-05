package battleships;

public class Ship {
	private ShipType type;
        private Coordinates[] coords;        
	private char orientation;
	
        private int posX;
	private int posY;
	private int size;
	private int shotCount=0;
	private boolean sinked=false;
	private boolean set=false;
        
	public Coordinates[] getCoords() {
            return this.coords;
        }
        
        void setCoords(Coordinates[] coords) {
            this.coords = coords;
        }
        
        public ShipType getType() {
            return this.type;
        }
        
	public Ship(ShipType type, char orientation, int posX,int posY){
		this.type=type;
		this.orientation=orientation;
		this.posX=posX;
		this.posY=posY;
		this.size=type.getLength();
	}
	
	public Ship(ShipType type){
		this.type=type;
		this.size=type.getLength();
                this.coords = new Coordinates[type.getLength()];
	}
	public ShipType getShipType(){
		return this.type;
	}
	public int getShotCount(){
		return shotCount;
	}
	public void setShotCount(int shotCount){
		this.shotCount=shotCount;
	}
	public void setOrientation(char orientation){
		this.orientation=orientation;
	}
	public void setPosX(int posX){
		this.posX=posX;
	}
	public int getPosX(){
		return this.posX;
	}
	public void setPosY(int posY){
		this.posY=posY;
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
	public boolean getSet(){
		return this.set;
	}
	public void setSet(){
		this.set=true;
	}
}

enum ShipType {
    DESTROYER (2,"destroyer"),
    CRUISER1(3,"cruiser1"),
    CRUISER2(3,"cruiser2"),
    BATTLESHIP(4,"battleship"),
    CARRIER(5,"carrier");
    
    private final int length;
    private String name;
    
    ShipType(int length, String name) {
        this.length = length;
        this.name = name;
    }
    
    int getLength() {
        return this.length;
    }
    
    CaseContents convertToCaseContents() {
        CaseContents result;
        switch(name){
            case "destroyer" : result = CaseContents.DESTROYER_CASE; break;
            case "cruiser1" : result = CaseContents.CRUISER1_CASE; break;
            case "cruiser2" : result = CaseContents.CRUISER2_CASE; break;
            case "battleship" : result = CaseContents.BATTLESHIP_CASE; break;
            case "carrier" : result = CaseContents.CARRIER_CASE; break;
            default : result = CaseContents.UNKNOWN; break;
        }
        return result;
    }
}
