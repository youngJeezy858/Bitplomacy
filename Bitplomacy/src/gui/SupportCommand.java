package gui;

public class SupportCommand extends Commands{

	public SupportCommand(float x, float y, int color) {
		super(x, y, color);
	}

	@Override
	public void execute() {
		System.out.println("Support command wus really good");
	}

}
