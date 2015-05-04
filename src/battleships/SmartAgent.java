package battleships;

import javafx.scene.control.Label;

public class SmartAgent extends Player {
	
    private GridPacker moveCalculator;
    
    public SmartAgent(int id, String name, Label[][] grid) {
        super(id, name, grid);
    }
    
    public Coordinates ChooseCase() {
        // (re)make the GridPacker using the current state of the game
        moveCalculator = new GridPacker(new ProbabilityGrid(this.getGridCase()));
        // generate all configurations possible
        moveCalculator.generateAllConfigurations();
        // generate all case probabilities
        moveCalculator.calculateProbabilities();
        // pick the best one
        return chooseMaxProb();
    }
    
    public Coordinates chooseMaxProb() {
        ProbabilityCase[][] cases = (ProbabilityCase[][]) moveCalculator.getGrid().cases;
        Coordinates max = new Coordinates(0,0);
        for (int i = 0; i < cases.length; i++) {
            for (int j = 0; j < cases[0].length; j++) {
                if (cases[i][j].getProbabilityIsShip() > cases[max.getX()][max.getY()].getProbabilityIsShip()) {
                    max = new Coordinates(i,j);
                }
            }
        }
        return max;
    }
}
