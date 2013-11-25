package commands;

import canvases.GameCanvas;

/**
 * The Class SetOrderCommand.
 */
public class SetOrderCommand extends Commands {

	/**
	 * Instantiates a new sets the order command.
	 *
	 * @param x the x
	 * @param y the y
	 * @param color the color
	 */
	public SetOrderCommand(float x, float y) {
		super(x, y);
	}

	/* (non-Javadoc)
	 * @see gui.Commands#execute()
	 */
	@Override
	public void execute() {
		GameCanvas.getC().finalizeOrder();
	}

}
