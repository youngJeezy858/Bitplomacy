package buttons;

import canvases.GameCanvas;

/**
 * The Class SaveGameButton.
 */
public class SaveGameButton extends Button {

	/**
	 * Instantiates a new save game button.
	 *
	 * @param x the x coordinate for the button's location on the screen
	 * @param y the y coordinate for the button's location on the screen
	 * @param path the file path of the Image 
	 */
	public SaveGameButton(int x, int y, String path) {
		super(x, y, path);
	}

	/* (non-Javadoc)
	 * @see buttons.Button#update()
	 */
	@Override
	public void update() {
		GameCanvas.getC().save();	
		GameCanvas.getC().setState(GameCanvas.NORM);
	}

}
