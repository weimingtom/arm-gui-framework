package platform.thread;

import platform.gui.Area;
import platform.gui.PlatformDropDown;
import platform.gui.Screen;
import sdljava.SDLException;
import sdljavax.guichan.GUIException;
import sdljavax.guichan.evt.Key;
import sdljavax.guichan.evt.KeyInput;
import sdljavax.guichan.evt.MouseInput;
import sdljavax.guichan.sdl.SDLInput;
import sdljavax.guichan.widgets.Widget;

public class EventDispatcher extends Thread{

	boolean eventTriggered;
	private SDLInput inputSource;
	private Area background;
	private Area foreground;
	private ActiveArea activeArea;
	private Widget widgetWithMouse= null;
	
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
			while(Screen.getScreen().isRunning()){
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
				
				
				while (false == inputSource.isMouseQueueEmpty()) {
					
					MouseInput mi = inputSource.dequeueMouseInput();

						
					if (mi.x > 0 && mi.y > 0 ){
						if(widgetWithMouse != null){
								
							if(!widgetWithMouse.getDimension().isPointInRect(mi.x, mi.y)){
								
								widgetWithMouse.mouseOutMessage();
								
								widgetWithMouse = null;
													
							}
							else{
								
								widgetWithMouse.mouseInputMessage(mi);
								continue;
							}
						}
						
						for(Widget widget: active.getWidgetMap().keySet()){
																			
							if( widget.getDimension().isPointInRect(mi.x, mi.y) ) {
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
			
		} catch(InterruptedException e){
			e.printStackTrace();
		} catch (GUIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SDLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e){
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
