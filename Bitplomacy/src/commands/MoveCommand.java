package commands;

import canvases.GameCanvas;
import orders.MoveOrder;

/**
 * The Class MoveCommand.
 */
public class MoveCommand extends Commands{

	/**
	 * Instantiates a new move command.
	 *
	 * @param x the x
	 * @param y the y
	 * @param color the color
	 */
	public MoveCommand(float x, float y) {
		super(x, y);
	}

	/* (non-Javadoc)
	 * @see gui.Commands#execute()
	 */
	@Override
	public void execute() {
		if (GameCanvas.getC().getCurrentTerritory() != null){
			GameCanvas.getC().setOrder(new MoveOrder(GameCanvas.getC().getCurrentTerritory()));
			GameCanvas.getC().setState(GameCanvas.COMM_SELECTED);
		}
	}

}
