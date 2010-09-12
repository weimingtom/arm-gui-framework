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

import platform.buttons.ClickedDemo2PlayButton;
import platform.buttons.ClickedDemoPlayButton;
import platform.buttons.ClickedPlayButton;
import platform.buttons.DefaultPlayButton;
import platform.buttons.SelectedPlayButton;
import platform.gfx.UnifiedGraphics;
import platform.util.UpdateListener;
import platform.util.WidgetUpdate;
import sdljavax.guichan.GUIException;
import sdljavax.guichan.evt.MouseListener;

/**
 * Class representing button , class changes beahvior according to internal state
 * @author Bartosz Kędra
 * @author bartosz.kedra@gmail.com 
 */

public class Button extends PlatformWidget implements MouseListener, UpdateListener {

	/**
	 * Three button states - default, clicked and selected
	 */
	ButtonState defaultButton;
	ButtonState clickedButton;
	ButtonState selectedButton;

	/**
	 * Current button state, the class behaved differently according to that state
	 */
	ButtonState currentState;
		
	/**
	 * Constructor
	 * @param resourceDir
	 * 			path to directory where the button images are stored
	 * @param demoNr
	 * 			for the demo purposes, the configuration can be chosen
	 * @throws GUIException
	 */
	public Button(String resourceDir, int demoNr) throws GUIException {
		super ();
		
		defaultButton = new DefaultPlayButton(resourceDir + "music_button_default.png", this , this);
		selectedButton = new SelectedPlayButton(resourceDir + "music_button_selected.png", this, this);
		
		if (demoNr == 0) {
			clickedButton = new ClickedPlayButton(resourceDir + "music_button_pressed.png", this, this);
		} else if (demoNr == 1) {
			clickedButton = new ClickedDemoPlayButton(resourceDir + "music_button_pressed.png", this, this);
		} else if (demoNr == 2) {
			clickedButton = new ClickedDemo2PlayButton(resourceDir + "music_button_pressed.png", this, this);
		}
		currentState = defaultButton;
		
		setWidth(defaultButton.getWidth());
		setHeight(defaultButton.getHeight());
		
		addMouseListener(this);
	}
	
	/**
	 * Cleans any dynamically reserved memory areas in C style 
	 * @throws GUIException
	 */
	@Override
	public void delete() throws GUIException {
		defaultButton.delete();
		clickedButton.delete();
		selectedButton.delete();
		super.delete();
	}

	/**
	 * Draws button on the surface - method overridden 
	 * @param graphics
	 * 			used for drawing button on target surface
	 * @throws GUIException
	 */
	@Override
	public void draw(UnifiedGraphics graphics) throws GUIException {
		graphics.drawImage(currentState.getImage(), getX(), getY());
	}

	/**
	 * Draws border - method overridden, not used
	 * @throws GUIException
	 */
	@Override
	public void drawBorder(UnifiedGraphics graphics) throws GUIException {
		// TODO Auto-generated method stub
	}
	
	/**
	 * Get clicked button state object
	 * @return <code> ButtonState </code>
	 * 
	 */
	public ButtonState getClickedButton() {
		return clickedButton;
	}

	/**
	 * Get default button state object
	 * @return <code> ButtonState </code>
	 */
	public ButtonState getDefaultButton() {
		return defaultButton;
	}

	/**
	 * Get selected button state object
	 * @return <code> ButtonState </code>
	 */
	public ButtonState getSelectedButton() {
		return selectedButton;
	}

	public void mouseClick(int x, int y, int button, int count)	throws GUIException {
		//buttonState=ButtonStates.DEFAULT;	
	}

	public void mouseIn() throws GUIException {
		currentState.mouseIn();
	}

	public void mouseMotion(int x, int y) throws GUIException {
		// TODO Auto-generated method stub
	}

	public void mouseOut() {
		currentState.mouseOut();
	}
	
	public void mousePress(int x, int y, int button) throws GUIException {
		currentState.mousePress();
	}

	public void mouseRelease(int x, int y, int button) throws GUIException {
		currentState.mouseRelease();
	}

	public void mouseWheelDown(int x, int y) throws GUIException {
		// TODO Auto-generated method stub
	}

	public void mouseWheelUp(int x, int y) throws GUIException {
		// TODO Auto-generated method stub
	}

	public void putRegionToUpdate(WidgetUpdate updateInfo) throws InterruptedException {
		updateListener.putRegionToUpdate(updateInfo);
	}

	public void setCurrentState(ButtonState currentState) {
		this.currentState = currentState;
	}
}
