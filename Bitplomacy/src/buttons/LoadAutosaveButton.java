package buttons;

import java.io.FileNotFoundException;

import canvases.GameCanvas;
import canvases.TitleCanvas;

public class LoadAutosaveButton extends Button {

	public LoadAutosaveButton(int x, int y, String s) {
		super(x, y, s);
	}

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
