package commands;

import canvases.GameCanvas;

/**
 * The Class DiscardOrderCommand.
 */
public class DiscardOrderCommand extends Commands {

	/**
	 * Instantiates a new discard order command.
	 *
	 * @param x the x coordinate to draw this command
	 * @param y the y coordinate to draw this command
	 */
	public DiscardOrderCommand(float x, float y) {
		super(x, y);
	}

	/* (non-Javadoc)
	 * @see gui.Commands#execute()
	 */
	@Override
	public void execute() {
		GameCanvas.getC().discardOrder();
	}

}
