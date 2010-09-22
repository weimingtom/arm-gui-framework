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
import platform.gui.TextField.ElementChanged;
import platform.util.WidgetUpdate;
import sdljava.SDLException;
import sdljava.video.SDLColor;
import sdljava.video.SDLRect;
import sdljava.video.SDLSurface;
import sdljava.video.SDLVideo;
import sdljavax.guichan.GUIException;
import sdljavax.guichan.evt.ActionListener;
import sdljavax.guichan.evt.MouseListener;
import sdljavax.guichan.font.Font;

/**
 * Class representing simple label
 * @author Bartosz Kędra
 * @author bartosz.kedra@gmail.com
 *
 */
public class Label extends PlatformWidget implements MouseListener, ActionListener {

		class TimerHandler extends Thread {
		
		int minutes;
		
		int seconds;
		
		int separatorIndex = 3;
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
		TimerHandler(boolean run) {
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
						minutes = Integer.parseInt(Label.this.descriptiveText.substring(0, separatorIndex -1));
						seconds = Integer.parseInt(Label.this.descriptiveText.substring(separatorIndex ));
						
						minutes += ((seconds  + 1)/ 60);
						seconds  = (seconds + 1) % 60;
						
						if ( minutes < 10 && seconds < 10) {
							descriptiveText = "0" + Integer.toString(minutes) + ":0" + Integer.toString(seconds);
						} else if ( minutes < 10) {
							descriptiveText = "0" + Integer.toString(minutes) + ":" + Integer.toString(seconds);
						} else if ( seconds < 10) {
							descriptiveText = "" + Integer.toString(minutes) + ":0" + Integer.toString(seconds);
						} else {
							descriptiveText = "" + Integer.toString(minutes) + ":" + Integer.toString(seconds);
						}
						
						updateListener.putRegionToUpdate(new WidgetUpdate(Label.this, 
																		  new SDLRect(Label.this.getX(),Label.this.getY(), Label.this.getWidth(), Label.this.getHeight()) ));
							
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
		
		public void zeroTimer() {
			descriptiveText = "00:00";
			try {
				updateListener.putRegionToUpdate(new WidgetUpdate(Label.this, 
						  new SDLRect(Label.this.getX(),Label.this.getY(), Label.this.getWidth(), Label.this.getHeight()) ));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	protected Label.TimerHandler timerHandler = null;
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
	 * Index of character in text that is currently disappearing
	 */
	protected int shiftIndex = 0;
	
	/**
	 * Flag used for communicating that an effect has ended
	 */
	protected boolean shiftEnded = false;
	
	protected int headTextShift;
	
	protected int descriptiveTextShift;
	
	protected PlatformDropDown platDropDown;
	
	public Label(String head, String description, int width, int height, PlatformDropDown dropDown) throws SDLException {
		this(head,description,width,height);
		platDropDown = dropDown;
	}
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
			if ((int) (getHeight() * 0.45) > 20){
				textFont = new CalibriFont(20, new SDLColor(10,10,10), true);
			} else {
			textFont = new CalibriFont((int) (getHeight() * 0.45), new SDLColor(10,10,10), true);
			}
		} else {
			textFont = new CalibriFont((int) (getHeight() * 0.7), new SDLColor(10,10,10), true);
		}
		
		headTextShift = (getWidth() - textFont.getWidth(headText)) / 2;
		descriptiveTextShift = (getWidth() - textFont.getWidth(descriptiveText)) / 2;
		if(descriptiveTextShift < 0) descriptiveTextShift = 0; 
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
		textFont.drawString(graphics, headText, getX() + headTextShift , getY() + (int) (getHeight() * 0.05) );	
		
		if (descriptiveText.length() != 0) {
			if (textFont instanceof CalibriFont) {
				synchronized (this) {
					try {
						if (shift < ((CalibriFont) textFont).getCharacterWidth(descriptiveText.charAt(shiftIndex)) + descriptiveTextShift && shiftIndex==0 ) {
							((CalibriFont) textFont).drawStringBlended(graphics, descriptiveText, 
																	   getX() + descriptiveTextShift - shift, getY() + (int) (getHeight() * 0.5) );
						} else if (shift < ((CalibriFont) textFont).getCharacterWidth(descriptiveText.charAt(shiftIndex)) + descriptiveTextShift) {
							((CalibriFont) textFont).drawStringBlended(graphics, descriptiveText.substring(shiftIndex),
																	  getX() + descriptiveTextShift - shift, getY() + (int)(getHeight() * 0.5) );
						} else {
							shift -= ((CalibriFont) textFont).getCharacterWidth(descriptiveText.charAt(shiftIndex));
							
							if (shiftIndex + 1 != descriptiveText.length()) {  //% (descriptiveText.length());
								shiftIndex ++;
								((CalibriFont) textFont).drawStringBlended(graphics, descriptiveText.substring(shiftIndex),
																		   getX() + descriptiveTextShift - shift, getY() + (int)(getHeight() * 0.5));
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

	protected void moveText(){
		new Thread(){
			public void run(){
				int textWidth = textFont.getWidth(descriptiveText);
				try{
					for (int i = 0 ; i < textWidth + descriptiveTextShift; i+=5) {
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
	 * MouseListener implementation - launches the anonymous thread 
	 * that makes an effect of text leaving the label 
	 */
	public void mouseClick(int x, int y, int button, int count) throws GUIException {
		//generateAction();
		moveText();
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

	public void action(String actionName) throws GUIException {
		int index;
		String position = null;
		
		if (timerHandler == null) {
			
		} else {
			if (actionName.equals("play")) {
				timerHandler.setRunning(true);
			} else if(actionName.equals("pause")) {
				timerHandler.setRunning(false);
			} else if(actionName.equals("next") || actionName.equals("previous") || actionName.equals("ListBox selection")){
				timerHandler.zeroTimer();
			}
			
			return;
		}
		
		if (actionName.equals("ListBox selection") || actionName.equals("music")) {
			position = platDropDown.getListModel().getElementAt(platDropDown.listBox.getSelected());
		} else if(actionName.equals("next")) {
			platDropDown.listBox.setSelected(platDropDown.listBox.getSelected() + 1);
			position = platDropDown.getListModel().getElementAt(platDropDown.listBox.getSelected());
		} else if(actionName.equals("previous")) {
			if(platDropDown.listBox.getSelected() != 0 ) {
				platDropDown.listBox.setSelected(platDropDown.listBox.getSelected() - 1);
				position = platDropDown.getListModel().getElementAt(platDropDown.listBox.getSelected());
			} else {
				return;
			}
		} else if ( actionName.equals("play")) {
			moveText();
			return;
		}	else {
				return;
		}
	
		index = position.indexOf('-');
		headText = position.substring(0, index);
		descriptiveText = position.substring(index + 1);
		
		headTextShift = (getWidth() - textFont.getWidth(headText)) / 2;
		descriptiveTextShift = Math.abs((getWidth() - textFont.getWidth(descriptiveText)) / 2);
		if(descriptiveTextShift < 0) descriptiveTextShift = 0; 
		
		try {
			updateListener.putRegionToUpdate( new WidgetUpdate( this, new SDLRect(this.getX(), this.getY(), this.getWidth(), this.getHeight())));
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void startCounting(){
		timerHandler = new TimerHandler(false);
	}
}
