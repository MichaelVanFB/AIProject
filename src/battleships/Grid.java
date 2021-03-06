package battleships;

import java.util.ArrayList;
import java.util.ListIterator;

public class Grid
{   
    /**
     * A 2D array of Case or ProbabilityCase instances.
     */
    protected Case[][] cases;
 
    /**
     * constructor to make a blank grid of known dimensions.
     */
    Grid(int height, int width) {
        cases = new Case[height][width];
    }
    
    Grid(Case[][] stateCases) {
        this.cases = stateCases;
    }
    
    /**
     * Constructor to make a grid that is a copy of another.
     * 
     * We want to make copies (not clones!) of each case in the grid.
     */
    Grid(Grid grid) {
        // make a new empty grid of the same size
        this(grid.getHeight(), grid.getWidth());
        // iteratate over the cases of the new grid (this) and set them to the
        // same values as those of the grid being copied
        for (int i = 0; i < grid.getHeight(); i++) {
            for (int j = 0; j < grid.getHeight(); j++) {
                this.cases[i][j] = grid.cases[i][j].copy();
            }
        }        
    }
    
    Case[][] getCases() {
        return this.cases;
    }
    
    Case getCase(int row, int column) {
        if (row >= getHeight() || column >= getWidth()) {
            return null;
        }
        return cases[row][column];
    }
    
    void setCase(int row, int column, Case newCase) {
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