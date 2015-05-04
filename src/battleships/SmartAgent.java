package battleships;

import javafx.scene.control.Label;

public class SmartAgent extends Player {
	
	private GridPacker moveCalculator;

	public SmartAgent(int id, String name, Label[][] grid) {
		super(id, name, grid);
	}
	
}
