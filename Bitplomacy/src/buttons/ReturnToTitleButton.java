package buttons;

import canvases.WinCanvas;

/**
 * The Class ReturnToTitleButton.
 */
public class ReturnToTitleButton extends Button{

	/**
	 * Instantiates a new return to title button.
	 *
	 * @param x the x coordinate for the button's location on the screen
	 * @param y the y coordinate for the button's location on the screen
	 * @param path the file path of the Image 
	 */
	public ReturnToTitleButton(int x, int y, String path) {
		super(x, y, path);
	}

	/* (non-Javadoc)
	 * @see buttons.Button#update()
	 */
	@Override
	public void update() {
		WinCanvas.getWC().setState(WinCanvas.RETURN_TO_START);
	}

}
