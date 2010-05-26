package platform.gfx;

import sdljava.SDLException;
import sdljava.video.SDLRect;
import sdljava.video.SDLSurface;
import sdljavax.guichan.gfx.Graphics;

public abstract class UnifiedGraphics extends Graphics{

	//SDLGraphics methods
	public void	drawSDLSurface(SDLSurface surface, SDLRect source, SDLRect destination) throws SDLException{
		throw new UnsupportedOperationException();
	}
	
	public SDLSurface getTarget(){	
		throw new UnsupportedOperationException();
	}
	
	public void	setTarget(SDLSurface target){
		throw new UnsupportedOperationException();
	}
	
	protected void	drawHLine(int x1, int y, int x2){
		throw new UnsupportedOperationException();
	}
	protected void	drawVLine(int x, int y1, int y2){
		throw new UnsupportedOperationException();
	}
	
	//OpenGlGraphics method
	public void setTargetPlane(int width, int height){
		throw new UnsupportedOperationException();
	}
	
}
