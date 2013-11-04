package gui;

public class SupportCommand extends Commands{

	public SupportCommand(float x, float y, int color) {
		super(x, y, color);
	}

	@Override
	public void execute() {
		Canvas.getC().setCommand("support");
	}

}
