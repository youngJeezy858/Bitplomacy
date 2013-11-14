package gui;

// TODO: Auto-generated Javadoc
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
	public DiscardOrderCommand(float x, float y, int color) {
		super(x, y, color);
	}

	/* (non-Javadoc)
	 * @see gui.Commands#execute()
	 */
	@Override
	public void execute() {
		Canvas.getC().discardOrder();
	}

}
