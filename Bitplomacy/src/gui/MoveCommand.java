package gui;

// TODO: Auto-generated Javadoc
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
	public MoveCommand(float x, float y, int color) {
		super(x, y, color);
	}

	/* (non-Javadoc)
	 * @see gui.Commands#execute()
	 */
	@Override
	public void execute() {
		Canvas.getC().setCommand("move");
	}

}
