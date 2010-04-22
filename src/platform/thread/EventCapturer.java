package platform.thread;

import platform.gui.Screen;
import sdljava.SDLException;
import sdljava.event.SDLEvent;
import sdljava.event.SDLKey;
import sdljava.event.SDLKeyboardEvent;
import sdljavax.guichan.sdl.SDLInput;

public class EventCapturer extends Thread{

	private SDLInput inputSource;
	private SDLEvent event;
	private EventDispatcher eventDispatcher;
	private boolean eventCaptured;
	
	public EventCapturer(SDLInput input, EventDispatcher dispatcher) throws SDLException{
		
		super("EventCapturer");
		inputSource = input;
		eventDispatcher = dispatcher;
		// We want unicode
		SDLEvent.enableUNICODE(1);
		// We want to enable key repeat
		SDLEvent.enableKeyRepeat(SDLEvent.SDL_DEFAULT_REPEAT_DELAY, SDLEvent.SDL_DEFAULT_REPEAT_INTERVAL);
		eventCaptured = false;
		start();
	}
	
	public void run(){
		//TODO implement handling mousekeydown etc.
		try {
			while(!Thread.interrupted()){
					/*
					 * Poll SDL events
					 */
					while (null != (event = SDLEvent.pollEvent())) {	
						if (event.getType() == SDLEvent.SDL_KEYDOWN) {
								if (((SDLKeyboardEvent) event).getSym() == SDLKey.SDLK_ESCAPE) {
											Screen.getScreen().setRunning(false);
											return;
								}		
							eventCaptured = true;
						} 
						else if (event.getType() == SDLEvent.SDL_QUIT) {
							Screen.getScreen().setRunning(false);	
							return;
						}
							/*
							 * Now that we are done polling and using SDL events we pass the leftovers to the
							 * SDLInput object to later be handled by the Gui.
							 */
							 inputSource.pushInput(event);	
					}
					if(eventCaptured){
						eventCaptured = false;
						
						synchronized(eventDispatcher){
							eventDispatcher.eventTriggered = true;
							eventDispatcher.notify();
						}
					}
					//Thread.yield();
					Thread.sleep(200);
			}
		}	
		catch (SDLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

}	

