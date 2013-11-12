package gui;

public class MoveCommand extends Commands{

	public MoveCommand(float x, float y, int color) {
		super(x, y, color);
	}

	@Override
	public void execute() {
		Canvas.getC().setCommand("move");
	}

}
