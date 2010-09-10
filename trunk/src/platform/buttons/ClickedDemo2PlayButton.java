package platform.buttons;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import platform.gui.Button;
import platform.gui.ButtonState;
import platform.gui.PlatformIcon;
import platform.gui.Screen;
import platform.thread.CurveMotionHandler;
import platform.util.UpdateListener;
import platform.util.WidgetUpdate;
import sdljava.SDLException;
import sdljava.video.SDLRect;
import sdljavax.guichan.GUIException;
import sdljavax.guichan.gfx.Image;

public class ClickedDemo2PlayButton extends ButtonState {
	
	List<PlatformIcon> iconList = new ArrayList<PlatformIcon>();
	
	public ClickedDemo2PlayButton(String imageStr, UpdateListener listener, Button parent) throws GUIException {
		super (imageStr, listener, parent);
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
			updateListener.putRegionToUpdate(new WidgetUpdate(parentButton, new SDLRect(parentButton.getX(),parentButton.getY(),
																						buttonImage.getWidth(), buttonImage.getHeight())));
					
			PlatformIcon icon1 = new PlatformIcon(new Image(new String("resource" + File.separator 
																		+ "images" + File.separator + "gmail.png")));
			PlatformIcon icon2 = new PlatformIcon(new Image(new String("resource" + File.separator 
																		+ "images" + File.separator + "music.png")));	
			PlatformIcon icon3 = new PlatformIcon(new Image(new String("resource" + File.separator 
																		+ "images" + File.separator + "youtube.png")));
			PlatformIcon icon4 = new PlatformIcon(new Image(new String("resource" + File.separator 
																		+ "images" + File.separator + "camera.png")));
			
			iconList.add(icon1);
			iconList.add(icon2);
			iconList.add(icon3); 
			iconList.add(icon4);
			
			new CurveMotionHandler(Screen.getScreen().getForeground(), iconList);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SDLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GUIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}
