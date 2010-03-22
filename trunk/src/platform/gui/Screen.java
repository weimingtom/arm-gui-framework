package platform.gui;

import sdljava.SDLException;
import sdljava.video.SDLVideo;
import sdljavax.guichan.GUIException;
import sdljavax.guichan.evt.Input;

public class Screen {

	
	
	public static final int _screenWidth = 320;
	public static final int _screenHeight = 240;
	public static final long _screenflags = SDLVideo.SDL_HWSURFACE | SDLVideo.SDL_DOUBLEBUF | SDLVideo.SDL_HWACCEL | SDLVideo.SDL_ANYFORMAT ;
	
	private static Screen _soleInstance = null;
 	private final long alphaFlags = SDLVideo.SDL_SRCALPHA | SDLVideo.SDL_RLEACCEL ; 
	private Area foreground;
	private Area background;
	private Input inputSource;
	
	
	
	public static Screen getScreen(){
		
		if(_soleInstance == null){
			
			_soleInstance = new Screen();
			
		}
					
			return _soleInstance;
	}

	public void handleEvents() throws GUIException {
		
		
		
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
	
	public void setAreaAlpha(Area area, int alphaIndex) throws SDLException{
		
		area.getTarget().setAlpha(alphaFlags, alphaIndex);
		
	}
	
	private Screen(){
		
	}
}
