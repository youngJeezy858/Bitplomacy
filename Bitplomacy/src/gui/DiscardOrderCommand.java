package gui;

public class DiscardOrderCommand extends Commands {

	public DiscardOrderCommand(float x, float y, int color) {
		super(x, y, color);
	}

	@Override
	public void execute() {
		Canvas.getC().discardOrder();
	}

}
