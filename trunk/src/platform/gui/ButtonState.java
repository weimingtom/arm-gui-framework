package platform.gui;

import platform.util.UpdateListener;
import sdljavax.guichan.GUIException;
import sdljavax.guichan.gfx.Image;

public abstract class ButtonState {

	protected Image buttonImage;
	protected UpdateListener updateListener;
	
	public ButtonState(String imageStr, UpdateListener listener) throws GUIException{
		buttonImage = new Image(imageStr);
		updateListener = listener;
	}
	
	abstract public void mouseIn();
	
	abstract public void mouseOut();
	
	abstract public void mousePress();
	
	abstract public void mouseRelease();
	
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
