package gui;

public class BuildArmyCommand extends Commands {

	public BuildArmyCommand(float x, float y, int color) {
		super(x, y, color);
	}

	@Override
	public void execute() {
		Canvas.getC().setCommand("build army");
	}

}
