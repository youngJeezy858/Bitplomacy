package buttons;

import canvases.GameCanvas;

public class ExitGameButton extends Button {

	public ExitGameButton(int x, int y, String s) {
		super(x, y, s);
	}

	@Override
	public void update() {
		GameCanvas.getC().setState(GameCanvas.RETURN_TO_START);
	}

}
