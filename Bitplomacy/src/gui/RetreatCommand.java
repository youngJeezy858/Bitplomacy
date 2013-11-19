package gui;

public class RetreatCommand extends Commands{

	public RetreatCommand(float x, float y, int color) {
		super(x, y, color);
	}

	@Override
	public void execute() {
		Canvas.getC().setCommand("retreat");
	}

}
