package platform.gui;

import platform.thread.EventCapturer;
import platform.thread.EventDispatcher;
import sdljava.SDLException;
import sdljava.video.SDLSurface;
import sdljava.video.SDLVideo;
import sdljavax.guichan.GUIException;
import sdljavax.guichan.evt.Input;
import sdljavax.guichan.gfx.Graphics;
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
	private SDLSurface target;
	private Graphics graphics;
	private volatile boolean running;
	
	public static Screen getScreen() throws SDLException{	
		if(_soleInstance == null){
			
			synchronized(Screen.class){
				if(_soleInstance == null){
					_soleInstance = new Screen();
				}
			}
			
		}
			return _soleInstance;
	}
	public synchronized void refresh() throws SDLException{
		target.flip();
	}

	public void setAreas(Area background, Area foreground) throws SDLException{		
		this.background = background;
		this.foreground = foreground;
		
		startEventHandling();
	}
				
	public Graphics getGraphics() {
		return graphics;
	}

	public void setGraphics(Graphics graphics) {
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

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public void delete() throws SDLException, GUIException{
		target.freeSurface();
		foreground.delete();
		background.delete();
	}
	
	private void startEventHandling() throws SDLException{
		EventDispatcher eventDispatcher = new EventDispatcher((SDLInput)inputSource );
		new EventCapturer((SDLInput)inputSource, eventDispatcher);
	}
	
	private Screen() throws SDLException{	
		//TODO change that for normal variables ?
		target = SDLVideo.setVideoMode(Screen._screenWidth, Screen._screenHeight,0 ,Screen._screenflags	);
		graphics = new SDLGraphics();
		
		if(graphics instanceof SDLGraphics){
			((SDLGraphics)graphics).setTarget(target);
		}
		inputSource = new SDLInput();
		running = true;
	}
	
	
}
