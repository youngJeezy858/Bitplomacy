package commands;

import canvases.GameCanvas;
import orders.AttackOrder;

/**
 * The Class AttackCommand.
 */
public class AttackCommand extends Commands{

	/**
	 * Instantiates a new attack command.
	 *
	 * @param x the x coordinate to draw this command
	 * @param y the y coordinate to draw this command
	 */
	public AttackCommand(float x, float y) {
		super(x, y);
	}

	/* (non-Javadoc)
	 * @see gui.Commands#execute()
	 */
	@Override
	public void execute() {
		if (GameCanvas.getC().getCurrentTerritory() != null){
			GameCanvas.getC().setOrder(new AttackOrder(GameCanvas.getC().getCurrentTerritory()));
			GameCanvas.getC().setState(GameCanvas.COMM_SELECTED);
		}
	}

}
