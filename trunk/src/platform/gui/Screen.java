package platform.gui;

import sdljava.SDLException;
import sdljava.video.SDLSurface;
import sdljava.video.SDLVideo;
import sdljavax.guichan.GUIException;
import sdljavax.guichan.evt.Input;
import sdljavax.guichan.sdl.SDLGraphics;

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
	private SDLGraphics graphics;
	
	public static Screen getScreen() throws SDLException{
		
		if(_soleInstance == null){
			
			_soleInstance = new Screen();
			
		}
					
			return _soleInstance;
	}
	
	public void refresh() throws GUIException, SDLException{
		
		//TODO add logic to distinguish whether refreshing really needed - Message pattern ?
		background.refreshArea();
		foreground.refreshArea();
		target.flip();
		
	}

	public void handleEvents() throws GUIException {
		
		
		
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
	
	
	
	private Screen() throws SDLException{
		//TODO change that for normal variables ?
		target = SDLVideo.setVideoMode(Screen._screenWidth, Screen._screenHeight,0 ,Screen._screenflags	);
		graphics = new SDLGraphics();
		graphics.setTarget(target);
	
	}
}
