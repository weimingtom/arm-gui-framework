package platform.buttons;

import platform.gui.Button;
import platform.gui.ButtonState;
import platform.util.UpdateListener;
import platform.util.WidgetUpdate;
import sdljava.video.SDLRect;
import sdljavax.guichan.GUIException;

public class SelectedPlayButton extends ButtonState {
	
	public SelectedPlayButton(String imageStr, UpdateListener updateListener, Button parent) throws GUIException {
		super(imageStr, updateListener, parent);
	}
		
	public void mouseIn() {
		// TODO Auto-generated method stub
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
