package platform.buttons;

import platform.gui.Button;
import platform.gui.ButtonState;
import platform.util.UpdateListener;
import platform.util.WidgetUpdate;
import sdljava.video.SDLRect;
import sdljavax.guichan.GUIException;

public class DefaultPlayButton extends ButtonState {

	public DefaultPlayButton(String imageStr, UpdateListener listener, Button parent) throws GUIException {
		super (imageStr, listener, parent);
	}

	public void mouseIn() {
		parentButton.setMouseEnabled(true);
		parentButton.setCurrentState(parentButton.getSelectedButton());

		try {
			parentButton.requestFocus();
			updateListener.putRegionToUpdate(new WidgetUpdate(parentButton, new SDLRect(parentButton.getX(), parentButton.getY(),
																						buttonImage.getWidth(), buttonImage.getHeight() )));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GUIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void mousePress() {
		parentButton.setCurrentState(parentButton.getClickedButton());
		
		try {
			updateListener.putRegionToUpdate(new WidgetUpdate(parentButton,
															  new SDLRect(parentButton.getX(), parentButton.getY(), buttonImage.getWidth(), buttonImage.getHeight())));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void mouseRelease() {
		// TODO Auto-generated method stub
	}
}
