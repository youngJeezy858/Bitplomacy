package buttons;

import canvases.GameCanvas;

public class SaveAndExitGameButton extends Button {

	public SaveAndExitGameButton(int x, int y, String s) {
		super(x, y, s);
	}

	@Override
	public void update() {
		GameCanvas.getC().save();
		GameCanvas.getC().setState(GameCanvas.RETURN_TO_START);
	}

}
