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
	
	public ButtonState(String imageStr, UpdateListener listener, Button parent) throws GUIException{
		buttonImage = new Image(imageStr);
		updateListener = listener;
		parentButton = parent;
	}
	
	
	abstract public void mouseIn();
	
	
	abstract public void mousePress();
	
	abstract public void mouseRelease();
	
	public void mouseOut(){
		
		
		parentButton.setMouseEnabled(false);
		parentButton.setCurrentState(parentButton.getDefaultButton());
		try {
			updateListener.putRegionToUpdate(new WidgetUpdate(parentButton,new SDLRect(parentButton.getX(),parentButton.getY(), buttonImage.getWidth(), buttonImage.getHeight())));
			parentButton.lostFocus();
		} catch (GUIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void delete() throws GUIException{
		
		buttonImage.delete();
	}
	
	public Image getImage(){
		
		return buttonImage;
	}
	
	public int getWidth(){
		return buttonImage.getWidth();
	}
	
	public int getHeight(){
		return buttonImage.getHeight();
	}
	
	
}
