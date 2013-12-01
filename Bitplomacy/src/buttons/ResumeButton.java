package buttons;

import canvases.GameCanvas;

public class ResumeButton extends Button {

	public ResumeButton(int x, int y, String s) {
		super(x, y, s);
	}

	@Override
	public void update() {
		GameCanvas.getC().setState(GameCanvas.NORM);
	}

}
