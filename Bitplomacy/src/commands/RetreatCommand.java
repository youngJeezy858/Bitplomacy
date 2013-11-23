package commands;

import canvases.GameCanvas;
import orders.RetreatOrder;

/**
 * The Class RetreatCommand.
 */
public class RetreatCommand extends Commands{

	/**
	 * Instantiates a new retreat command.
	 *
	 * @param x the x
	 * @param y the y
	 * @param color the color
	 */
	public RetreatCommand(float x, float y, int color) {
		super(x, y, color);
	}

	/* (non-Javadoc)
	 * @see gui.Commands#execute()
	 */
	@Override
	public void execute() {
		GameCanvas.getC().setOrder(new RetreatOrder(GameCanvas.getC().getCurrentTerritory()));
		GameCanvas.getC().setState(GameCanvas.COMM_SELECTED);
	}

}
