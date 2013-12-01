package buttons;

import canvases.GameCanvas;

/**
 * The Class PauseButton.
 */
public class PauseButton extends Button {

	/**
	 * Instantiates a new pause button.
	 *
	 * @param x the x coordinate for the button's location on the screen
	 * @param y the y coordinate for the button's location on the screen
	 * @param path the file path of the Image 
	 */
	public PauseButton(int x, int y, String path) {
		super(x, y, path);
	}

	/* (non-Javadoc)
	 * @see buttons.Button#update()
	 */
	@Override
	public void update() {
		GameCanvas.getC().setState(GameCanvas.PAUSED);
	}

}
