package gui;

// TODO: Auto-generated Javadoc
/**
 * The Class SubmitCommand.
 */
public class SubmitCommand extends Commands{

	/**
	 * Instantiates a new submit command.
	 *
	 * @param x the x
	 * @param y the y
	 * @param color the color
	 */
	public SubmitCommand(float x, float y, int color) {
		super(x, y, color);
	}

	/* (non-Javadoc)
	 * @see gui.Commands#execute()
	 */
	@Override
	public void execute() {
		Canvas.getC().submit();		
	}

}
