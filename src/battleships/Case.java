package battleships;

public class Case
{
    private CaseContents contents;
        
    Case(CaseContents contents) {
        this.contents = contents;
    }

    Case() {
        this(CaseContents.UNKNOWN);
    }

    CaseContents getContents() {
        return this.contents;
    }

    void setContents(CaseContents contents) {
        this.contents = contents;
    }
    
    Case copy() {
        return new Case(this.contents);
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
    
    ShipType getShipType() {
        return this.shipType;
    }
}