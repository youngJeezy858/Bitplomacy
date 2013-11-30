package commands;

import canvases.GameCanvas;
import orders.BuildArmyOrder;

/**
 * The Class BuildArmyCommand.
 */
public class BuildArmyCommand extends Commands {

	/**
	 * Instantiates a new builds the army command.
	 *
	 * @param x the x
	 * @param y the y
	 * @param color the color
	 */
	public BuildArmyCommand(float x, float y) {
		super(x, y);
	}

	/* (non-Javadoc)
	 * @see gui.Commands#execute()
	 */
	@Override
	public void execute() {
		if (GameCanvas.getC().getCurrentTerritory() != null){
			GameCanvas.getC().setOrder(new BuildArmyOrder(GameCanvas.getC().getCurrentTerritory()));
			GameCanvas.getC().setState(GameCanvas.NORM);
		}
	}

}
