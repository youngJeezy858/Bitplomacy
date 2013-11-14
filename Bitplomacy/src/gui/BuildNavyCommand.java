package gui;

// TODO: Auto-generated Javadoc
/**
 * The Class BuildNavyCommand.
 */
public class BuildNavyCommand extends Commands {

	/**
	 * Instantiates a new builds the navy command.
	 *
	 * @param x the x
	 * @param y the y
	 * @param color the color
	 */
	public BuildNavyCommand(float x, float y, int color) {
		super(x, y, color);
	}

	/* (non-Javadoc)
	 * @see gui.Commands#execute()
	 */
	@Override
	public void execute() {
		Canvas.getC().setCommand("build navy");
	}

}
