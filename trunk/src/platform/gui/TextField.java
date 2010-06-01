package platform.gui;

import java.io.File;

import platform.font.CalibriFont;
import platform.gfx.UnifiedGraphics;
import platform.util.WidgetUpdate;
import sdljava.SDLException;
import sdljava.video.SDLColor;
import sdljava.video.SDLRect;
import sdljavax.guichan.GUIException;
import sdljavax.guichan.evt.Key;
import sdljavax.guichan.evt.KeyListener;
import sdljavax.guichan.font.Font;
import sdljavax.guichan.gfx.Color;
import sdljavax.guichan.gfx.Graphics;
import sdljavax.guichan.gfx.Image;

public class TextField extends PlatformWidget implements  KeyListener{

	protected Image textField;
	protected String displayedText;
	protected Font textFont;
	protected int cursorPosition;
	protected int prevOnScreenCursorPosition;
	protected int onScreenCursorPosition;
	protected int stepsFromBorder;
	protected SDLColor cursorColor;
	protected ElementChanged elementChanged;
	
	public TextField() throws GUIException, SDLException{				
		this("");
	}
	
	public TextField(String text) throws GUIException, SDLException{
		displayedText = text;
		textField = new Image("resource" + File.separator + "images" + File.separator + "google_search.png");
		cursorColor = new SDLColor(255,255,255,0);
		textFont = new CalibriFont((int) (textField.getHeight() * 0.45), new SDLColor(255,255,255,0), false);
		setWidth(textField.getWidth());
		setHeight(textField.getHeight());
		

		cursorPosition = text.length();
		onScreenCursorPosition= getXTextPosition() + textFont.getWidth(displayedText) ; 
		prevOnScreenCursorPosition=onScreenCursorPosition;
		stepsFromBorder=10;
		elementChanged = ElementChanged.ALL;
		
		addKeyListener(this);
	}
	@Override
	public void draw(UnifiedGraphics graphics) throws GUIException {
		
		switch(elementChanged){
			case ALL:
				drawBorder(graphics);
				drawText(graphics);
				drawCursor(graphics);
			break;
			
			case TEXT:
				eraseCursor(graphics);
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
			
	public void drawText(Graphics graphics) throws GUIException{
	
		//System.out.println(cursorPosition);
		if(displayedText.length() != 0){
		
			if(displayedText.length() < 11){
				
				textFont.drawString(graphics, displayedText, getXTextPosition(), getYTextPosition() );
			}
			else if(displayedText.length() - cursorPosition > 10){
				textFont.drawString(graphics, displayedText.substring(cursorPosition, cursorPosition+10), getXTextPosition(), getYTextPosition() );
				
			}
			else{
				
				textFont.drawString(graphics, displayedText.substring(cursorPosition-10, cursorPosition), getXTextPosition(), getYTextPosition() );

			}
		}
	}
	
	public void drawTextLine(Graphics graphics) throws GUIException{
		//TODO change an argument 150 for visible text length
		graphics.drawImage(textField, (int)(getWidth() * 0.2) , (int)(getHeight() * 0.3), getXTextPosition(), getYTextPosition(), 160, textFont.getHeight() + 1 ); 
		
	}
	
	@Override
	public void drawBorder(UnifiedGraphics graphics) throws GUIException {
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
		
		try {
			if (key.getValue() == Key.LEFT && cursorPosition > 0) {
				if(displayedText.length() - cursorPosition > 9){
				elementChanged = ElementChanged.TEXT;
				}
				else{
				onScreenCursorPosition-=((CalibriFont)textFont).getCharacterWidth(displayedText.charAt(cursorPosition-1));
				
					if(stepsFromBorder<10){
						stepsFromBorder++;
					}
					elementChanged = ElementChanged.TEXT;
				//elementChanged = ElementChanged.CURSOR;
				}
				--cursorPosition;
			}
			else if (key.getValue() == Key.RIGHT && cursorPosition < displayedText.length()) {
				
			
				if(stepsFromBorder > 0){
					onScreenCursorPosition+=((CalibriFont)textFont).getCharacterWidth(displayedText.charAt(cursorPosition));
					stepsFromBorder--;
					//elementChanged = ElementChanged.CURSOR;
					elementChanged = ElementChanged.TEXT;
				}
				else {
					elementChanged = ElementChanged.TEXT;
				}
				++cursorPosition;
				
			}
			else if (key.getValue() == Key.DELETE && cursorPosition < displayedText.length()) {
				
				displayedText = displayedText.substring(0, cursorPosition) + displayedText.substring(cursorPosition + 1);
								
				/*if(displayedText.length() > 9 && cursorPosition != 0){
					onScreenCursorPosition+=((CalibriFont)textFont).getCharacterWidth(displayedText.charAt(cursorPosition-1));	
					stepsFromBorder--;
				}*/
				elementChanged = ElementChanged.TEXT;
			}
			else if (key.getValue() == Key.BACKSPACE && cursorPosition > 0) {
				
				if(cursorPosition<11 && cursorPosition != 0){
				onScreenCursorPosition-=((CalibriFont)textFont).getCharacterWidth(displayedText.charAt(cursorPosition-1));
				stepsFromBorder++;
				}
				displayedText = displayedText.substring(0, cursorPosition - 1) + displayedText.substring(cursorPosition);
				--cursorPosition;
				elementChanged = ElementChanged.TEXT;
			} 
			else if (key.getValue() == Key.ENTER) {
				generateAction();
				
			} 
			else if (key.getValue() == Key.HOME) {
				cursorPosition = 0;
				stepsFromBorder=10;
				onScreenCursorPosition=getXTextPosition();
				elementChanged = ElementChanged.TEXT;
				//elementChanged = ElementChanged.CURSOR; 
			} 
			else if (key.getValue() == Key.END) {
				cursorPosition = displayedText.length();
				stepsFromBorder=(displayedText.length()>10) ? 0 : 10-displayedText.length() ;
				if(cursorPosition<11){
				onScreenCursorPosition = getXTextPosition() + textFont.getWidth(displayedText);
				}
				else{
					onScreenCursorPosition = getXTextPosition() + textFont.getWidth(displayedText.substring(cursorPosition-10, cursorPosition));
					
				}
				elementChanged = ElementChanged.TEXT;
				//elementChanged = ElementChanged.CURSOR;
			} 
			else if (key.isCharacter()) {
				displayedText = displayedText.substring(0, cursorPosition) + (char) key.getValue() + displayedText.substring(cursorPosition);
				if(++cursorPosition<11){
				stepsFromBorder--;
				onScreenCursorPosition+=((CalibriFont)textFont).getCharacterWidth((char) key.getValue());
				}
				elementChanged = ElementChanged.TEXT;
			}
			
			if(updateListener != null){
				updateListener.putRegionToUpdate( new WidgetUpdate ( this,new SDLRect(getXTextPosition(), getYTextPosition(), 160, textFont.getHeight() + 1 ) ) );
			}
			/*if(elementChanged == ElementChanged.TEXT){
				
				if(updateListener != null){
					updateListener.putRegionToUpdate( new WidgetUpdate ( this,new SDLRect(getXTextPosition(), getYTextPosition(), 160, textFont.getHeight() + 1 ) ) );
				}
				
			}
			else{ //elementChanged == ElementChanged.CURSOR
				int smallerIndex = (prevOnScreenCursorPosition < onScreenCursorPosition) ? prevOnScreenCursorPosition : onScreenCursorPosition;
				int greaterIndex = (prevOnScreenCursorPosition > onScreenCursorPosition) ? prevOnScreenCursorPosition : onScreenCursorPosition;
					
				if(updateListener != null){
					//updateListener.putRegionToUpdate( new WidgetUpdate ( this,new SDLRect(  smallerIndex - 1 , getYTextPosition(), Math.abs(prevOnScreenCursorPosition - onScreenCursorPosition), textFont.getHeight() + 1 ) ) );
					updateListener.putRegionToUpdate( new WidgetUpdate ( this,new SDLRect(  smallerIndex - 1 , getYTextPosition(), Math.abs(prevOnScreenCursorPosition - onScreenCursorPosition), textFont.getHeight() + 1 ) ) );

				}
			}*/
		}
		catch (SDLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void keyRelease(Key key) throws GUIException {
		// TODO Auto-generated method stub
	}
	
	public void delete() throws GUIException {
		textField.delete();
		textFont.delete();
		super.delete();
	}
	enum ElementChanged { TEXT, CURSOR , NONE, ALL };
	
}
