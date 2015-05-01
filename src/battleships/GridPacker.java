package battleships;

public class GridPacker
{
    class ProbabilityCase extends Grid.Case {
        
        private float probabilityIsShip = 0.0f; // use floats to save space
        
        ProbabilityCase(float probability) {
            this.probabilityIsShip = probability;
        }
        
        float getProbabilityIsShip() {
            return this.probabilityIsShip;
        }
        
        void setProbabilityIsShip(float probability) {
            this.probabilityIsShip = probability;
        }
    }
    
    /**
     * The starting grid from which we calculate all the possible ship
     * configurations.
     */
    private final Grid grid;
    
    /**
     * An array of all the different possible configurations formed by placing
     * one more ship on the grid.
     */
    private Grid[] possibleConfigurations;
    
    public GridPacker(Grid grid) {
        this.grid = grid;
    }
    
    public Grid getGrid() {
        return this.grid;
    }
    
    
}
