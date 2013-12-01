package commands;

import canvases.GameCanvas;

/**
 * The Class SetOrderCommand.
 */
public class SetOrderCommand extends Commands {

	/**
	 * Instantiates a new sets the order command.
	 *
	 * @param x the x coordinate to draw this command
	 * @param y the y coordinate to draw this command
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
