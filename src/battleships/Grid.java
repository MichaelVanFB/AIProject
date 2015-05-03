package battleships;

import java.util.ArrayList;
import java.util.ListIterator;

public class Grid<E extends Case>
{   
    /**
     * Can be a 2D array of Case or Probability case.
     */
    protected E[][] cases;
 
    // constructor to make a blank grid
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
    
    void setCase(int row, int column, E newCase) {
        this.cases[row][column] = newCase;
    }
    
    int getHeight() {
        return cases.length;
    }
    
    int getWidth() {
        return cases[0].length;
    }
    
    boolean isEmpty() {
        for (int i = 0; i < this.getHeight(); i++) {
            for (int j = 0; j < this.getWidth(); j++) {
                if (cases[i][j].getContents() != CaseContents.UNKNOWN) {
                    return false;
                }
            }
        }
        return true;
    }
}