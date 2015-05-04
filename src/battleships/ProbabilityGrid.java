package battleships;

import java.util.ArrayList;
import java.util.ListIterator;

/**
 * This is a convenience class that sets the type of Grid to ProbabilityCase, so
 * that we can use this class in the place of Grid<ProbabilityCase>.
 * Extra convenience methods could also be added...
 */
public class ProbabilityGrid extends Grid<ProbabilityCase>
{
    ProbabilityGrid(int height, int width) {
        // instanciate the superclass with ProbabilityCase as a type parameter
        super(height, width);
    }
    
    ProbabilityGrid(Grid<ProbabilityCase> grid) {
        super(grid);
    }
    
    ProbabilityGrid(ProbabilityCase[][] cases) {
        super(cases);
    }
    
}
