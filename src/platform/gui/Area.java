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
	private Color color;
	private SDLGraphics graphicEngine;
	
	/*public Area(Graphics graphics) throws SDLException{
		
		graphicEngine = (SDLGraphics) graphics;
		
	}*/
	
		
	public Area(String filename, Graphics graphics) throws GUIException {
		
		graphicEngine = (SDLGraphics) graphics;
		
		try {
			surface= SDLImage.load(filename);
		} 
		catch (SDLException e) {
			e.printStackTrace();
			throw new GUIException("Unable to load image");
			
		}
		

	}

	
	public Area(Image image, Graphics graphics) throws GUIException {
		
	
		graphicEngine = (SDLGraphics) graphics;
		surface = (SDLSurface) image.getData();
		
		if(surface == null){
			throw new GUIException("Unable to create surface from image");
		}
				
				
	}
	
	
	public Area(SDLColor color, Graphics graphics) throws GUIException{

		int rmask,gmask,bmask,amask;
		
		
		this.graphicEngine = (SDLGraphics) graphics;
		
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
			graphicEngine.beginDraw();
			graphicEngine.drawSDLSurface(surface, surface.getRect(), graphicEngine.getTarget().getRect() );
			graphicEngine.endDraw();
		} 
		catch (SDLException e) {
			
			e.printStackTrace();
			throw new GUIException("Exception while drawing on target surface");
		}
		
	
	}

		
		
}
