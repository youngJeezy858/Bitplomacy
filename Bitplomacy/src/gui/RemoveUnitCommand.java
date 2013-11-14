package gui;

// TODO: Auto-generated Javadoc
/**
 * The Class RemoveUnitCommand.
 */
public class RemoveUnitCommand extends Commands {

	/**
	 * Instantiates a new removes the unit command.
	 *
	 * @param x the x
	 * @param y the y
	 * @param color the color
	 */
	public RemoveUnitCommand(float x, float y, int color) {
		super(x, y, color);
	}

	/* (non-Javadoc)
	 * @see gui.Commands#execute()
	 */
	@Override
	public void execute() {
		Canvas.getC().setCommand("remove unit");
	}

}
