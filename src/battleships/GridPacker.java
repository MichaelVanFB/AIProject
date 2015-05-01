package battleships;

public class GridPacker
{    
    /**
     * The starting grid from which we calculate all the possible ship
     * configurations. Once made the grid is final, but we can still make
     * changes to the cases of the grid.
     */
    private final ProbabilityGrid grid;
    
    /**
     * An array of all the different possible configurations formed by placing
     * one more ship on the grid.
     */
    private ProbabilityGrid[] possibleConfigurations;
    
    public GridPacker(Grid grid) {
        // set the grid
        this.grid = new ProbabilityGrid(grid);
    }
    
    public Grid getGrid() {
        return this.grid;
    }
    
    
}
