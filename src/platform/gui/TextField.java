package platform.gui;
/**
*  arm-gui-framework -Java GUI based on sdljava for omap5912 board
*  Copyright (C) 2010  Bartosz Kędra (bartosz.kedra@gmail.com)
* 
*  This library is free software; you can redistribute it and/or
*  modify it under the terms of the GNU Lesser General Public
*  License as published by the Free Software Foundation; either
*  version 3.0 of the License, or (at your option) any later version.
* 
*  This library is distributed in the hope that it will be useful,
*  but WITHOUT ANY WARRANTY; without even the implied warranty of
*  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
*  Lesser General Public License for more details.
* 
*  You should have received a copy of the GNU Lesser General Public
*  License along with this library; if not, write to the Free Software
*  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
*  USA
*
*/

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

/**
 * Class representing text field - widget we can write to with keyboard
 * @author Bartosz Kędra
 * @author bartosz.kedra@gmail.com
 *
 */
public class TextField extends PlatformWidget implements KeyListener {

	enum ElementChanged { TEXT, CURSOR_ON, CURSOR_OFF , NONE, ALL }
	
	/**
	 * Class used for flickering cursors handling 
	 * @author Bartosz Kędra
	 * @author bartosz.kedra@gmail.com
	 *
	 */
	class FlickeringCursorHandler extends Thread {
		
		/**
		 * Flag indicating whether cursor is currently visible
		 */
		boolean visible = false;
		
		/**
		 * Flag indicating whether cursor must be flickering or not
		 */
		boolean running;
		
		/**
		 * Constructor
		 * @param run
		 * 		active status flag
		 */
		FlickeringCursorHandler(boolean run) {
			running = run;
			start();
		}
		
		/**
		 * Changes visibility of cursor every 1 sec if cursor must be active
		 */
		public void run() {
			while (true) {
				try {
					if (running) {
							elementChanged = (visible == true ) ? ElementChanged.CURSOR_ON : ElementChanged.CURSOR_OFF;
							updateListener.putRegionToUpdate(new WidgetUpdate(TextField.this, 
																			  new SDLRect(onScreenCursorPosition, getYTextPosition(), 1, textFont.getHeight() + 1) ));
							visible = !visible;
							Thread.sleep(1000);
					} else {
						Thread.sleep(1000);
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		/**
		 * Set running status
		 * @param run
		 * 			status
		 */
		public void setRunning(boolean run) {
			running = run;
		}
	}
	
	/**
	 * Maximum number of characters displayed
	 */
	protected final int MAX_STEPS_FROM_BORDER = 10;
	
	/**
	 * Text field background surface
	 */
	protected Image textField;
	
	/**
	 * Displayed string
	 */
	protected String displayedText;
	
	/**
	 * Font used to draw text
	 */
	protected Font textFont;
	
	/**
	 * Cursor position on the text field
	 */
	protected int cursorPosition;
	
	/**
	 * Previous on screen cursor position - in pixels
	 */
	protected int prevOnScreenCursorPosition;
	
	/**
	 * On screen cursor position - in pixels
	 */
	protected int onScreenCursorPosition;
	
	/**
	 * Number of steps form border
	 */
	protected int stepsFromBorder;
	
	/**
	 * Cursor color
	 */
	protected SDLColor cursorColor;
	
	protected ElementChanged elementChanged;
	
	/**
	 * Flickering cursor handler
	 */
	protected TextField.FlickeringCursorHandler flickeringCursorHandler;
	
	/**
	 * Constructor with no text displayed
	 * @throws GUIException
	 * @throws SDLException
	 */
	public TextField() throws GUIException, SDLException{				
		this("");
	}
	
	/**
	 * Constructor with text displayed
	 * @param text
	 * 			string to be displayed
	 * @throws GUIException
	 * @throws SDLException
	 */
	public TextField(String text) throws GUIException, SDLException {
		displayedText = text;
		textField = new Image("resource" + File.separator + "images" + File.separator + "google_search.png");
		cursorColor = new SDLColor(255,255,255,0);
		textFont = new CalibriFont((int) (textField.getHeight() * 0.45), new SDLColor(255,255,255,0), false);
		setWidth(textField.getWidth());
		setHeight(textField.getHeight());
		
		cursorPosition = text.length();
		onScreenCursorPosition= getXTextPosition() + textFont.getWidth(displayedText) ; 
		prevOnScreenCursorPosition = onScreenCursorPosition;
		stepsFromBorder= MAX_STEPS_FROM_BORDER - text.length();
		elementChanged = ElementChanged.ALL;
		
		flickeringCursorHandler = new FlickeringCursorHandler(false);
		addKeyListener(this);
	}
	
	/**
	 * Cleans any dynamically reserved memory areas in C style 
	 * @throws GUIException
	 */
	public void delete() throws GUIException {
		textField.delete();
		textFont.delete();
		super.delete();
	}
	
	/**
	 * Draws text field on the surface 
	 * @param graphics
	 * 			used for drawing label on target surface
	 * @throws GUIException
	 */
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
			
			case CURSOR_ON:
				drawCursor(graphics);
			break;
			
			case CURSOR_OFF:
				eraseCursor(graphics);
			break;
			
			case NONE:	
			break;
		}
		elementChanged = ElementChanged.ALL;
	}
	
	/**
	 * Draws border - background on the target surface
	 * @param graphics
	 * 			used for drawing background on target surface
	 * @throws GUIException
	 */
	@Override
	public void drawBorder(UnifiedGraphics graphics) throws GUIException {
		graphics.drawImage(textField, getX(), getY());
	}

	
	/**
	 * Draws cursors on position specified by on Screen cursor position
	 * @param graphics
	 * 			object capable of drawing
	 * @throws GUIException
	 */
	public void drawCursor(Graphics graphics) throws GUIException {
		graphics.setColor(new Color(cursorColor.getRed(), cursorColor.getGreen(), cursorColor.getBlue(), 255));
		graphics.drawLine(onScreenCursorPosition, getYTextPosition(), onScreenCursorPosition,
						  getYTextPosition() + textFont.getHeight());
		
	}
	
	/**
	 * Draws text or part on text field surface depending on cursor position
	 * @param graphics
	 * 			object capable of drawing
	 * @throws GUIException
	 */
	public void drawText(Graphics graphics) throws GUIException {
		//System.out.println(cursorPosition);
		if (displayedText.length() != 0) {
			if (displayedText.length() < MAX_STEPS_FROM_BORDER + 1) {
				textFont.drawString(graphics, displayedText, getXTextPosition(), getYTextPosition() );
			} else if (displayedText.length() - cursorPosition > MAX_STEPS_FROM_BORDER) {
				textFont.drawString(graphics, displayedText.substring(cursorPosition, cursorPosition+MAX_STEPS_FROM_BORDER), getXTextPosition(), getYTextPosition() );
			} else {
				textFont.drawString(graphics, displayedText.substring(displayedText.length()-MAX_STEPS_FROM_BORDER, displayedText.length()), getXTextPosition(), getYTextPosition() );
			}
		}
	}

	/**
	 * Draws only part of the background surface, the text line not to refresh whole widget
	 * @param graphics
	 * 			object capable of drawing
	 * @throws GUIException
	 */
	public void drawTextLine(Graphics graphics) throws GUIException {
		//TODO change an argument 150 for visible text length
		graphics.drawImage(textField, (int) (getWidth() * 0.2), (int) (getHeight() * 0.3),
						   getXTextPosition(), getYTextPosition(), 160, textFont.getHeight() + 1 ); 
	}
	
	/**
	 * Erases cursor from its position
	 * @param graphics
	 * 			object capable of drawing
	 * @throws GUIException
	 */
	public void eraseCursor(Graphics graphics) throws GUIException {
		if (prevOnScreenCursorPosition != 0) {
			graphics.setColor(new Color(0, 0, 0, 255));
			graphics.drawLine(onScreenCursorPosition, getYTextPosition(), 
							  onScreenCursorPosition, getYTextPosition() + textFont.getHeight() );
			//graphics.drawLine(prevOnScreenCursorPosition, getYTextPosition(), prevOnScreenCursorPosition, getYTextPosition() +  textFont.getHeight());
		}
		prevOnScreenCursorPosition=onScreenCursorPosition;
	}
	
	/**
	 * Get displayed text
	 * @return
	 * 		displayed text
	 */
	public String getDisplayedText() {
		return displayedText;
	}

	/**
	 * Get X position of text on the screen
	 * @return
	 * 		X position of text on the screen
	 */
	public int getXTextPosition(){
		return getX() + (int) (getWidth() * 0.2);
	}

	/**
	 * Get Y position of text on the screen
	 * @return
	 * 		Y position of text on the screen
	 */
	public int getYTextPosition(){
		return getY() + (int) (getHeight() * 0.3);
	}
	
	/**
	 * Takes action after key press, called internally by gui
	 */
	public void keyPress(Key key) throws GUIException {
		try {
			if (key.getValue() == Key.LEFT && cursorPosition > 0) {
				if (displayedText.length() - cursorPosition > MAX_STEPS_FROM_BORDER) {
					elementChanged = ElementChanged.TEXT;
				} else {
				onScreenCursorPosition-=((CalibriFont) textFont).getCharacterWidth(displayedText.charAt(cursorPosition-1));
				
					if (stepsFromBorder < 10) {
						stepsFromBorder++;
					}
					elementChanged = ElementChanged.TEXT;
				//elementChanged = ElementChanged.CURSOR;
				}
				cursorPosition--;
			} else if (key.getValue() == Key.RIGHT && cursorPosition < displayedText.length()) {
				if (stepsFromBorder > 0) {
					onScreenCursorPosition+=((CalibriFont) textFont).getCharacterWidth(displayedText.charAt(cursorPosition));
					stepsFromBorder--;
					//elementChanged = ElementChanged.CURSOR;
					elementChanged = ElementChanged.TEXT;
				} else {
					elementChanged = ElementChanged.TEXT;
				}
				cursorPosition++;
			} else if (key.getValue() == Key.DELETE && cursorPosition < displayedText.length()) {
				displayedText = displayedText.substring(0, cursorPosition) + displayedText.substring(cursorPosition + 1);
								
				/*if(displayedText.length() > 9 && cursorPosition != 0){
					onScreenCursorPosition+=((CalibriFont)textFont).getCharacterWidth(displayedText.charAt(cursorPosition-1));	
					stepsFromBorder--;
				}*/
				elementChanged = ElementChanged.TEXT;
			} else if (key.getValue() == Key.BACKSPACE && cursorPosition > 0) {
				
				if ( cursorPosition < MAX_STEPS_FROM_BORDER + 1) {
					onScreenCursorPosition-=((CalibriFont) textFont).getCharacterWidth(displayedText.charAt(cursorPosition - 1));
					stepsFromBorder++;
				}
				displayedText = displayedText.substring(0, cursorPosition - 1) + displayedText.substring(cursorPosition);
				cursorPosition--;
				elementChanged = ElementChanged.TEXT;
			} else if (key.getValue() == Key.ENTER) {
				generateAction();
			} else if (key.getValue() == Key.HOME) {
				cursorPosition = 0;
				stepsFromBorder = MAX_STEPS_FROM_BORDER ;
				onScreenCursorPosition = getXTextPosition();
				elementChanged = ElementChanged.TEXT;
				//elementChanged = ElementChanged.CURSOR; 
			} else if (key.getValue() == Key.END) {
				cursorPosition = displayedText.length();
				stepsFromBorder = (displayedText.length()>MAX_STEPS_FROM_BORDER) ? 0 : MAX_STEPS_FROM_BORDER-displayedText.length() ;
				if (cursorPosition < 11) {
					onScreenCursorPosition = getXTextPosition() + textFont.getWidth(displayedText);
				} else {
					onScreenCursorPosition = getXTextPosition() + textFont.getWidth(displayedText.substring(cursorPosition - 10, cursorPosition));
				}
				elementChanged = ElementChanged.TEXT;
				//elementChanged = ElementChanged.CURSOR;
			} else if (key.getValue() < 127 && key.getValue() > 31) {	
				displayedText = displayedText.substring(0, cursorPosition) + (char) key.getValue() + displayedText.substring(cursorPosition);
				if (cursorPosition < MAX_STEPS_FROM_BORDER) {
					stepsFromBorder--;
					onScreenCursorPosition+=((CalibriFont)textFont).getCharacterWidth((char) key.getValue());
				}
				cursorPosition++;
				elementChanged = ElementChanged.TEXT;
			}
			
			if (updateListener != null && elementChanged != ElementChanged.NONE) {
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
		} catch (SDLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Not used
	 */
	public void keyRelease(Key key) throws GUIException {
		// TODO Auto-generated method stub
	};
	
	/**
	 * Acknowledges mouse over text field, starts flickering cursor
	 */
	@Override
	public void mouseInMessage() throws GUIException {
		// TODO Auto-generated method stub
		super.mouseInMessage();
		flickeringCursorHandler.setRunning(true);
	}

	/**
	 * Acknowledges mouse leaving text field, stops flickering cursor
	 */
	@Override
	public void mouseOutMessage() {
		// TODO Auto-generated method stub
		super.mouseOutMessage();
		flickeringCursorHandler.setRunning(false);
	}

	/**
	 * Set text to be displayed
	 * @param displayedText
	 * 			text to be displayed
	 */
	public void setDisplayedText(String displayedText) {
		this.displayedText = displayedText;
		elementChanged = ElementChanged.TEXT;
	}
}
