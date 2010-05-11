package platform.gui;

import sdljava.SDLException;
import sdljava.video.SDLSurface;
import sdljavax.guichan.GUIException;
import sdljavax.guichan.gfx.Graphics;
import sdljavax.guichan.gfx.Image;
import sdljavax.guichan.widgets.Widget;

public class PlatformIcon extends Widget {

	private Image iconImage;
	
	public PlatformIcon(Image image){
		super();
		iconImage = image;
		
		setHeight(image.getHeight());
		setWidth(image.getWidth());
	}
	@Override
	public void draw(Graphics graphics) throws GUIException {
		graphics.drawImage(iconImage, getX(), getY());
	}

	@Override
	public void drawBorder(Graphics graphics) throws GUIException {
		// TODO Auto-generated method stub
		
	}
	
	public void setAlpha(int alphaIndex) throws SDLException{
		((SDLSurface)iconImage.getData()).setAlpha(Screen._alphaFlags, alphaIndex);
	}
	
	
	@Override
	public void delete() throws GUIException {
		// TODO Auto-generated method stub
		iconImage.delete();
		super.delete();
	}
	
	

}
