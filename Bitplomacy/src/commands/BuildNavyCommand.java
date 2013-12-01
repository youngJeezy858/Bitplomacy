package commands;

import canvases.GameCanvas;
import orders.BuildNavyOrder;

/**
 * The Class BuildNavyCommand.
 */
public class BuildNavyCommand extends Commands {

	/**
	 * Instantiates a new builds the navy command.
	 *
	 * @param x the x coordinate to draw this command
	 * @param y the y coordinate to draw this command
	 */
	public BuildNavyCommand(float x, float y) {
		super(x, y);
	}

	/* (non-Javadoc)
	 * @see gui.Commands#execute()
	 */
	@Override
	public void execute() {
		if (GameCanvas.getC().getCurrentTerritory() != null){
			GameCanvas.getC().setOrder(new BuildNavyOrder(GameCanvas.getC().getCurrentTerritory()));
			GameCanvas.getC().setState(GameCanvas.NORM);
		}
	}

}
