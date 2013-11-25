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
	 * @param x the x
	 * @param y the y
	 * @param color the color
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
