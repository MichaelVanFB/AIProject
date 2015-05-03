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
    
    /**
     * A ProbabilityGrid containing all the different possible configs
     * formed after inferring from the rules and the current configuration where
     * one or more cases must contain a ship.
     */
    private ProbabilityGrid inferredConfiguration;
    
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
    
    ProbabilityGrid getInferredConfiguration() {
        return inferredConfiguration;
    }
    
    void setInferredConfiguration(ProbabilityGrid grid) {
        this.inferredConfiguration = grid;
    }
    
    // set the middle cases' probability to 1 and add them to inferredConfigurations
    boolean setCasesBetween(ArrayList<Coordinates> shipCases, ShipType type, ProbabilityGrid grid) {
        CaseContents contents = type.convertToCaseContents();
        if (Coordinates.alignedX( (Coordinates[]) shipCases.toArray() )) {
            // get x ordinate, which should be the same for all in the array
            int x = shipCases.get(0).getX();
            // get minimum, to start from
            int startY = Coordinates.minY( (Coordinates[]) shipCases.toArray() );
            // get max, to end at
            int endY = Coordinates.maxY( (Coordinates[]) shipCases.toArray() );
            // iterate over middle cases and add cases where don't yet exist
            for (int y = startY; y <= endY; y++) {
                // check for existance
                if (grid.cases[x][y].getContents() == CaseContents.UNKNOWN) {
                    // make the probabilitycase
                    ProbabilityCase newShipCase = new ProbabilityCase(contents, 1);
                    // add it to the grid at the relevent coordinates
                    grid.setCase(x, y, newShipCase);
                    // add it to the grid's list of known ship cases
                    grid.addKnownCase(type, new Coordinates(x,y));
                }
            }
            return true;
        }
        else if (Coordinates.alignedY( (Coordinates[]) shipCases.toArray() )) {
            // get y ordinate, which should be the same for all in the array
            int y = shipCases.get(0).getY();
            // get minimum, to start from
            int startX = Coordinates.minX( (Coordinates[]) shipCases.toArray() );
            // get max, to end at
            int endX = Coordinates.maxX( (Coordinates[]) shipCases.toArray() );
            // iterate over middle cases and add cases where don't yet exist
            for (int x = startX; x <= endX; x++) {
                // check for existance and add only if doesn't exist yet
                if (grid.cases[x][y].getContents() == CaseContents.UNKNOWN) {
                    // make the probabilitycase
                    ProbabilityCase newShipCase = new ProbabilityCase(contents, 1);
                    // add it to the grid at the relevent coordinates
                    grid.setCase(x, y, newShipCase);
                    // add it to the grid's list of known ship cases
                    grid.addKnownCase(type, new Coordinates(x,y));
                }
            }
            return true;
        }
        return false;
    }
    
    boolean fillInFreeDirection(ArrayList<Coordinates> shipCases, ShipType type, ProbabilityGrid grid) {
        if (Coordinates.alignedX( (Coordinates[]) shipCases.toArray() )) {
            int x = shipCases.get(0).getX();
            // check case at minY - 1
            int startY = Coordinates.minY( (Coordinates[]) shipCases.toArray() );
            // check case at maxY + 1
            int endY = Coordinates.maxY( (Coordinates[]) shipCases.toArray() );
            if (startY == 0 || grid.cases[x][startY - 1].getContents() != CaseContents.UNKNOWN) {
                // add new calculated end case            
                shipCases.add(new Coordinates(x, endY));
            }
            else if (endY == grid.getWidth() - 1 || grid.cases[x][endY + 1].getContents() != CaseContents.UNKNOWN) {
                // add new calculated start case  
                shipCases.add(new Coordinates(x, endY - type.getLength() + 1 )); // + 1 as 0-indexed
            }
            return setCasesBetween(shipCases, type, grid);
        }
        else if (Coordinates.alignedY( (Coordinates[]) shipCases.toArray() )) {
            int y = shipCases.get(0).getY();
            // check case at minX - 1
            int startX = Coordinates.minX( (Coordinates[]) shipCases.toArray() );
            // check case at maxX + 1
            int endX = Coordinates.maxX( (Coordinates[]) shipCases.toArray() );
            if (startX == 0 || grid.cases[startX - 1][y].getContents() != CaseContents.UNKNOWN) {
                // add new calculated end case            
                shipCases.add(new Coordinates(endX, y));
            }
            else if (endX == grid.getHeight() - 1 || grid.cases[endX + 1][y].getContents() != CaseContents.UNKNOWN) {
                // add new calculated start case  
                shipCases.add(new Coordinates(endX - type.getLength() + 1  , y)); // + 1 as 0-indexed
            }
            return setCasesBetween(shipCases, type, grid);
        }
        return false;
    }
    
    boolean extendInFreeDirection(ArrayList<Coordinates> shipCases, ShipType type, ProbabilityGrid grid) {
        Coordinates endCase = shipCases.get(0);
        int x = endCase.getX();
        int y = endCase.getY();
        // check which of the 4 sides have blocking neighbours
        boolean blockedNorth =  (x == 0 || grid.cases[x-1][y].getContents() != CaseContents.UNKNOWN);
        boolean blockedSouth =  (x == grid.getHeight() - 1 || grid.cases[x+1][y].getContents() != CaseContents.UNKNOWN);
        boolean blockedWest =  (y == 0 || grid.cases[x][y-1].getContents() != CaseContents.UNKNOWN);
        boolean blockedEast =  (y == grid.getWidth() - 1 || grid.cases[x][y+1].getContents() != CaseContents.UNKNOWN);
        // prepare the arraylist that will be passed to the setCasesBetween method
        ArrayList<Coordinates> boundingCases = new ArrayList<>();
        boundingCases.add(endCase);
        if (blockedNorth && blockedWest && blockedSouth) {
            // extend in positive y direction
            boundingCases.add(new Coordinates(x,y + type.getLength() ));
            return setCasesBetween(boundingCases, type, grid);
        }
        else if (blockedNorth && blockedEast && blockedSouth) {
            // extend in negative y direction
            boundingCases.add(new Coordinates(x,y - type.getLength() ));
            return setCasesBetween(boundingCases, type, grid);
        }
        else if (blockedNorth && blockedEast && blockedWest) {
            // extend in positive x direction
            boundingCases.add(new Coordinates(x + type.getLength(), y));
            return setCasesBetween(boundingCases, type, grid);
        }
        else if (blockedWest && blockedEast && blockedSouth) {
            // extend in negative x direction
            boundingCases.add(new Coordinates(x - type.getLength(), y));
            return setCasesBetween(boundingCases, type, grid);
        }
        return false;
    }
    
    boolean fillInCaged(ArrayList<Coordinates> shipCases, ShipType type, ProbabilityGrid grid) {
        Coordinates knownCase = shipCases.get(0);
        int x = knownCase.getX();
        int y = knownCase.getY();
        // check on which axis the case has 2 blocking neighbours
        boolean blockedNorth =  (x == 0 || grid.cases[x-1][y].getContents() != CaseContents.UNKNOWN);
        boolean blockedSouth =  (x == grid.getHeight() - 1 || grid.cases[x+1][y].getContents() != CaseContents.UNKNOWN);
        boolean blockedWest =  (y == 0 || grid.cases[x][y-1].getContents() != CaseContents.UNKNOWN);
        boolean blockedEast =  (y == grid.getWidth() - 1 || grid.cases[x][y+1].getContents() != CaseContents.UNKNOWN);
        // make an arraylist we will use to fill in cases assuming the method finds a ship
        ArrayList<Coordinates> bounds = new ArrayList<>();
        if (blockedNorth && blockedSouth) {
            // go forward in the y direction to a max distance equalling the
            // ships length, checking for a blocking case. Count the number of 
            // cases gone forward
            int casesForward = 0;
            for (int v = y; v < y + type.getLength(); v++) {
                if (v < grid.getWidth() - 1 || grid.cases[x][v+1].getContents() == CaseContents.UNKNOWN) {
                    casesForward++;
                }
                else break;
            }
            // now go backwards...
            int casesBackward = 0;
            for (int v = y; v > y - type.getLength(); v--) {
                if (v > 0 || grid.cases[x][v-1].getContents() == CaseContents.UNKNOWN) {
                    casesBackward++;
                }
                else break;
            }
            // check the sum
            if (casesBackward + casesForward + 1 == type.getLength()) {
                // exactly right length, therefore ship found
                // add start and end case to a new ArrayList we will use to construct
                bounds.add(new Coordinates(x,y - casesBackward));
                bounds.add(new Coordinates(x,y + casesForward));
                return setCasesBetween(bounds, type, grid);
            }
            return false;
        }
        if (blockedWest && blockedEast) {
            // go forward in the x direction to a max distance equalling the
            // ships length, checking for a blocking case. Count the number of 
            // cases gone forward
            int casesForward = 0;
            for (int u = x; u < x + type.getLength(); u++) {
                if (u < grid.getHeight() - 1 || grid.cases[u+1][y].getContents() == CaseContents.UNKNOWN) {
                    casesForward++;
                }
                else break;
            }
            // same for reverse direction
            int casesBackward = 0;
            for (int u = x; u > x - type.getLength(); u--) {
                if (u > 0 || grid.cases[u-1][y].getContents() == CaseContents.UNKNOWN) {
                    casesBackward++;
                }
                else break;
            }
            // check the sum
            if (casesBackward + casesForward + 1 == type.getLength()) {
                // exactly right length, therefore ship found
                // add start and end case to a new ArrayList we will use to construct
                bounds.add(new Coordinates(x - casesBackward,y));
                bounds.add(new Coordinates(x + casesForward,y));
                return setCasesBetween(bounds, type, grid);
            }
            return false;
        }
        return false;
    }
    
    ArrayList<Coordinates>[] discernibleCruisers(ArrayList<Coordinates> shipCases) {
        // We can only be sure 2 cruiser cases belong to separate ships if they
        // have at least distance 3 between them on the same axis
        // We must therefore split the cases in the ArrayList into 2 ArrayLists
        ArrayList<Coordinates> cruiser1 = new ArrayList<>();
        ArrayList<Coordinates> cruiser2 = new ArrayList<>();
        // start with the first Coordinates element in cruiser1
        cruiser1.add(shipCases.get(0));
        // now we iterate over the rest and add to the first only if distance
        // is greater than or equal to 3
    }
    
    boolean inferForType(ShipType type, ArrayList<Coordinates> shipCases) {
        /**
         * we can infer the remaining cases  of a ship if: we know the 2
         * outer; we know 2 cases, one blocked on the aligning axis by another
         * ship or known ocean; we know one case which is blocked on 3 sides; we
         * know one case which is blocked by aligned neighbours on either side
         * and by cases on the other axis at a distance apart equal to the
         * length of the ship.
         * The exception to this is for cruisers, which don't obey the same
         * logic as there are 2 of them on the grid, UNLESS we can be sure the 2
         * are a certain distance apart. Thus for cruisers we call this method
         * twice, once with each separated set of cases for each discernible cruiser
         */
        int typeLen = type.getLength();
        int numKnown = shipCases.size();
        // check if all the cases have already been found
        if (numKnown == typeLen) {
            return false; // nothing left to infer!
        }
        if (numKnown >= 2) {
            boolean anyInferred = false;
            // if scenario 1 where we know the 2 end cases
            if (Coordinates.distance( (Coordinates[]) shipCases.toArray() ) == typeLen) {
                anyInferred = setCasesBetween(shipCases, type, inferredConfiguration);
            }
            // scenario 2 where we know at least 2 cases (therefore orientation) and one
            // is bocked by a neighbour on the far side
            else if (fillInFreeDirection(shipCases, type, inferredConfiguration)) {
                anyInferred = true;
            }
            // case where one point is know but may be caged on two same-axis
            // sides and at a distance equal to the ships lenght on the other axis
            else if (fillInCaged(shipCases, type, inferredConfiguration)) {
                anyInferred = true;
            }
            return anyInferred;
        }
        else if (numKnown == 1) {
            // check for scenario where the know case is blocked on 3 sides
            return extendInFreeDirection(shipCases, type, inferredConfiguration);
        }
        return false;
    }
    
    boolean inferForCruisers(ArrayList<Coordinates> shipCases) {
        return false;
    }
    
    /**
     * Go through each array of cases known to contain a ship of a
     * certain kind and apply logic to infer the locations of cases that
     * must contain a ship.
     * @return 
     */
    boolean findInferredConfigurations() {
        /* first check coordinates arrays to see if we can make any iferences
         * using ship length. This is only possible for cruisers, battleships
         * and carriers
         */
        ArrayList<Coordinates> battleshipCases = this.grid.getKnownCasesByType(3);
        
        return true;
    }
    
    void findPossibleConfigurations() {
        
    }
    
    boolean generateAllConfigurations() {
        // we stop the algorithm immediately when the board is full.
        // This should bubble up the recursive call stack.
        if (grid.isFull()) {
            return false;
        }
        return true;
    }
    
}
