package battleships;

public class Case
{
    private CaseContents contents = CaseContents.UNKNOWN;
    private float probabilityIsShip = 0.0f;
        
    Case(CaseContents contents, float probability) {
        this.contents = contents;
        this.probabilityIsShip = probability;
    }
    
    Case(CaseContents contents) {
        this(contents, 0.0f);
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
    
    float getProbabilityIsShip() {
        return this.probabilityIsShip;
    }

    void setProbabilityIsShip(float probability) {
        this.probabilityIsShip = probability;
    }
    
    Case copy() {
        return new Case(this.contents, this.probabilityIsShip);
    }
}

enum CaseContents {
    UNKNOWN(null),
    MISS(null),
    HIT_DESTROYER(ShipType.DESTROYER),
    HIT_CRUISER(ShipType.CRUISER),
    HIT_BATTLESHIP(ShipType.BATTLESHIP),
    HIT_CARRIER(ShipType.CARRIER),
    DESTROYER_CASE(ShipType.DESTROYER),
    CRUISER1_CASE(ShipType.CRUISER1),
    CRUISER2_CASE(ShipType.CRUISER2),
    BATTLESHIP_CASE(ShipType.BATTLESHIP),
    CARRIER_CASE(ShipType.CARRIER);
    
    private final ShipType shipType;
    
    CaseContents(ShipType shipType) {
        this.shipType = shipType;
    }
    
    ShipType getShipType() {
        return this.shipType;
    }
}