package platform.thread;

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
import platform.evt.ExtendedInput;
import platform.gui.Area;
import platform.gui.Screen;
import platform.util.Active;
import sdljava.SDLException;
import sdljavax.guichan.GUIException;
import sdljavax.guichan.evt.Key;
import sdljavax.guichan.evt.KeyInput;
import sdljavax.guichan.evt.MouseInput;
import sdljavax.guichan.widgets.Widget;

/**
 * Class serving as constantly running thread that dispatches events to appropriate widgets
 * @author Bartosz Kędra
 * @author bartosz.kedra@gmail.com
 *
 */
public class EventDispatcher extends Thread {

	/**
	 * Flag indicating whether an event was triggered
	 */
	boolean eventTriggered;
	
	/**
	 * Input event source
	 */
	private ExtendedInput inputSource;
	
	/**
	 * Background area of screen
	 */
	private Area background;
	
	/**
	 * Foreground area of screen
	 */
	private Area foreground;
	
	/**
	 * Enum indicating which area is active
	 */
	private Active activeArea;
	
	/**
	 * Widget that currently has mouse over
	 */
	private Widget widgetWithMouse = null;
	
	/**
	 * Constructor that starts the thread
	 * @param input
	 * 			Input event source
	 * @throws SDLException
	 */
	public EventDispatcher(ExtendedInput input) throws SDLException {
		super ("EventDispatcher");
		eventTriggered = false;
		inputSource = input;
		
		background = Screen.getScreen().getBackground();
		foreground = Screen.getScreen().getForeground();
		
		activeArea = Screen.getScreen().getActive(); 
		start();
	}
	
	/**
	 * Thread once notified of new event polls for this event 
	 * and dispatches it to the appropriate widget depending on area that is active and 
	 * widget that is focused
	 */
	public void run(){
		try{
			while (Screen.getScreen().isRunning()) {
				synchronized(this){		
					while (eventTriggered == false) { 	//waiting for an event to be triggered
						wait(); 
					}
					eventTriggered = false;
				}
				
				inputSource.pollInput();
				Area active = (Screen.getScreen().getActive() == Active.BACKGROUND) ? background : foreground;
				
				while (false == inputSource.isKeyQueueEmpty()) {	
					KeyInput ki = inputSource.dequeueKeyInput();

					if (Key.TAB == ki.getKey().getValue() && KeyInput.PRESS == ki.getType()) {
						if (ki.getKey().isShiftPressed()) {
							active.getFocusHandler().tabPrevious();
						} else {
							active.getFocusHandler().tabNext();
						}
					} else {
						// Send key inputs to the focused widgets
						if (null != active.getFocusHandler().getFocused()) {
							if (active.getFocusHandler().getFocused().isFocusable()) {
									active.getFocusHandler().getFocused().keyInputMessage(ki);
								
							} else {
								active.getFocusHandler().focusNone();
							}
						}
					}
					active.getFocusHandler().applyChanges();
				}
				
				while (false == inputSource.isMouseQueueEmpty()) {
					MouseInput mi = inputSource.dequeueMouseInput();
					if (mi.x > 0 && mi.y > 0 ) {
						if(widgetWithMouse != null){
							if (! widgetWithMouse.getDimension().isPointInRect(mi.x, mi.y)) {
								widgetWithMouse.mouseOutMessage();
								
								widgetWithMouse = null;
							} else{
								widgetWithMouse.mouseInputMessage(mi);
								continue;
							}
						}
						
						for (Widget widget : active.getWidgetMap().keySet()) {
							if (widget.getDimension().isPointInRect(mi.x, mi.y)) {
								widgetWithMouse = widget;
								if (false == widgetWithMouse.hasMouse()) {
									widgetWithMouse.mouseInMessage();
									continue;
								}
								widgetWithMouse.mouseInputMessage(mi);
							}	
						}
					}
					active.getFocusHandler().applyChanges();
				}
				Thread.sleep(200);
			}
		} catch(InterruptedException e) {
			e.printStackTrace();
		} catch (GUIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SDLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
