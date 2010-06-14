package platform.buttons;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import platform.gui.Button;
import platform.gui.ButtonState;
import platform.gui.PlatformIcon;
import platform.gui.Screen;
import platform.thread.CurveMotionHandler;
import platform.thread.FlipEffectHandler;
import platform.util.UpdateListener;
import platform.util.WidgetUpdate;
import sdljava.SDLException;
import sdljava.video.SDLRect;
import sdljavax.guichan.GUIException;
import sdljavax.guichan.gfx.Image;

public class ClickedDemoPlayButton extends ButtonState{

	public ClickedDemoPlayButton(String imageStr, UpdateListener listener, Button parent)throws GUIException {
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
