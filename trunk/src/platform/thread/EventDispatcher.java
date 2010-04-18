package platform.thread;

import platform.gui.Area;
import sdljavax.guichan.sdl.SDLInput;

public class EventDispatcher extends Thread{

	boolean eventTriggered;
	private SDLInput inputSource;
	private Area background;
	private Area foreground;
	
	public EventDispatcher(SDLInput input){
	
		super("EventDispatcher");
		eventTriggered = false;
		inputSource = input;
		start();
	}
	
	public void run(){
	
		try{
			
			while(!Thread.interrupted()){
			
				synchronized(this){
					while(eventTriggered == false){ 	//waiting for an event to be triggered
						wait(); 
					}
				}
				
					eventTriggered = false;
								
				inputSource.pollInput();
				//TODO dispatch events to an active area
				
			}
			
		}
		
		catch(InterruptedException e){
			
			e.printStackTrace();
		}
		
	}
	
}
