package platform.gui;

import sdljava.SDLException;
import sdljava.image.SDLImage;
import sdljava.video.SDLColor;
import sdljava.video.SDLSurface;
import sdljava.video.SDLVideo;
import sdljavax.guichan.GUIException;
import sdljavax.guichan.gfx.Color;
import sdljavax.guichan.gfx.Graphics;
import sdljavax.guichan.gfx.Image;
import sdljavax.guichan.sdl.SDLGraphics;
import sdljavax.guichan.sdl.SDLUtils;

public class Area {

	private SDLSurface surface;
	
	
	//TODO list of Widgets here , every Widget should contain xOffset, yOffset, 
	//TODO extends Container?
	private SDLGraphics surfaceGraphics;
	
	public Area(String filename) throws GUIException {
		
				
		try {
			surface= SDLImage.load(filename);
			surfaceGraphics = new SDLGraphics();
			surfaceGraphics.setTarget(surface);
		} 
		catch (SDLException e) {
			e.printStackTrace();
			throw new GUIException("Unable to load image");
			
		}
		

	}

	
	public Area(Image image) throws GUIException {
		
	
		
		surface = (SDLSurface) image.getData();
		
		if(surface == null){
			throw new GUIException("Unable to create surface from image");
		}
				
		surfaceGraphics = new SDLGraphics();
		surfaceGraphics.setTarget(surface);		
	}
	
	
	public Area(SDLColor color) throws GUIException{

		int rmask,gmask,bmask,amask;
		
		
		
		
		if (SDLUtils.SDL_BYTEORDER == SDLUtils.SDL_BIG_ENDIAN) {
			rmask = 0xff000000;
			gmask = 0x00ff0000;
			bmask = 0x0000ff00;
			amask = 0x000000ff;
		} else {
			rmask = 0x000000ff;
			gmask = 0x0000ff00;
			bmask = 0x00ff0000;
			amask = 0xff000000;
		}
		
		
		try {
			
			//TODO mask problem and transparency
			surface = SDLVideo.createRGBSurface(SDLVideo.SDL_HWSURFACE,Screen._screenWidth, Screen._screenHeight, 16 , 0, 0, 0, 0);
			surface.fillRect( surface.mapRGBA(color.getRed(),color.getGreen(), color.getBlue(), 0));
			surfaceGraphics = new SDLGraphics();
			surfaceGraphics.setTarget(surface);
				
		} 
		catch (SDLException e) {
			e.printStackTrace();
			throw new GUIException("Unable to create surface from color");
		}
		
		
		
	}
	
	public void refreshArea() throws GUIException{
		
		//TODO refreshing function depending on the area changed 
		drawSurface();
		
	}
	
	public SDLSurface getSurface() {
		return surface;
	}

	public void setSurface(SDLSurface surface) {
		this.surface = surface;
	}

	
	
	public void refreshTile(int tileIndex){
		
		
	}
	
	private void drawSurface() throws GUIException{
		
		
		try {
			SDLGraphics screenGraphics = Screen.getScreen().getGraphics();
				
			screenGraphics.beginDraw();
			screenGraphics.drawSDLSurface(surface, surface.getRect(), screenGraphics.getTarget().getRect());
			screenGraphics.endDraw();
			
		} 
		
		catch (SDLException e) {
			
			e.printStackTrace();
			throw new GUIException("Exception while drawing on target surface");
		}
		
	
	}

		
		
}
