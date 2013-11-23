package commands;

import canvases.GameCanvas;
import orders.DefendOrder;

/**
 * The Class DefendCommand.
 */
public class DefendCommand extends Commands{

	/**
	 * Instantiates a new defend command.
	 *
	 * @param x the x
	 * @param y the y
	 * @param color the color
	 */
	public DefendCommand(float x, float y, int color) {
		super(x, y, color);
	}

	/* (non-Javadoc)
	 * @see gui.Commands#execute()
	 */
	@Override
	public void execute() {
		GameCanvas.getC().setOrder(new DefendOrder(GameCanvas.getC().getCurrentTerritory()));
	}

}
