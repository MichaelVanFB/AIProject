package battleships;

import java.util.ArrayList;
import java.util.ListIterator;

public class GridPacker
{    
    private static int instanceCount = 0;
    
    /**
     * The probability of the current configuration occurring, given its parent
     * GridPacker has occurred.
     * 
     * The root GridPacker will have this set to 1.
     */
    private float condProbability;
    
    /**
     * The starting grid from which we calculate all the possible ship
     * configurations.
     */
    private Grid grid;
    
    private Ship destroyer;
    
    private Ship cruiser1;
    
    private Ship cruiser2;
    
    private Ship battleship;
    
    private Ship carrier;
    
    /**
     * ArrayList of GridPackers, each containing the different possible configs
     * formed by legally guessing the place of a ship.
     * 
     * We use an ArrayList instead of a regular array in order to save memory
     * and for convenience, although there may be a small impact on speed.
     */
    private ArrayList<GridPacker> possibleConfigurations = new ArrayList<GridPacker>();
    
    GridPacker(GridPacker old) {
        instanceCount++;
        this.grid = old.grid;
        this.condProbability = 0;
        this.destroyer = old.destroyer;
        this.cruiser1 = old.cruiser1;
        this.cruiser2 = old.cruiser2;
        this.battleship = old.battleship;
        this.carrier = old.carrier;
    }
    
    
    
    GridPacker(Grid grid) {
        instanceCount++;
        this.grid = grid;
        this.condProbability = 1;
        this.destroyer = new Ship(ShipType.DESTROYER);
        this.cruiser1 = new Ship(ShipType.CRUISER1);
        this.cruiser2 = new Ship(ShipType.CRUISER2);
        this.battleship = new Ship(ShipType.BATTLESHIP);
        this.carrier = new Ship(ShipType.CARRIER);
    }
    
    Grid getGrid() {
        return this.grid;
    }
    
    void setGrid(Grid grid) {
        this.grid = grid;
    }
    
    float getCondProbability() {
        return this.condProbability;
    }
    
    void setCondProbability(float p) {
        if (p > 1) {
            throw new IllegalArgumentException("probabilities must always be less than 1");
        }
        else if (p < 0) {
            throw new IllegalArgumentException("probabilities cannot be negative");
        }
        else {
            this.condProbability = p;
        }
    }
    
    ArrayList<GridPacker> getPossibleConfigurations() {
        return possibleConfigurations;
    }
    
    /**
     * A convenience method to return all ship Coordinates arrays in an array we
     * can iterate over.
     * @return 
     */
    Ship[] getAllShips() {
        return new Ship[]{destroyer, cruiser1, cruiser2, battleship, carrier};
    }
    
    void setShipCoords(Coordinates[] coords, ShipType type) {
        switch(type) {
            case DESTROYER : this.destroyer.setCoords(coords); break;
            case CRUISER1 : this.cruiser1.setCoords(coords); break;
            case CRUISER2 : this.cruiser2.setCoords(coords); break;
            case BATTLESHIP : this.battleship.setCoords(coords); break;
            case CARRIER : this.carrier.setCoords(coords); break;
            default : break;
        }
    }
    
    void addPossibleConfiguration(GridPacker gp) {
        this.possibleConfigurations.add(gp);
    }
    
    void generateAllConfigurations() {
        // for each ship, generate all the possible positions it can be in (in a
        // new GridPacker for each configuration) and add them to the ArrayList,
        // possibleConfigurations
        for (Ship ship : getAllShips()) {
            for (GridPacker newConfig : makeShipConfigurations(ship)) {
                addPossibleConfiguration(newConfig);
                newConfig.generateAllConfigurations();
            }
        }
    }
    
    GridPacker[] makeShipConfigurations(Ship ship) {
        // instantiate new ArrayList to hold the new configurations
        ArrayList<GridPacker> results = new ArrayList<>();
        // extract coordinates
        Coordinates[] coords = ship.getCoords();
        // return immediately if we already found all the cases
        if (coords.length == ship.getType().getLength()) {
            // return an empty array
            return (GridPacker[])results.toArray(new GridPacker[results.size()]);
        }
        // instanciate a counter for the number of configurations generated.
        // This is used to calculate the conditional probabilities (1/num)
        int numNewConfigs = 0;
        // get the length as we will make use of it many times
        int shiplen = ship.getType().getLength();
        // if no coordinates in ship, then iterate over every case in the grid
        // and see if we can place the ship there vertically or horizontally
        if (coords.length == 0) {
            Case[][] probaCases = grid.getCases();
            // find all horizontal configurations
            for (int row = 0; row < probaCases.length; row++) {
                colLoop:
                // we don't bother testing cases when we will go over the border
                for (int col = 0; col <= probaCases[0].length - shiplen; col++) {
                    // make a new GridPacker to hold the new config. Initially
                    // with conditional probability set to zero
                    GridPacker newConfig = new GridPacker(this);
                    Coordinates[] newCoords = new Coordinates[shiplen];
                    // otherwise check forwards for any obstructions
                    for (int v = 0; v < shiplen; v++) {
                        // if obstruction, fail
                        if (newConfig.grid.cases[row][v].getContents() != CaseContents.UNKNOWN) {
                            continue colLoop;
                        }
                        else {
                            newCoords[v] = new Coordinates(row,v);
                        }
                    }
                    // if arrived here, there were no obstructions
                    numNewConfigs++;
                    // add the coordinates to the config
                    newConfig.setShipCoords(newCoords, ship.getType());
                    results.add(newConfig);
                }
            }
            // find all vertical configurations
            rowLoop:
            // we don't bother testing cases when we will go over the border
            for (int row = 0; row <= probaCases.length - shiplen; row++) {
                for (int col = 0; col < probaCases[0].length; col++) {
                    // make a new GridPacker to hold the new config. Initially
                    // with condition probability set to zero
                    GridPacker newConfig = new GridPacker(this);
                    Coordinates[] newCoords = new Coordinates[shiplen];
                    // otherwise check forwards for any obstructions
                    for (int u = 0; u < shiplen; u++) {
                        // if obstruction, fail
                        if (newConfig.grid.cases[u][col].getContents() != CaseContents.UNKNOWN) {
                            continue rowLoop;
                        }
                        else {
                            newCoords[u] = new Coordinates(u,col);
                        }
                    }
                    // if arrived here, there were no obstructions
                    numNewConfigs++;
                    // add the coordinates to the config
                    newConfig.setShipCoords(newCoords, ship.getType());
                    results.add(newConfig);
                }
            }
        }
        // if we have found 1 case already there are fewer possiblities
        else if (coords.length == 1) {
            // all possible configurations must contain the coordinates of the
            // known case
            int x = coords[0].getX();
            int y = coords[0].getY();
            // we must search in the city block neighbour directions for
            // obstructions
            VLoop:
            for (int d = x - shiplen + 1; d <= x; d++) {
                // make a new GridPacker to hold the new config. Initially
                // with condition probability set to zero
                GridPacker newConfig = new GridPacker(this);
                Coordinates[] newCoords = new Coordinates[shiplen];
                for (int u = 0; u < shiplen; u++) {
                    CaseContents contents = newConfig.grid.cases[d+u][y].getContents();
                    if ( d + u < 0 || d + u >= newConfig.grid.getHeight() || (d != x && contents != CaseContents.UNKNOWN) ) {
                        continue VLoop;
                    }
                    else {
                        newCoords[u] = new Coordinates(d+u,y);
                    }
                }
                // if arrived here, there were no obstructions
                numNewConfigs++;
                results.add(newConfig);
                // add the coordinates to the config
                newConfig.setShipCoords(newCoords, ship.getType());
            }
            HLoop:
            for (int d = y - shiplen + 1; d <= y; d++) {
                // make a new GridPacker to hold the new config. Initially
                // with condition probability set to zero
                GridPacker newConfig = new GridPacker(this);
                Coordinates[] newCoords = new Coordinates[shiplen];
                for (int v = 0; v < shiplen; v++) {
                    CaseContents contents = newConfig.grid.cases[x][d+v].getContents();
                    if ( d + v < 0 || d + v >= newConfig.grid.getWidth() || (d != y && contents != CaseContents.UNKNOWN) ) {
                        continue HLoop;
                    }
                    else {
                        newCoords[v] = new Coordinates(x,d+v);
                    }
                }
                // if arrived here, there were no obstructions
                numNewConfigs++;
                results.add(newConfig);
                // add the coordinates to the config
                newConfig.setShipCoords(newCoords, ship.getType());
            }
            
        }
        else if (coords.length > 1) {
            // can only have configs containing all the points, aligned in same
            // axis
            int x = Coordinates.minX(coords);
            int y = Coordinates.minY(coords);
            if (Coordinates.alignedX(coords)) {
                // we must search in the city block neighbour directions for
                // obstructions
                VLoop:
                for (int d = x - shiplen + 1; d <= x; d++) {
                    // make a new GridPacker to hold the new config. Initially
                    // with condition probability set to zero
                    GridPacker newConfig = new GridPacker(this);
                    Coordinates[] newCoords = new Coordinates[shiplen];
                    for (int u = 0; u < shiplen; u++) {
                        CaseContents contents = newConfig.grid.cases[d+u][y].getContents();
                        // check not past borders
                        boolean pastBorders = d + u < 0 || d + u >= newConfig.grid.getHeight();
                        // check new cases are unknown
                        boolean obstruction = !Coordinates.arrayContains(coords, new Coordinates(d+u,y)) && contents != CaseContents.UNKNOWN;
                        if (pastBorders || obstruction) {
                            continue VLoop;
                        }
                        else {
                            newCoords[u] = new Coordinates(d+u,y);
                        }
                    }
                    // if arrived here, there were no obstructions
                    numNewConfigs++;
                    results.add(newConfig);
                    // add the coordinates to the config
                    newConfig.setShipCoords(newCoords, ship.getType());
                }
            }
            else if (Coordinates.alignedY(coords)) {
                HLoop:
                for (int d = y - shiplen + 1; d <= y; d++) {
                    // make a new GridPacker to hold the new config. Initially
                    // with condition probability set to zero
                    GridPacker newConfig = new GridPacker(this);
                    Coordinates[] newCoords = new Coordinates[shiplen];
                    for (int v = 0; v < shiplen; v++) {
                        CaseContents contents = newConfig.grid.cases[x][d+v].getContents();
                        // check not past borders
                        boolean pastBorders = d + v < 0 || d + v >= newConfig.grid.getWidth();
                        // check new cases are unknown
                        boolean obstruction = !Coordinates.arrayContains(coords, new Coordinates(x,d+v)) && contents != CaseContents.UNKNOWN;
                        if (pastBorders || obstruction) {
                            continue HLoop;
                        }
                        else {
                            newCoords[v] = new Coordinates(x,d+v);
                        }
                    }
                    // if arrived here, there were no obstructions
                    numNewConfigs++;
                    results.add(newConfig);
                    // add the coordinates to the config
                    newConfig.setShipCoords(newCoords, ship.getType());
                }
            }
        }
        // now that we know the number of new child configurations, we can set
        // the conditional probability of each
        ListIterator<GridPacker> iter = results.listIterator();
        float conditionalProb = (float) 1.0/numNewConfigs;
        while(iter.hasNext()) {
            iter.next().setCondProbability(conditionalProb);
        }
        // convert and return an array containing the new configs
        return (GridPacker[])results.toArray();
    }
    
    void calculateProbabilities() {
        // if the are no possible configurations left, the probability of each
        // case equals num of posible ship cases left divided by num of unknown
        // cases
        if (possibleConfigurations.size() == 0) {
            int numKnownShip = 0;
            int numUnknown = 0;
            // iterate over grid to count num of known and unknown ship cases
            for (int row = 0; row < grid.getHeight(); row++) {
                for (int col = 0; col < grid.getWidth(); col++) {
                    if (grid.getCase(row,col).getContents() == CaseContents.UNKNOWN) {
                        numUnknown++;
                    }
                    else if (grid.getCase(row,col).getContents() != CaseContents.MISS) {
                        numKnownShip++;
                    }
                }
            }
            float probShip = (float)((float)(17 - numKnownShip) / numUnknown);
            // iterate back over, set 1 where ship, 0 where miss, and formula
            // for rest
            for (int row = 0; row < grid.getHeight(); row++) {
                for (int col = 0; col < grid.getWidth(); col++) {
                    Case currentCase = grid.getCase(row,col);
                    CaseContents contents = currentCase.getContents();
                    if (contents == CaseContents.UNKNOWN) {
                        currentCase.setProbabilityIsShip(probShip);
                    }
                    else if (contents == CaseContents.MISS) {
                        currentCase.setProbabilityIsShip(0.0f);
                    }
                    else {
                        currentCase.setProbabilityIsShip(1.0f);
                    }
                }
            }
        }
        else {
            // call recursive method on each possibleConfiguration so each fills
            // its probabilitycases
            for (GridPacker gp : possibleConfigurations) {
                gp.calculateProbabilities();
            }
            // iterate over again and sum conditional probab times prob for each
            // case given its configuration is true
            for (GridPacker gp : possibleConfigurations) {
                for (int row = 0; row < gp.grid.getHeight(); row++) {
                    for (int col = 0; col < gp.grid.getWidth(); col++) {
                        Case pCase = grid.getCase(row,col);
                        float oldProb = grid.cases[row][col].getProbabilityIsShip();
                        grid.cases[row][col].setProbabilityIsShip(oldProb + condProbability * pCase.getProbabilityIsShip());
                    }
                }
            }
        }
    }
    
}
