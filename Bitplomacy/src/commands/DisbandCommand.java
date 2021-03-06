package commands;

import canvases.GameCanvas;
import orders.DisbandOrder;

/**
 * The Class RemoveUnitCommand.
 */
public class DisbandCommand extends Commands {

	/**
	 * Instantiates a new removes the unit command.
	 *
	 * @param x the x coordinate to draw this command
	 * @param y the y coordinate to draw this command
	 */
	public DisbandCommand(float x, float y) {
		super(x, y);
	}

	/* (non-Javadoc)
	 * @see gui.Commands#execute()
	 */
	@Override
	public void execute() {
		if (GameCanvas.getC().getCurrentTerritory() != null){
			GameCanvas.getC().setOrder(new DisbandOrder(GameCanvas.getC().getCurrentTerritory()));
			GameCanvas.getC().setState(GameCanvas.NORM);
		}
	}

}
