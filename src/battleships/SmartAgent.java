package battleships;

import javafx.scene.control.Label;

public class SmartAgent extends Player {

    GridPacker moveCalculator;
    
    public SmartAgent(int id, String name, Label[][] grid) {
        super(id, name, grid);
    }
    
    public Coordinates ChooseCase() {
        // (re)make the GridPacker using the current state of the game
        moveCalculator = new GridPacker(new ProbabilityGrid(this.getGrid()));
        // generate all configurations possible
        moveCalculator.generateAllConfigurations();
        // generate all case probabilities
        moveCalculator.calculateProbabilities();
        // pick the best one
        chooseMaxProb();
    }
    
    public Coordinates chooseMaxProb() {
        ProbabilityCase[][] cases = (ProbabilityCase[][]) moveCalculator.getGrid().cases;
        for (int i = 0; i < cases.length; i++) {
            for (int j = 0; j < cases[0].length; j++) {

            }
        }
    }
}
