package gui;

public class ConvoyCommand extends Commands{

	public ConvoyCommand(float x, float y, int color) {
		super(x, y, color);
	}

	@Override
	public void execute() {
		System.out.println("Convoy command wus really goodie");
	}

}
