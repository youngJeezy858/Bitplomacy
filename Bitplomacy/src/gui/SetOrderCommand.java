package gui;

public class SetOrderCommand extends Commands {

	public SetOrderCommand(float x, float y, int color) {
		super(x, y, color);
	}

	@Override
	public void execute() {
		Canvas.getC().finalizeOrder();
	}

}
