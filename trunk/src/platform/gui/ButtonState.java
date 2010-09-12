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

import platform.util.UpdateListener;
import platform.util.WidgetUpdate;
import sdljava.video.SDLRect;
import sdljavax.guichan.GUIException;
import sdljavax.guichan.gfx.Image;

/**
 * Abstract class representing single state of button
 * @author Bartosz Kędra
 * @author bartosz.kedra@gmail.com
 */

public abstract class ButtonState {

	/**
	 * Button image when set in that state
	 */
	protected Image buttonImage;
	
	/**
	 * <code>UpdateListener</code> object for passing update info
	 */
	protected UpdateListener updateListener;
	
	/**
	 * <code>Button</code> that encapsulates this state
	 */
	protected Button parentButton;
	
	/**
	 * Constructor
	 * @param imageStr
	 * 			path to button image
	 * @param listener
	 * 			Update requests handling object
	 * @param parent
	 * 			button that encapsulates this state
	 * @throws GUIException
	 */
	public ButtonState(String imageStr, UpdateListener listener, Button parent) throws GUIException {
		buttonImage = new Image(imageStr);
		updateListener = listener;
		parentButton = parent;
	}
	
	/**
	 * Cleans any dynamically reserved memory areas in C style 
	 * @throws GUIException
	 */
	public void delete() throws GUIException {
		buttonImage.delete();
	}
		
	/**
	 * Get button height
	 * @return button height
	 */
	public int getHeight() {
		return buttonImage.getHeight();
	}
	
	/**
	 * Get button image
	 * @return button image
	 */
	public Image getImage() {
		return buttonImage;
	}
	
	/**
	 * Get button width
	 * @return button width
	 */
	public int getWidth() {
		return buttonImage.getWidth();
	}
	
	abstract public void mouseIn();
	
	/**
	 * Called when the mouse leaves the button surface
	 */
	public void mouseOut() {
		parentButton.setMouseEnabled(false);
		parentButton.setCurrentState(parentButton.getDefaultButton());
		try {
			updateListener.putRegionToUpdate(new WidgetUpdate(parentButton, new SDLRect(parentButton.getX(),parentButton.getY(),
																						buttonImage.getWidth(), buttonImage.getHeight())));
			parentButton.lostFocus();
		} catch (GUIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	abstract public void mousePress();
	
	abstract public void mouseRelease();	
}
