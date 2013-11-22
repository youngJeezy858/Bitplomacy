package gui;

// TODO: Auto-generated Javadoc
/**
 * The Class RetreatCommand.
 */
public class RetreatCommand extends Commands{

	/**
	 * Instantiates a new retreat command.
	 *
	 * @param x the x
	 * @param y the y
	 * @param color the color
	 */
	public RetreatCommand(float x, float y, int color) {
		super(x, y, color);
	}

	/* (non-Javadoc)
	 * @see gui.Commands#execute()
	 */
	@Override
	public void execute() {
		Canvas.getC().setCommand("retreat");
	}

}
