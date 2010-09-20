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
import platform.gui.Screen;
import sdljava.SDLException;
import sdljava.event.SDLEvent;
import sdljava.event.SDLKey;
import sdljava.event.SDLKeyboardEvent;
/**
 * Class serving as constantly running thread that captures the events
 * @author Bartosz Kędra
 * @author bartosz.kedra@gmail.com
 *
 */

public class EventCapturer extends Thread{

	/**
	 * Input event source
	 */
	private ExtendedInput inputSource;
	
	/**
	 * Event object
	 */
	private SDLEvent event;
	
	/**
	 * Thread that dispatches events to appriopriate objects
	 * <code> EventDispatcher </code>
	 */
	private EventDispatcher eventDispatcher;
	
	/**
	 * Flag indicating whether input event was captured
	 */
	private boolean eventCaptured;
	
	/**
	 * Constructor also starts the thread
	 * @param input
	 * 			Input event source
	 * @param dispatcher
	 * 			Event dispatcher
	 * @throws SDLException
	 */
	public EventCapturer(ExtendedInput input, EventDispatcher dispatcher) throws SDLException {
		super ("EventCapturer");
		inputSource = input;
		eventDispatcher = dispatcher;
		// We want unicode
		SDLEvent.enableUNICODE(1);
		// We want to enable key repeat
		SDLEvent.enableKeyRepeat(SDLEvent.SDL_DEFAULT_REPEAT_DELAY, SDLEvent.SDL_DEFAULT_REPEAT_INTERVAL);
		eventCaptured = false;
		start();
	}
	
	/**
	 * Thread polls for events, once event occurs it is analyzed ( if ESC the app finishes)
	 * and then it is put into event queue of inputSource and eventDispatcher is notified
	 * 
	 */
	public void run(){
		try {
			while (! Thread.interrupted()) {
					/*
					 * Poll SDL events
					 */
					while (null != (event = SDLEvent.pollEvent())) {	
						if (event.getType() == SDLEvent.SDL_KEYDOWN) {
								if (((SDLKeyboardEvent) event).getSym() == SDLKey.SDLK_ESCAPE) {
											Screen.getScreen().setRunning(false);
											return;
								}		
						} else if (event.getType() == SDLEvent.SDL_QUIT) {
							Screen.getScreen().setRunning(false);	
							return;
						}
							/*
							 * Now that we are done polling and using SDL events we pass the leftovers to the
							 * SDLInput object to later be handled by the Gui.
							 */
						eventCaptured = true;
						inputSource.pushInput(event);
					}
					if (eventCaptured) {
						eventCaptured = false;
						
						synchronized(eventDispatcher){
							eventDispatcher.eventTriggered = true;
							eventDispatcher.notify();
						}
					}
					Thread.sleep(100);
			}
		} catch (SDLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}	

