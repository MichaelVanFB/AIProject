package battleships;

public class Grid<E extends Case>
{   
    /**
     * cases can be a 2D array of Case or Probability case.
     * It can be final as Case instances won't be added/deleted or moved,
     * only the contents of each Case will be changed.
     */
    protected final E[][] cases;
    
    
 
    // contrsuctor to make a blank grid
    Grid(int height, int width) {
        cases = (E[][]) new Object[height][width];
    }
    
    /**
     * Constructor to make a grid that is a copy of another.
     * We want to make copies (not clones!) of each case in the grid.
     */
    Grid(Grid<E> grid) {
        // make a new empty grid of the same size
        this(grid.getHeight(), grid.getWidth());
        // iteratate over the cases of the new grid (this) and set them to the
        // same values as those of the grid being copied
        for (int i = 0; i < grid.getHeight(); i++) {
            for (int j = 0; j < grid.getHeight(); j++) {
                this.cases[i][j] = (E) grid.cases[i][j].copy();
            }
        }
    }
    
    E getCase(int row, int column) {
        if (row >= getHeight() || column >= getWidth()) {
            return null;
        }
        return cases[row][column];
    }
    
    
    
    int getHeight() {
        return cases.length;
    }
    
    int getWidth() {
        return cases[0].length;
    }
    
    boolean isEmpty() {
        for (int i = 0; i < this.getHeight(); i++) {
            for (int j = 0; j < this.getHeight(); j++) {
                if (cases[i][j].getContents() != CaseContents.UNKNOWN) {
                    return false;
                }
            }
        }
        return true;
    }
    
    boolean placeShipAt(ShipType type, char orientation, int row, int column) {
        // we have already checked the case isn't ocean (i.e. missed shot), so
        // the first step is to make sure that it is legal to place a new ship
        // of the given type
        
        // Next we make sure placing the ship in the given orientation would not
        // go over the border
        if (
            (
                orientation == 'v' &&
                (
                    (row >= getHeight()) ||
                    getHeight() - row - 1 < type.getLength()
                )
            ) ||
            (
                orientation == 'h' &&
                (
                    (column >= getWidth()) ||
                    getWidth() - column - 1 < type.getLength()
                )
            )    
        ) {
            return false;
        }
        // now we must check whether
        return true;
    }
    
    boolean canAddShipType(ShipType type) {
        return false;
    }
}