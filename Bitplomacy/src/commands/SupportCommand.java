package commands;

import canvases.GameCanvas;
import orders.SupportOrder;

/**
 * The Class SupportCommand.
 */
public class SupportCommand extends Commands{

	/**
	 * Instantiates a new support command.
	 *
	 * @param x the x coordinate to draw this command
	 * @param y the y coordinate to draw this command
	 */
	public SupportCommand(float x, float y) {
		super(x, y);
	}

	/* (non-Javadoc)
	 * @see gui.Commands#execute()
	 */
	@Override
	public void execute() {
		if (GameCanvas.getC().getCurrentTerritory() != null){
			GameCanvas.getC().setOrder(new SupportOrder(GameCanvas.getC().getCurrentTerritory()));
			GameCanvas.getC().setState(GameCanvas.COMM_SELECTED);
		}
	}

}
