package buttons;

import canvases.TitleCanvas;

/**
 * The Class NewGameButton.
 */
public class NewGameButton extends Button {

	/**
	 * Instantiates a new new game button.
	 *
	 * @param x the x coordinate for the button's location on the screen
	 * @param y the y coordinate for the button's location on the screen
	 * @param path the file path of the Image 
	 */
	public NewGameButton(int x, int y, String path) {
		super(x, y, path);
	}

	/* (non-Javadoc)
	 * @see buttons.Button#update()
	 */
	@Override
	public void update() {
		TitleCanvas.getTC().setState(TitleCanvas.START_GAME);
	}

}
