package battleships;

import java.util.ArrayList;
import java.util.ListIterator;

public class Grid<E extends Case>
{   
    /**
     * Can be a 2D array of Case or Probability case.
     */
    protected E[][] cases;
    
    protected ArrayList<ArrayList<Coordinates>> knownCasesByType;
 
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
        // copy the coordinates of the known ship cases
        this.knownCasesByType = grid.knownCasesByType;
        
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
    
    ArrayList<ArrayList<Coordinates>> getKnownCasesByType() {
        return this.knownCasesByType;
    }
    
    ArrayList<Coordinates> getKnownCasesByType(int index) {
        return this.knownCasesByType.get(index);
    }
    
    void addKnownCase(int typeIndex, Coordinates coords) {
        this.knownCasesByType.get(typeIndex).add(coords);
    }
    
    void addKnownCase(ShipType type, Coordinates coords) {
        int typeIndex = type.getLength() - 2;
        this.knownCasesByType.get(typeIndex).add(coords);
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
    
    /**
     * Have all the ships been found?
     * @return 
     */
    boolean isFull() {
        // check the number of known coordinates of each type
        ListIterator<ArrayList<Coordinates>> typeIter = this.knownCasesByType.listIterator();
        int numShipCases = 0;
        while (typeIter.hasNext()) {
            ArrayList<Coordinates> shipCases = typeIter.next();
            numShipCases += shipCases.size();
        }
        return numShipCases == 17;
    } 
}