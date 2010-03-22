package platform.gui;

import sdljava.SDLException;
import sdljava.image.SDLImage;
import sdljava.video.SDLRect;
import sdljava.video.SDLSurface;
import sdljava.video.SDLVideo;
import sdljavax.guichan.GUIException;
import sdljavax.guichan.gfx.Color;
import sdljavax.guichan.gfx.Graphics;
import sdljavax.guichan.gfx.Image;
import sdljavax.guichan.gfx.Rectangle;
import sdljavax.guichan.sdl.SDLGraphics;

public class Area {

	private SDLSurface surface;
	private Image image;
	private Color color;
	private SDLGraphics graphicEngine;
	private Type type;
	
	/*public Area(Graphics graphics) throws SDLException{
		
		graphicEngine = (SDLGraphics) graphics;
		
	}*/
	
	public Area(String filename, Graphics graphics) throws SDLException, GUIException {
		
		graphicEngine = (SDLGraphics) graphics;
		surface= SDLImage.load(filename);
		type = Type.SURFACE;

	}

	
	public Area(Image image, Graphics graphics) throws SDLException, GUIException {
		
		graphicEngine = (SDLGraphics) graphics;
		this.image = image;
		type = Type.IMAGE;
		//TODO convert here an image into Surface format?
		
				
	}
	
	
	public Area(Color color, Graphics graphics) throws SDLException, GUIException{

		this.graphicEngine = (SDLGraphics) graphics;
		this.color = color;
		type = Type.COLOR;
		
	}
	
	public void refreshArea() throws GUIException, SDLException{
		
		switch(type){
		
		case SURFACE: drawSurface();
			break;
			
		case IMAGE: drawImage();
			break;
			
		case COLOR: drawRectangle();
			break;
			
		default: 	//should never happen
			break;
			
			
		}
		
	}
	
	public SDLSurface getSurface() {
		return surface;
	}

	public void setSurface(SDLSurface surface) {
		this.surface = surface;
	}

	//TODO refreshing function depending on the tile changed
	
	public void refreshTile(int tileIndex){
		
		
	}
	
	private void drawSurface() throws GUIException, SDLException{
		
		//TODO convert here an image into Surface format?
		graphicEngine.beginDraw();
		graphicEngine.drawSDLSurface(surface, surface.getRect(), graphicEngine.getTarget().getRect() );
		graphicEngine.endDraw();
	
	}

	private void drawRectangle() throws GUIException{
		
		graphicEngine.setColor(color);
		
		SDLRect tmpRectangle = graphicEngine.getTarget().getRect();
		
		graphicEngine.beginDraw();
		graphicEngine.fillRectangle(new Rectangle(tmpRectangle.x, tmpRectangle.y, tmpRectangle.width, tmpRectangle.height));
		graphicEngine.endDraw();
		
	}
	
	private void drawImage() throws GUIException{
		
		//TODO convert here an image into Surface format?
		graphicEngine.beginDraw();
		graphicEngine.drawImage(image, 0 , 0);
		graphicEngine.endDraw();
		
	}
	
	private enum Type { SURFACE, IMAGE, COLOR }
	
	
}
