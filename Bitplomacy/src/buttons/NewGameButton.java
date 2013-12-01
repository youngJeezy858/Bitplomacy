package buttons;

import canvases.TitleCanvas;

public class NewGameButton extends Button {

	public NewGameButton(int x, int y, String s) {
		super(x, y, s);
	}

	@Override
	public void update() {
		TitleCanvas.getTC().setState(TitleCanvas.START_NEWGAME);
	}

}
