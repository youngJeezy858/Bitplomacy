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
	 * @param x the x coordinate to draw this command
	 * @param y the y coordinate to draw this command
	 */
	public DefendCommand(float x, float y) {
		super(x, y);
	}

	/* (non-Javadoc)
	 * @see gui.Commands#execute()
	 */
	@Override
	public void execute() {
		if (GameCanvas.getC().getCurrentTerritory() != null){
			GameCanvas.getC().setOrder(new DefendOrder(GameCanvas.getC().getCurrentTerritory()));
			GameCanvas.getC().setState(GameCanvas.NORM);
		}
	}

}
