package platform.gui;

import platform.util.UpdateListener;
import platform.util.WidgetUpdate;
import sdljava.video.SDLRect;
import sdljavax.guichan.GUIException;
import sdljavax.guichan.gfx.Image;

public abstract class ButtonState {

	protected Image buttonImage;
	protected UpdateListener updateListener;
	protected Button parentButton;
	
	public ButtonState(String imageStr, UpdateListener listener, Button parent) throws GUIException {
		buttonImage = new Image(imageStr);
		updateListener = listener;
		parentButton = parent;
	}
	
	public void delete() throws GUIException {
		buttonImage.delete();
	}
		
	public int getHeight() {
		return buttonImage.getHeight();
	}
	
	public Image getImage() {
		return buttonImage;
	}
	
	public int getWidth() {
		return buttonImage.getWidth();
	}
	
	abstract public void mouseIn();
	
	public void mouseOut() {
		parentButton.setMouseEnabled(false);
		parentButton.setCurrentState(parentButton.getDefaultButton());
		try {
			updateListener.putRegionToUpdate(new WidgetUpdate(parentButton,
															  new SDLRect(parentButton.getX(),parentButton.getY(), buttonImage.getWidth(), buttonImage.getHeight())));
			parentButton.lostFocus();
		} catch (GUIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	abstract public void mousePress();
	
	abstract public void mouseRelease();	
}
