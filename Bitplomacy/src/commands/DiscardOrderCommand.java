package commands;

import canvases.GameCanvas;

/**
 * The Class DiscardOrderCommand.
 */
public class DiscardOrderCommand extends Commands {

	/**
	 * Instantiates a new discard order command.
	 *
	 * @param x the x
	 * @param y the y
	 * @param color the color
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
