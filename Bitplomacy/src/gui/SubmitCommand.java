package gui;

public class SubmitCommand extends Commands{

	public SubmitCommand(float x, float y, int color) {
		super(x, y, color);
	}

	@Override
	public void execute() {
		Canvas.getC().submit();		
	}

}
