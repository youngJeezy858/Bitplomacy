package gui;

public class RemoveUnitCommand extends Commands {

	public RemoveUnitCommand(float x, float y, int color) {
		super(x, y, color);
	}

	@Override
	public void execute() {
		Canvas.getC().setCommand("remove unit");
	}

}
