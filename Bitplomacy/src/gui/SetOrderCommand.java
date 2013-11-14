package gui;

// TODO: Auto-generated Javadoc
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
	public SetOrderCommand(float x, float y, int color) {
		super(x, y, color);
	}

	/* (non-Javadoc)
	 * @see gui.Commands#execute()
	 */
	@Override
	public void execute() {
		Canvas.getC().finalizeOrder();
	}

}
