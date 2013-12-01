package buttons;

import canvases.WinCanvas;

public class ReturnToTitleButton extends Button{

	public ReturnToTitleButton(int x, int y, String s) {
		super(x, y, s);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update() {
		WinCanvas.getWC().setState(WinCanvas.RETURN_TO_START);
	}

}
