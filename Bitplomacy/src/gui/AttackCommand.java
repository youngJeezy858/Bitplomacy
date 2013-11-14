package gui;

// TODO: Auto-generated Javadoc
/**
 * The Class AttackCommand.
 */
public class AttackCommand extends Commands{

	/**
	 * Instantiates a new attack command.
	 *
	 * @param x the x
	 * @param y the y
	 * @param color the color
	 */
	public AttackCommand(float x, float y, int color) {
		super(x, y, color);
	}

	/* (non-Javadoc)
	 * @see gui.Commands#execute()
	 */
	@Override
	public void execute() {
		Canvas.getC().setCommand("attack");
	}

}
