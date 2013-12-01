package commands;

import canvases.GameCanvas;

/**
 * The Class SubmitCommand.
 */
public class SubmitCommand extends Commands{

	/**
	 * Instantiates a new submit command.
	 *
	 * @param x the x coordinate to draw this command
	 * @param y the y coordinate to draw this command
	 */
	public SubmitCommand(float x, float y) {
		super(x, y);
	}

	/* (non-Javadoc)
	 * @see gui.Commands#execute()
	 */
	@Override
	public void execute() {
		GameCanvas.getC().submit();		
	}

}
