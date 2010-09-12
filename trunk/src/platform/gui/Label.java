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

import platform.font.CalibriFont;
import platform.gfx.UnifiedGraphics;
import platform.util.WidgetUpdate;
import sdljava.SDLException;
import sdljava.video.SDLColor;
import sdljava.video.SDLRect;
import sdljava.video.SDLSurface;
import sdljava.video.SDLVideo;
import sdljavax.guichan.GUIException;
import sdljavax.guichan.evt.MouseListener;
import sdljavax.guichan.font.Font;

/**
 * Class representing simple label
 * @author Bartosz Kędra
 * @author bartosz.kedra@gmail.com
 *
 */
public class Label extends PlatformWidget implements MouseListener {

	/**
	 * Label background surface
	 */
	protected SDLSurface labelSurface;
	
	/**
	 * Label upper text
	 */
	protected String headText;
	
	/**
	 * Label lower text
	 */
	protected String descriptiveText;
	
	/**
	 * Font used for drawing text
	 */
	protected Font textFont;
	
	/**
	 * Text shift - used for an extra effect of text leaving label
	 */
	protected int shift;
	
	/**
	 * Index of character in text that is currently disappering
	 */
	protected int shiftIndex=0;
	
	/**
	 * Flag used for communicating that an effect has ended
	 */
	protected boolean shiftEnded = false;
	
	/**
	 * Constructor with no lower text
	 * @param head
	 * 			upper text string
	 * @param width
	 * 			label width
	 * @param height
	 * 			label height
	 * @throws SDLException
	 */
	public Label(String head, int width, int height) throws SDLException {
		this(head, "", width, height);
	}
	
	/**
	 * Constructor with lower text
	 * @param head
	 * 			upper text string
	 * @param description
	 * 			lower text string
	 * @param width
	 * 			label width
	 * @param height
	 * 			label height
	 * @throws SDLException
	 */
	public Label(String head, String description, int width, int height) throws SDLException {
		super ();
		headText = head;
		descriptiveText = description;
		
		labelSurface = SDLVideo.createRGBSurface(SDLVideo.SDL_HWSURFACE, width, height, 16, 0, 0, 0, 0);
		labelSurface.fillRect(labelSurface.mapRGB(150, 220, 250));
		setWidth(width);
		setHeight(height);
		
		if (descriptiveText.length() != 0) {
			textFont = new CalibriFont((int) (getHeight() * 0.45), new SDLColor(10,10,10), true);
		} else {
			textFont = new CalibriFont((int) (getHeight() * 0.7), new SDLColor(10,10,10), true);
		}
		addMouseListener(this);
	}
	
	/**
	 * Cleans any dynamically reserved memory areas in C style 
	 * @throws GUIException
	 */
	public void delete(){
		try {
			labelSurface.freeSurface();
			super.delete();
		} catch (SDLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GUIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Draws label on the surface 
	 * @param graphics
	 * 			used for drawing label on target surface
	 * @throws GUIException
	 */
	@Override
	public void draw(UnifiedGraphics graphics) throws GUIException {
		drawBorder(graphics);
		textFont.drawString(graphics, headText, getX() + 10, getY() + (int) (getHeight() * 0.05) );	
		
		if (descriptiveText.length() != 0) {
			if (textFont instanceof CalibriFont) {
				synchronized (this) {
					try {
						if (shift < ((CalibriFont) textFont).getCharacterWidth(descriptiveText.charAt(shiftIndex)) && shiftIndex==0 ) {
							((CalibriFont) textFont).drawStringBlended(graphics, descriptiveText, 
																	   getX() + 10 - shift, getY() + (int) (getHeight() * 0.5) );
						} else if (shift < ((CalibriFont) textFont).getCharacterWidth(descriptiveText.charAt(shiftIndex))) {
							((CalibriFont) textFont).drawStringBlended(graphics, descriptiveText.substring(shiftIndex),
																	  getX() + 10 - shift, getY() + (int)(getHeight() * 0.5) );
						} else {
							shift -= ((CalibriFont) textFont).getCharacterWidth(descriptiveText.charAt(shiftIndex));
							
							if (shiftIndex + 1 != descriptiveText.length()) { ; //% (descriptiveText.length());
								shiftIndex ++;
								((CalibriFont) textFont).drawStringBlended(graphics, descriptiveText.substring(shiftIndex),
																		   getX() + 10 - shift, getY() + (int)(getHeight() * 0.5) );
							}
						}
					} catch (SDLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} else {
			textFont.drawString(graphics, descriptiveText, getX() + 10, getY() + (int) (getHeight() * 0.6) );
			}
		}
	}
	
	/**
	 * Draws border - background on the target surface
	 * @param graphics
	 * 			used for drawing background on target surface
	 * @throws GUIException
	 */
	@Override
	public void drawBorder(UnifiedGraphics graphics) throws GUIException {
		try {
			graphics.drawSDLSurface(labelSurface, labelSurface.getRect(),
									graphics.getTarget().getRect(getX(), getY()));
		} catch (SDLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * MouseListener implementation - launches the anonymous thread 
	 * that makes an effect of text leaving the label 
	 */
	public void mouseClick(int x, int y, int button, int count) throws GUIException {
		new Thread(){
			public void run(){
				int textWidth = textFont.getWidth(descriptiveText);
				try{
					for (int i = 0 ; i < textWidth; i+=5) {
						synchronized(Label.this){
							shift+=5 ;
						}
						updateListener.putRegionToUpdate(new WidgetUpdate(Label.this, new SDLRect(getX(), getY(), getWidth(), getHeight())));
						Thread.sleep(100);
					}
					
					Thread.sleep(100);
					synchronized(Label.this){
						shift = 0;
						shiftIndex = 0;
					}
					updateListener.putRegionToUpdate( new WidgetUpdate( Label.this, new SDLRect(Label.this.getX(), Label.this.getY(),
																								Label.this.getWidth(), Label.this.getHeight())));
					Thread.sleep(50);
					
				}  catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
	}

	/**
	 * Acknowledges mouse over label
	 */
	public void mouseIn() throws GUIException {
		m_bHasMouse =  true;
		requestFocus();	
	}

	/**
	 * Not used
	 */
	public void mouseMotion(int x, int y) throws GUIException {
		// TODO Auto-generated method stub
	}

	/**
	 * Acknowledges mouse leaving label
	 */
	public void mouseOut() {
		m_bHasMouse =  false;
		try {
			lostFocus();
		} catch (GUIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Not used
	 */
	public void mousePress(int x, int y, int button) throws GUIException {
		// TODO Auto-generated method stub
	}

	/**
	 * Not used
	 */
	public void mouseRelease(int x, int y, int button) throws GUIException {
		// TODO Auto-generated method stub
	}

	/**
	 * Not used
	 */
	public void mouseWheelDown(int x, int y) throws GUIException {
		// TODO Auto-generated method stub
	}

	/**
	 * Not used
	 */
	public void mouseWheelUp(int x, int y) throws GUIException {
		// TODO Auto-generated method stub
	}
}
