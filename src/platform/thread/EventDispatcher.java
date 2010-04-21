package platform.thread;

import platform.gui.Area;
import platform.gui.Screen;
import sdljava.SDLException;
import sdljavax.guichan.GUIException;
import sdljavax.guichan.evt.Key;
import sdljavax.guichan.evt.KeyInput;
import sdljavax.guichan.sdl.SDLInput;

public class EventDispatcher extends Thread{

	boolean eventTriggered;
	private SDLInput inputSource;
	private Area background;
	private Area foreground;
	private ActiveArea activeArea;
	
	public EventDispatcher(SDLInput input) throws SDLException{
		super("EventDispatcher");
		eventTriggered = false;
		inputSource = input;
		
		background = Screen.getScreen().getBackground();
		foreground = Screen.getScreen().getForeground();
		
		activeArea = ActiveArea.BACKGROUND;
		start();
	}
	
	public void run(){
		try{
			while(!Thread.interrupted()){
			
				synchronized(this){		
					while(eventTriggered == false){ 	//waiting for an event to be triggered
						wait(); 
					}
					eventTriggered = false;
				}
				inputSource.pollInput();
				Area active = (activeArea == ActiveArea.BACKGROUND) ? background : foreground;
					
				while (false == inputSource.isKeyQueueEmpty()) {	
					KeyInput ki = inputSource.dequeueKeyInput();

					if (Key.TAB == ki.getKey().getValue() && KeyInput.PRESS == ki.getType()) {
						if (ki.getKey().isShiftPressed()) {
							active.getFocusHandler().tabPrevious();
						} else {
							active.getFocusHandler().tabNext();
						}
					} 
					else {
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
				
			}
			
		} catch(InterruptedException e){
			e.printStackTrace();
		} catch (GUIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public ActiveArea getActiveArea() {
		return activeArea;
	}

	public void setActiveArea(ActiveArea activeArea) {
		this.activeArea = activeArea;
	}

	enum ActiveArea { FOREGROUND, BACKGROUND };
	
}
