package platform.gui;

import java.io.File;

import platform.gfx.UnifiedGraphics;
import platform.util.WidgetUpdate;
import sdljava.video.SDLRect;
import sdljavax.guichan.GUIException;
import sdljavax.guichan.evt.MouseListener;
import sdljavax.guichan.gfx.Image;

public class PlayButton extends PlatformWidget implements MouseListener{

	Image defaultButton;
	Image clickedButton;
	Image selectedButton;
	String resourcePath;
	ButtonStates buttonState;
	
	public PlayButton() throws GUIException{
		super();
		resourcePath = new String("resource" + File.separator + "PNG" + File.separator + "music_button");
		defaultButton = new Image(resourcePath + "_default.png");
		clickedButton = new Image(resourcePath + "_pressed.png");
		selectedButton = new Image(resourcePath + "_selected.png");
		buttonState = ButtonStates.DEFAULT;
		
		setWidth(defaultButton.getWidth());
		setHeight(defaultButton.getHeight());
		
		addMouseListener(this);
	}
	
	@Override
	public void draw(UnifiedGraphics graphics) throws GUIException {
		
		switch(buttonState){
			case DEFAULT:
				graphics.drawImage(defaultButton, getX(), getY());
			break;
			
			case PRESSED:
				graphics.drawImage(clickedButton, getX(), getY());
			break;
			
			case SELECTED:
				graphics.drawImage(selectedButton, getX(), getY());
			break;
		}
		
	}

	@Override
	public void drawBorder(UnifiedGraphics graphics) throws GUIException {
		// TODO Auto-generated method stub
		
	}

	public void mouseClick(int x, int y, int button, int count)
			throws GUIException {
		
		//buttonState=ButtonStates.DEFAULT;
	}

	public void mouseIn() throws GUIException {
	
		m_bHasMouse =  true;
		requestFocus();
		buttonState = ButtonStates.SELECTED;
		try {
			updateListener.putRegionToUpdate(new WidgetUpdate(this,new SDLRect(getX(),getY(),selectedButton.getWidth(), selectedButton.getHeight())));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void mouseMotion(int x, int y) throws GUIException {
		// TODO Auto-generated method stub
		
	}


	public void mouseOut() {
		m_bHasMouse =  false;
		buttonState=ButtonStates.DEFAULT;
		try {
			updateListener.putRegionToUpdate(new WidgetUpdate(this,new SDLRect(getX(),getY(),defaultButton.getWidth(), defaultButton.getHeight())));
			lostFocus();
		} catch (GUIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void mousePress(int x, int y, int button) throws GUIException {
		buttonState=ButtonStates.PRESSED;
		try {
			updateListener.putRegionToUpdate(new WidgetUpdate(this,new SDLRect(getX(),getY(),clickedButton.getWidth(), clickedButton.getHeight())));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void mouseRelease(int x, int y, int button) throws GUIException {
	
		buttonState=ButtonStates.DEFAULT;
		try {
			updateListener.putRegionToUpdate(new WidgetUpdate(this,new SDLRect(getX(),getY(),defaultButton.getWidth(), defaultButton.getHeight())));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void mouseWheelDown(int x, int y) throws GUIException {
		// TODO Auto-generated method stub
		
	}

	public void mouseWheelUp(int x, int y) throws GUIException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void delete() throws GUIException {
		defaultButton.delete();
		clickedButton.delete();
		selectedButton.delete();
		super.delete();
	}
	
	enum ButtonStates { DEFAULT, PRESSED, SELECTED };
}
