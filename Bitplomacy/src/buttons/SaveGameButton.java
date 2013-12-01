package buttons;

import canvases.GameCanvas;

public class SaveGameButton extends Button {

	public SaveGameButton(int x, int y, String s) {
		super(x, y, s);
	}

	@Override
	public void update() {
		GameCanvas.getC().save();	
		GameCanvas.getC().setState(GameCanvas.NORM);
	}

}
