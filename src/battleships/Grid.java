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
    HIT_DESTROYER(Ship.DESTROYER),
    HIT_CRUISER(Ship.CRUISER),
    HIT_BATTLESHIP(Ship.BATTLESHIP),
    HIT_CARRIER(Ship.CARRIER);
    
    private final Ship ship;
    
    CaseContents(Ship ship) {
        this.ship = ship;
    }
    
    Ship getShip() {
        return this.ship;
    }
}

enum Ship {
    DESTROYER (2),
    CRUISER (3),
    BATTLESHIP(4),
    CARRIER(5);
    
    private final int length;
    
    Ship(int length) {
        this.length = length;
    }
    
    int getLength() {
        return this.length;
    }
}
