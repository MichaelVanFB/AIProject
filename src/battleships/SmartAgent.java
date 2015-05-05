package battleships;

import javafx.scene.control.Label;

public class SmartAgent extends Player {
	
    private GridPacker moveCalculator;
    
    public SmartAgent(int id, String name, Label[][] grid) {
        super(id, name, grid);
    }
    
    public Coordinates ChooseCase() {
        // (re)make the GridPacker using the current state of the game
        moveCalculator = new GridPacker(new Grid(this.getGridCase()));
        // generate all configurations possible
        moveCalculator.generateAllConfigurations();
        // generate all case probabilities
        moveCalculator.calculateProbabilities();
        // pick the best one
        return chooseMaxProb();
    }
    
    public Coordinates chooseMaxProb() {
        Case[][] cases = moveCalculator.getGrid().cases;
        Coordinates max = null;
        for (int i = 0; i < cases.length; i++) {
            for (int j = 0; j < cases[0].length; j++) {
                Case c = cases[i][j];
                if (max == null && c.getContents() == CaseContents.UNKNOWN) {
                    max = new Coordinates(i,j);
                }
                else if (c.getContents() == CaseContents.UNKNOWN && c.getProbabilityIsShip() > cases[max.getX()][max.getY()].getProbabilityIsShip()) {
                    max = new Coordinates(i,j);
                }
            }
        }
        return max;
    }
    public GridPacker getGridPacker(){
    	return this.moveCalculator;
    }
}
