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

	private SDLSurface target;
	private SDLGraphics graphicEngine;
	
	public Area(Graphics graphics) throws SDLException{
		
		graphicEngine = (SDLGraphics) graphics;
		
		target = SDLVideo.setVideoMode(Screen._screenWidth, Screen._screenHeight,0 ,Screen._screenflags	);
			
		graphicEngine.setTarget(target);
		
	}
	
	public Area(String filename, Graphics graphics) throws SDLException, GUIException {
		
		graphicEngine = (SDLGraphics) graphics;
		
		target = SDLVideo.setVideoMode(Screen._screenWidth, Screen._screenHeight,0 ,Screen._screenflags	);
		SDLSurface tmpSurface = SDLImage.load(filename);
		
		//TODO convert here an image into Surface format?
		graphicEngine.setTarget(target);
		
		//TODO create function taking surface to load
					
			graphicEngine.beginDraw();
			graphicEngine.drawSDLSurface(tmpSurface, tmpSurface.getRect(), target.getRect() );
			graphicEngine.endDraw();
			
		tmpSurface.freeSurface();
		
	}

	
	public Area(Image image, Graphics graphics) throws SDLException, GUIException {
		
		graphicEngine = (SDLGraphics) graphics;
		
		target = SDLVideo.setVideoMode(Screen._screenWidth, Screen._screenHeight,0 ,Screen._screenflags	);
		
		//TODO convert here an image into Surface format?
		
		graphicEngine.setTarget(target);
		
		graphicEngine.beginDraw();
		graphicEngine.drawImage(image, 0 , 0);
		graphicEngine.endDraw();
		
	}
	
	
	public Area(Color color, Graphics graphics) throws SDLException, GUIException{

		this.graphicEngine = (SDLGraphics) graphics;

		target = SDLVideo.setVideoMode(Screen._screenWidth, Screen._screenHeight,0 ,Screen._screenflags	);
		//SDLSurface tmpSurface = SDLImage.load(filename);
		
		graphicEngine.setTarget(target);
		graphicEngine.setColor(color);
		
		SDLRect tmpRectangle = target.getRect();
		
		graphicEngine.fillRectangle(new Rectangle(tmpRectangle.x, tmpRectangle.y, tmpRectangle.width, tmpRectangle.height));
		
		graphicEngine.endDraw();
		
	}
	
	
	public SDLSurface getTarget() {
		return target;
	}

	public void setTarget(SDLSurface target) {
		this.target = target;
	}

	public void refreshArea() throws SDLException{
		
		target.flip();
	}
	
	public void refreshTile(int tileIndex){
		
		
	}
	
}
