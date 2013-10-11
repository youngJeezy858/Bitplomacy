package gui;

public class DefendCommand extends Commands{

	public DefendCommand(float x, float y, int color) {
		super(x, y, color);
	}

	@Override
	public void execute() {
		System.out.println("Defend command wus goodie");
	}

}
