package battleships;

import java.util.ArrayList;
import java.util.ListIterator;

public class GridPacker
{    
    private static int instanceCount = 0;
    
    /**
     * The probability of this configuration occurring, given its parent
     * GridPacker has occurred. The root GridPacker will have this set to 1.
     */
    private float condProbability;
    
    /**
     * The starting grid from which we calculate all the possible ship
     * configurations.
     */
    private ProbabilityGrid grid;
    
    /**
     * ArrayList of GridPackers, each containing the different possible configs
     * formed by legally guessing the place of one more case containing a ship.
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
        if (p > 1 ) {
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
    
    void addPossibleConfiguration(GridPacker gp) {
        this.possibleConfigurations.add(gp);
    }
    
    boolean generateAllConfigurations() {

        return true;
    }
    
}
