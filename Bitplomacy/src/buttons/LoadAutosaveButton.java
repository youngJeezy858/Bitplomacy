package buttons;

import java.io.FileNotFoundException;

import canvases.GameCanvas;
import canvases.TitleCanvas;

/**
 * The Class LoadAutosaveButton.
 */
public class LoadAutosaveButton extends Button {

	/**
	 * Instantiates a new load autosave button.
	 *
	 * @param x the x coordinate for the button's location on the screen
	 * @param y the y coordinate for the button's location on the screen
	 * @param path the file path of the Image 
	 */
	public LoadAutosaveButton(int x, int y, String path) {
		super(x, y, path);
	}

	/* (non-Javadoc)
	 * @see buttons.Button#update()
	 */
	@Override
	public void update() {
		try {
			GameCanvas.getC().load();
			TitleCanvas.getTC().setState(TitleCanvas.START_GAME);
		} catch (FileNotFoundException e) {
			TitleCanvas.getTC().output("No Save File Found!");
			e.printStackTrace();
		}
	}

}
