package battleships;

import java.util.ArrayList;
import java.util.Random;
import javafx.scene.control.Label;

public class SmartAgent extends Player {
	
    private GridPacker moveCalculator;
    
    public SmartAgent(int id, String name, Label[][] grid) {
        super(id, name, grid);
    }
    
    public Coordinates ChooseCase() {
        // (re)make the GridPacker using the current state of the game
        moveCalculator = new GridPacker(new Grid(this.getOpponentGridCase()));
        // generate all configurations possible
        moveCalculator.generateAllConfigurations();
        // generate all case probabilities
        moveCalculator.calculateProbabilities();
        // pick the best one
        return chooseMaxProb();
    }
    
    public Coordinates chooseMaxProb() {
        Case[][] cases = moveCalculator.getGrid().cases;
        ArrayList<Coordinates> max = new ArrayList<>();
        for (int i = 0; i < cases.length; i++) {
            for (int j = 0; j < cases[0].length; j++) {
                Case c = cases[i][j];
                if (max.isEmpty() && c.getContents() == CaseContents.UNKNOWN) {
                    max.add(new Coordinates(i,j));
                }
                else if (c.getContents() == CaseContents.UNKNOWN && c.getProbabilityIsShip() > cases[max.get(0).getX()][max.get(0).getY()].getProbabilityIsShip()) {
                    max = new ArrayList<Coordinates>();
                    max.add(new Coordinates(i,j));
                }
                else if (c.getContents() == CaseContents.UNKNOWN && c.getProbabilityIsShip() == cases[max.get(0).getX()][max.get(0).getY()].getProbabilityIsShip()) {
                    max.add(new Coordinates(i,j));
                }
            }
        }
        Random randomiser = new Random();
        int ind = randomiser.nextInt(max.size());
        Coordinates chosen = max.get(ind);
        for (Coordinates co : max) {
            System.out.print(" ("+co.getX()+","+co.getY()+")");
        }
        System.out.println("\nshooting at ("+chosen.getX()+","+chosen.getY()+")");
        return chosen;
    }
    public GridPacker getGridPacker(){
    	return this.moveCalculator;
    }
}
