package platform.gui;

import sdljava.SDLException;
import sdljava.video.SDLSurface;
import sdljavax.guichan.GUIException;
import sdljavax.guichan.evt.MouseListener;
import sdljavax.guichan.gfx.Graphics;
import sdljavax.guichan.gfx.Image;
import sdljavax.guichan.sdl.SDLGraphics;
import sdljavax.guichan.widgets.Widget;

public class PlatformIcon extends Widget implements MouseListener{

	private Image iconImage;
	
	public PlatformIcon(Image image){
		super();
		iconImage = image;
	}
	@Override
	public void draw(Graphics arg0) throws GUIException {
	
		arg0.drawImage(iconImage, getX(), getY());
							
	}

	@Override
	public void drawBorder(Graphics arg0) throws GUIException {
		// TODO Auto-generated method stub
		
	}

	public void mouseClick(int arg0, int arg1, int arg2, int arg3)
			throws GUIException {
		// TODO Auto-generated method stub
		
	}

	public void mouseIn() throws GUIException {
		// TODO Auto-generated method stub
		
	}

	public void mouseMotion(int arg0, int arg1) throws GUIException {
		// TODO Auto-generated method stub
		
	}

	public void mouseOut() {
		// TODO Auto-generated method stub
		
	}

	public void mousePress(int arg0, int arg1, int arg2) throws GUIException {
		// TODO Auto-generated method stub
		
	}

	public void mouseRelease(int arg0, int arg1, int arg2) throws GUIException {
		// TODO Auto-generated method stub
		
	}

	public void mouseWheelDown(int arg0, int arg1) throws GUIException {
		// TODO Auto-generated method stub
		
	}

	public void mouseWheelUp(int arg0, int arg1) throws GUIException {
		// TODO Auto-generated method stub
		
	}

}
