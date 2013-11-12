package gui;

public class BuildNavyCommand extends Commands {

	public BuildNavyCommand(float x, float y, int color) {
		super(x, y, color);
	}

	@Override
	public void execute() {
		Canvas.getC().setCommand("build navy");
	}

}
