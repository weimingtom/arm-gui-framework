package platform.gui;

import java.io.File;

import platform.font.CalibriFont;
import sdljava.SDLException;
import sdljava.video.SDLColor;
import sdljavax.guichan.GUIException;
import sdljavax.guichan.evt.Key;
import sdljavax.guichan.evt.KeyListener;
import sdljavax.guichan.evt.MouseListener;
import sdljavax.guichan.font.Font;
import sdljavax.guichan.gfx.Graphics;
import sdljavax.guichan.gfx.Image;
import sdljavax.guichan.widgets.Widget;

public class TextField extends Widget implements MouseListener, KeyListener{

	protected Image textField;
	protected String displayedText;
	protected Font textFont;
	
	public TextField() throws GUIException, SDLException{
		
		this("");
				
		
	}
	
	public TextField(String text) throws GUIException, SDLException{
		
		displayedText = text;
		textField = new Image(new String("resource" + File.separator + "images" + File.separator + "google_search.png"));
		textFont = new CalibriFont((int) (textField.getHeight() * 0.45), new SDLColor(255,255,255,0));
		setWidth(textField.getWidth());
		setHeight(textField.getHeight());
	}
	
	public void draw(Graphics arg0) throws GUIException {
		
		drawBorder(arg0);
		textFont.drawString(arg0, displayedText, getX() + (int)(getWidth() * 0.2 ), getY() + (int)(getHeight() * 0.3) );
		
	}

	
	public void drawBorder(Graphics arg0) throws GUIException {
	
		arg0.drawImage(textField, getX(), getY());
	}

	
	public String getDisplayedText() {
		return displayedText;
	}

	public void setDisplayedText(String displayedText) {
		this.displayedText = displayedText;
	}

	public void mouseClick(int arg0, int arg1, int arg2, int arg3)
			throws GUIException {
		// TODO Auto-generated method stub
		
	}

	public void mouseIn() throws GUIException {
		// TODO Auto-generated method stub
		
	}

	public void mouseMotion(int arg0, int arg1) throws GUIException {
		// TODO Auto-generated method stub
		
	}

	public void mouseOut() {
		// TODO Auto-generated method stub
		
	}

	public void mousePress(int arg0, int arg1, int arg2) throws GUIException {
		// TODO Auto-generated method stub
		
	}

	public void mouseRelease(int arg0, int arg1, int arg2) throws GUIException {
		// TODO Auto-generated method stub
		
	}

	public void mouseWheelDown(int arg0, int arg1) throws GUIException {
		// TODO Auto-generated method stub
		
	}

	public void mouseWheelUp(int arg0, int arg1) throws GUIException {
		// TODO Auto-generated method stub
		
	}

	public void keyPress(Key arg0) throws GUIException {
		// TODO Auto-generated method stub
		
	}

	public void keyRelease(Key arg0) throws GUIException {
		// TODO Auto-generated method stub
		
	}
	

}
