package buttons;

import canvases.GameCanvas;

/**
 * The Class ExitGameButton.
 */
public class ExitGameButton extends Button {

	/**
	 * Instantiates a new exit game button.
	 *
	 * @param x the x coordinate for the button's location on the screen
	 * @param y the y coordinate for the button's location on the screen
	 * @param path the file path of the Image 
	 */
	public ExitGameButton(int x, int y, String path) {
		super(x, y, path);
	}

	/* (non-Javadoc)
	 * @see buttons.Button#update()
	 */
	@Override
	public void update() {
		GameCanvas.getC().setState(GameCanvas.RETURN_TO_START);
	}

}
