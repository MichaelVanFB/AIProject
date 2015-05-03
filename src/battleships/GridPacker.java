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
    private ProbabilityGrid grid;
    
    private Coordinates[] destroyer;
    
    private Coordinates[] cruiser1;
    
    private Coordinates[] cruiser2;
    
    private Coordinates[] battleship;
    
    private Coordinates[] carrier;
    
    /**
     * ArrayList of GridPackers, each containing the different possible configs
     * formed by legally guessing the place of a ship.
     * 
     * We use an ArrayList instead of a regular array in order to save memory
     * and for convenience, although there may be a small impact on speed.
     */
    private ArrayList<GridPacker> possibleConfigurations;
    
    GridPacker(ProbabilityGrid grid, float condProb) {
        instanceCount++;
        this.grid = new ProbabilityGrid(grid);
        this.condProbability = condProb;
    }
    
    Grid getGrid() {
        return this.grid;
    }
    
    void setGrid(ProbabilityGrid grid) {
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
    Coordinates[][] getAllShips() {
        return new Coordinates[][]{destroyer, cruiser1, cruiser2, battleship, carrier};
    }
    
    void addPossibleConfiguration(GridPacker gp) {
        this.possibleConfigurations.add(gp);
    }
    
    void generateAllConfigurations() {
        // for each ship, generate all the possible positions it can be in (in a
        // new GridPacker for each configuration) and add them to the ArrayList,
        // possibleConfigurations
        for (Coordinates[] ship : getAllShips()) {
            for (GridPacker newConfig : makeShipConfigurations(ship)) {
                addPossibleConfiguration(newConfig);
                newConfig.generateAllConfigurations();
            }
        }
    }
    
    GridPacker[] makeShipConfigurations(Coordinates[] ship) {
        // instantiate new ArrayList to hold the new configurations
        ArrayList<GridPacker> results = new ArrayList<>();
        // if
        // convert and return an array containing the new configs
        return (GridPacker[])results.toArray();
    }
    
    
    
}
