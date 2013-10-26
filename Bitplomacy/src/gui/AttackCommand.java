package gui;

public class AttackCommand extends Commands{

	public AttackCommand(float x, float y, int color) {
		super(x, y, color);
	}

	@Override
	public void execute() {
		Canvas.getC().attack();
	}

}
