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
import sdljavax.guichan.gfx.Color;
import sdljavax.guichan.gfx.Graphics;
import sdljavax.guichan.gfx.Image;
import sdljavax.guichan.widgets.Widget;

public class TextField extends Widget implements  KeyListener{

	protected Image textField;
	protected String displayedText;
	protected Font textFont;
	protected int cursorPosition;
	protected int onScreenCursorPosition;
	protected SDLColor cursorColor;
	
	public TextField() throws GUIException, SDLException{
		
		this("");
				
		
	}
	
	public TextField(String text) throws GUIException, SDLException{
		
		displayedText = text;
		textField = new Image(new String("resource" + File.separator + "images" + File.separator + "google_search.png"));
		cursorColor = new SDLColor(255,255,255,0);
		textFont = new CalibriFont((int) (textField.getHeight() * 0.45), new SDLColor(255,255,255,0));
		setWidth(textField.getWidth());
		setHeight(textField.getHeight());
		
		cursorPosition = text.length();
		onScreenCursorPosition= getXTextPosition() + textFont.getWidth(text);
		
		
		addKeyListener(this);
	}
	
	public void draw(Graphics graphics) throws GUIException {
		
		drawBorder(graphics);
		drawText(graphics);
		drawCursor(graphics);
		
		
	}

	
	public void drawText(Graphics graphics) throws GUIException{
		textFont.drawString(graphics, displayedText, getXTextPosition(), getYTextPosition() );
	}
	
	public void drawBorder(Graphics graphics) throws GUIException {
	
		graphics.drawImage(textField, getX(), getY());
	}

	
	public void drawCursor(Graphics graphics) throws GUIException{
		
		graphics.setColor(new Color(cursorColor.getRed(), cursorColor.getGreen(), cursorColor.getBlue(), 255));
		graphics.drawLine(onScreenCursorPosition, getYTextPosition(), onScreenCursorPosition, getYTextPosition() +  textFont.getHeight());
		
	}
	
	public String getDisplayedText() {
		return displayedText;
	}

	public void setDisplayedText(String displayedText) {
		this.displayedText = displayedText;
	}
	
	public int getXTextPosition(){
		
		return getX() + (int)(getWidth() * 0.2 );
	}
	
	public int getYTextPosition(){
		
		return getY() + (int)(getHeight() * 0.3);
	}

	
	public void keyPress(Key key) throws GUIException {
		//TODO add here notification of a change being made

		if (key.getValue() == Key.LEFT && cursorPosition > 0) {
			cursorPosition--;
			onScreenCursorPosition-=17;
			
		} else if (key.getValue() == Key.RIGHT && cursorPosition < displayedText.length()) {
			
			
			cursorPosition++;
			onScreenCursorPosition+=17;
			
		} else if (key.getValue() == Key.DELETE && cursorPosition < displayedText.length()) {
			
			displayedText = displayedText.substring(0, cursorPosition) + displayedText.substring(cursorPosition + 1);
			
			
		} else if (key.getValue() == Key.BACKSPACE && cursorPosition > 0) {
			displayedText = displayedText.substring(0, cursorPosition - 1) + displayedText.substring(cursorPosition);
			
			cursorPosition--;
			onScreenCursorPosition-=17;
			
		} else if (key.getValue() == Key.ENTER) {
			generateAction();
			
		} else if (key.getValue() == Key.HOME) {
			cursorPosition = 0;
			onScreenCursorPosition=getXTextPosition();
			
		} else if (key.getValue() == Key.END) {
			cursorPosition = displayedText.length();
			onScreenCursorPosition = getXTextPosition() + textFont.getWidth(displayedText);
			
		} else if (key.isCharacter()) {
			displayedText = displayedText.substring(0, cursorPosition) + (char) key.getValue() + displayedText.substring(cursorPosition);
			cursorPosition++;
			onScreenCursorPosition+=17;
		}
				
	}

	public void keyRelease(Key arg0) throws GUIException {
		// TODO Auto-generated method stub
		
	}
	

}
