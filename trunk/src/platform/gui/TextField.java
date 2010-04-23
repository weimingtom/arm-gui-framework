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
	protected int prevOnScreenCursorPosition;
	protected int onScreenCursorPosition;
	protected SDLColor cursorColor;
	protected ElementChanged elementChanged;
	
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
		onScreenCursorPosition= getXTextPosition() + textFont.getWidth(displayedText) + 10; //TODO fix that shift somehow
		prevOnScreenCursorPosition=0;
		elementChanged = ElementChanged.ALL;
		
		addKeyListener(this);
	}
	
	public void draw(Graphics graphics) throws GUIException {
		
		switch(elementChanged){
			case ALL:
				drawBorder(graphics);
				drawText(graphics);
				drawCursor(graphics);
			break;
			
			case TEXT:
				
				drawTextLine(graphics);
				drawText(graphics);
				drawCursor(graphics);
			break;
			
			case CURSOR:
				eraseCursor(graphics);
				drawCursor(graphics);
			break;
			
			case NONE:	
			break;
				
		}
		
		elementChanged = ElementChanged.NONE;
	}
	
	public void eraseCursor(Graphics graphics) throws GUIException{
		
		if(prevOnScreenCursorPosition!=0){
			graphics.setColor(new Color(0, 0, 0, 255));
			graphics.drawLine(prevOnScreenCursorPosition, getYTextPosition(), prevOnScreenCursorPosition, getYTextPosition() +  textFont.getHeight());
			}
		prevOnScreenCursorPosition=onScreenCursorPosition;
	}
	
	/*public void eraseText(Graphics graphics) throws GUIException{
		
		textFont.drawString(graphics, "               ", getXTextPosition(), getYTextPosition() );
		
	}*/
	
	public void drawText(Graphics graphics) throws GUIException{
	
	
		if(displayedText.length() != 0){	
		textFont.drawString(graphics, displayedText, getXTextPosition(), getYTextPosition() );
		}
	}
	
	public void drawTextLine(Graphics graphics) throws GUIException{
		//TODO change an argument 150 for visible text length
		graphics.drawImage(textField, (int)(getWidth() * 0.2) , (int)(getHeight() * 0.3), getXTextPosition(), getYTextPosition(), 150, textFont.getHeight() + 1 ); 
		
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
		elementChanged = ElementChanged.TEXT;
	}
	
	public int getXTextPosition(){
		return getX() + (int)(getWidth() * 0.2 );
	}
	
	public int getYTextPosition(){
		return getY() + (int)(getHeight() * 0.3);
	}

	public void keyPress(Key key) throws GUIException{
		//TODO add here notification of a change being made
		try {
			if (key.getValue() == Key.LEFT && cursorPosition > 0) {
				
				onScreenCursorPosition-=((CalibriFont)textFont).getCharacterWidth(displayedText.charAt(cursorPosition-1));			
				--cursorPosition;
				elementChanged = ElementChanged.CURSOR;
				
			}
			else if (key.getValue() == Key.RIGHT && cursorPosition < displayedText.length()) {
				
				onScreenCursorPosition+=((CalibriFont)textFont).getCharacterWidth(displayedText.charAt(cursorPosition));
				++cursorPosition;
				elementChanged = ElementChanged.CURSOR;
				
			}
			else if (key.getValue() == Key.DELETE && cursorPosition < displayedText.length()) {
				displayedText = displayedText.substring(0, cursorPosition) + displayedText.substring(cursorPosition + 1);
				elementChanged = ElementChanged.TEXT;
			}
			else if (key.getValue() == Key.BACKSPACE && cursorPosition > 0) {
				
				onScreenCursorPosition-=((CalibriFont)textFont).getCharacterWidth(displayedText.charAt(cursorPosition-1));
				displayedText = displayedText.substring(0, cursorPosition - 1) + displayedText.substring(cursorPosition);
				--cursorPosition;
				elementChanged = ElementChanged.TEXT;
			} 
			else if (key.getValue() == Key.ENTER) {
				generateAction();
				
			} 
			else if (key.getValue() == Key.HOME) {
				cursorPosition = 0;
				onScreenCursorPosition=getXTextPosition() + 15;
				elementChanged = ElementChanged.CURSOR; //TODO distinguish here if text is longer than window
			} 
			else if (key.getValue() == Key.END) {
				cursorPosition = displayedText.length();
				onScreenCursorPosition = getXTextPosition() + textFont.getWidth(displayedText);
				//TOD) distinguish here if text is longer than window
				elementChanged = ElementChanged.CURSOR;
			} 
			else if (key.isCharacter()) {
				displayedText = displayedText.substring(0, cursorPosition) + (char) key.getValue() + displayedText.substring(cursorPosition);
				++cursorPosition;
				onScreenCursorPosition+=((CalibriFont)textFont).getCharacterWidth((char) key.getValue());
				elementChanged = ElementChanged.TEXT;
			}
		}
		catch (SDLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void keyRelease(Key arg0) throws GUIException {
		// TODO Auto-generated method stub
	}
	
	public void delete() throws GUIException {
		textField.delete();
		textFont.delete();
		super.delete();
	}
	enum ElementChanged { TEXT, CURSOR , NONE, ALL };
	
}
