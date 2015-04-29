package battleships;

public class Grid
{
    private class Case {
        
    }
    
    private Case[][] cases;
    
    public Grid(int height, int width) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < height; j++) {
                cases[i][j] = new Case();
            } 
        }
    }
}

enum CaseContents {
    UNKNOWN(null),
    MISS(null),
    HIT_DESTROYER(ShipType.DESTROYER),
    HIT_CRUISER(ShipType.CRUISER),
    HIT_BATTLESHIP(ShipType.BATTLESHIP),
    HIT_CARRIER(ShipType.CARRIER);
    
    private final ShipType shipType;
    
    CaseContents(ShipType shipType) {
        this.shipType = shipType;
    }
    
    ShipType getShip() {
        return this.shipType;
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
