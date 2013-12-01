package buttons;

import canvases.GameCanvas;

public class PauseButton extends Button {

	public PauseButton(int x, int y, String s) {
		super(x, y, s);
	}

	@Override
	public void update() {
		GameCanvas.getC().setState(GameCanvas.PAUSED);
	}

}
