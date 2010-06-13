package platform.buttons;

import platform.gui.Button;
import platform.gui.ButtonState;
import platform.gui.Screen;
import platform.thread.FlipEffectHandler;
import platform.util.UpdateListener;
import platform.util.WidgetUpdate;
import sdljava.SDLException;
import sdljava.video.SDLRect;
import sdljavax.guichan.GUIException;

public class ClickedPlayButton extends ButtonState{

	public ClickedPlayButton(String imageStr, UpdateListener listener, Button parent)throws GUIException {
		super(imageStr, listener, parent);
	
	}
	
	public void mouseIn() {
		// TODO Auto-generated method stub
		
	}

	public void mousePress() {
	// TODO Auto-generated method stub
		
	}

	public void mouseRelease() {
		
		parentButton.setCurrentState(parentButton.getDefaultButton());
		try {
			updateListener.putRegionToUpdate(new WidgetUpdate(parentButton,new SDLRect(parentButton.getX(),parentButton.getY(),buttonImage.getWidth(), buttonImage.getHeight())));
			new FlipEffectHandler(Screen.getScreen().getForeground());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SDLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	
}
