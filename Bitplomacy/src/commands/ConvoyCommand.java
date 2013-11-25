package commands;

import canvases.GameCanvas;
import orders.ConvoyOrder;

/**
 * The Class ConvoyCommand.
 */
public class ConvoyCommand extends Commands{

	/**
	 * Instantiates a new convoy command.
	 *
	 * @param x the x
	 * @param y the y
	 * @param color the color
	 */
	public ConvoyCommand(float x, float y) {
		super(x, y);
	}

	/* (non-Javadoc)
	 * @see gui.Commands#execute()
	 */
	@Override
	public void execute() {
		if (GameCanvas.getC().getCurrentTerritory() != null){
			GameCanvas.getC().setOrder(new ConvoyOrder(GameCanvas.getC().getCurrentTerritory()));
			GameCanvas.getC().setState(GameCanvas.COMM_SELECTED);
		}
	}

}
