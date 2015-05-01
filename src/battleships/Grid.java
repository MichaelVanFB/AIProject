package battleships;

public class Grid<E extends Case>
{   
    /**
     * cases can be a 2D array of Case or Probability case.
     * It can be final as Case instances won't be added/deleted or moved,
     * only the contents of each Case will be changed.
     */
    private final E[][] cases;
    
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
    
    int getHeight() {
        return cases.length;
    }
    
    int getWidth() {
        return cases[0].length;
    }
}