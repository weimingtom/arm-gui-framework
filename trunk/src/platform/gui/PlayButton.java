package platform.gui;

import java.io.File;

import sdljavax.guichan.GUIException;
import sdljavax.guichan.evt.MouseListener;
import sdljavax.guichan.gfx.Graphics;
import sdljavax.guichan.gfx.Image;
import sdljavax.guichan.widgets.Widget;

public class PlayButton extends Widget implements MouseListener{

	Image defaultButton;
	Image clickedButton;
	Image selectedButton;
	String resourcePath;
	ButtonStates buttonState;
	
	public PlayButton() throws GUIException{
		super();
		resourcePath = new String("resource" + File.separator + "PNG" + File.separator + "music_button");
		defaultButton = new Image(resourcePath + "_default.png");
		buttonState = ButtonStates.DEFAULT;
		
		setWidth(defaultButton.getWidth());
		setHeight(defaultButton.getHeight());
		
		addMouseListener(this);
	}
	
	@Override
	public void draw(Graphics graphics) throws GUIException {
		
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
	public void drawBorder(Graphics arg0) throws GUIException {
		// TODO Auto-generated method stub
		
	}

	public void mouseClick(int arg0, int arg1, int arg2, int arg3)
			throws GUIException {
		System.out.println("Button clicked");
		
	}

	public void mouseIn() throws GUIException {
	
		m_bHasMouse =  true;
		requestFocus();
		buttonState = ButtonStates.SELECTED;
		selectedButton = new Image(resourcePath + "_selected.png");
	}

	public void mouseMotion(int arg0, int arg1) throws GUIException {
		// TODO Auto-generated method stub
		
	}

	public void mouseOut() {
		m_bHasMouse =  false;
		buttonState=ButtonStates.DEFAULT;
		try {
			lostFocus();
		} catch (GUIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void mousePress(int arg0, int arg1, int arg2) throws GUIException {
		buttonState=ButtonStates.PRESSED;
		clickedButton = new Image(resourcePath + "_pressed.png");
		
		
	}

	public void mouseRelease(int arg0, int arg1, int arg2) throws GUIException {
		System.out.println("Mouse release");
		buttonState=ButtonStates.DEFAULT;
		
	}

	public void mouseWheelDown(int arg0, int arg1) throws GUIException {
		// TODO Auto-generated method stub
		
	}

	public void mouseWheelUp(int arg0, int arg1) throws GUIException {
		// TODO Auto-generated method stub
		
	}
	
	

	@Override
	public void delete() throws GUIException {
		System.out.println("deleting playButton");
		defaultButton.delete();
		clickedButton.delete();
		selectedButton.delete();
		super.delete();
	}



	enum ButtonStates { DEFAULT, PRESSED, SELECTED };
}