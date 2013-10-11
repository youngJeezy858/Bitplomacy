package gui;

public class AttackCommand extends Commands{

	public AttackCommand(float x, float y, int color) {
		super(x, y, color);
	}

	@Override
	public void execute() {
		System.out.println("Attack command wus good");
	}

}
