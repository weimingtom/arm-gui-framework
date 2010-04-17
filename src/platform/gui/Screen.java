package platform.gui;

import sdljava.SDLException;
import sdljava.event.SDLEvent;
import sdljava.event.SDLKey;
import sdljava.event.SDLKeyboardEvent;
import sdljava.video.SDLSurface;
import sdljava.video.SDLVideo;
import sdljavax.guichan.GUIException;
import sdljavax.guichan.evt.Input;
import sdljavax.guichan.sdl.SDLGraphics;
import sdljavax.guichan.sdl.SDLInput;

public class Screen {

	public static final int _screenWidth = 320;
	public static final int _screenHeight = 240;
	public static final long _screenflags = SDLVideo.SDL_HWSURFACE | SDLVideo.SDL_DOUBLEBUF | SDLVideo.SDL_HWACCEL | SDLVideo.SDL_ANYFORMAT ;
	public static final long _alphaFlags = SDLVideo.SDL_SRCALPHA | SDLVideo.SDL_RLEACCEL ; 
	
	private static Screen _soleInstance = null;
 	
	private Area foreground;
	private Area background;
	private Input inputSource;
	private SDLEvent event;
	private SDLSurface target;
	private SDLGraphics graphics;
	private boolean running;
	
	public static Screen getScreen() throws SDLException{
		
		if(_soleInstance == null){
			
			_soleInstance = new Screen();
			
		}
					
			return _soleInstance;
	}
	
	public void refresh() throws GUIException, SDLException{
		
		background.refreshArea();
		foreground.refreshArea();
		target.flip();
		
	}

	public void handleEvents() throws GUIException, SDLException {
		
		if( inputSource == null ){
			
			throw new GUIException("Cannot handle events - no input source set");
		}
		
		if(eventTriggered()){
			inputSource.pollInput();
			
			background.handleEvents();
			foreground.handleEvents();
		}
	}
		
	public SDLSurface getTarget() {
		return target;
	}

	public void setTarget(SDLSurface target) {
		this.target = target;
	}

	public SDLGraphics getGraphics() {
		return graphics;
	}

	public void setGraphics(SDLGraphics graphics) {
		this.graphics = graphics;
	}

	public void setForeground(Area foreground) {
		this.foreground = foreground;
	}

	public Area getForeground() {
		return foreground;
	}

	public void setBackground(Area background) {
		this.background = background;
	}

	public Area getBackground() {
		return background;
	}

	public void setInputSource(Input inputSource) {
		this.inputSource = inputSource;
	}

	public Input getInputSource() {
		return inputSource;
	}
	
	
	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	private Screen() throws SDLException{
		//TODO change that for normal variables ?
		
		target = SDLVideo.setVideoMode(Screen._screenWidth, Screen._screenHeight,0 ,Screen._screenflags	);
		graphics = new SDLGraphics();
		graphics.setTarget(target);
		inputSource = new SDLInput();
		running = true;
		// We want unicode
		SDLEvent.enableUNICODE(1);
		// We want to enable key repeat
		SDLEvent.enableKeyRepeat(SDLEvent.SDL_DEFAULT_REPEAT_DELAY, SDLEvent.SDL_DEFAULT_REPEAT_INTERVAL);
	
	}
	
	private boolean eventTriggered() throws SDLException{
		
		boolean hadEvent = false;
		/*
		 * Poll SDL events
		 */

		while (null != (event = SDLEvent.pollEvent())) {
			if (event.getType() == SDLEvent.SDL_KEYDOWN) {
				if (((SDLKeyboardEvent) event).getSym() == SDLKey.SDLK_ESCAPE) {
					running = false;
				}
				if (((SDLKeyboardEvent) event).getSym() == SDLKey.SDLK_f) {
					if (((SDLKeyboardEvent) event).getMod().ctrl()) {
						// Works with X11 only
						// SDL_WM_ToggleFullScreen(screen);
					}
				}
			} else if (event.getType() == SDLEvent.SDL_QUIT) {
				running = false;
			}

			/*
			 * Now that we are done polling and using SDL events we pass the leftovers to the
			 * SDLInput object to later be handled by the Gui.
			 */
			((SDLInput) inputSource).pushInput(event);
			hadEvent = true;
		}
		
		return hadEvent;
		
	}
}
