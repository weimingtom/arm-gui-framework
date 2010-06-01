package platform.gui;

import platform.gfx.UnifiedGraphics;
import platform.util.WidgetUpdate;
import sdljava.SDLException;
import sdljava.video.SDLRect;
import sdljava.video.SDLSurface;
import sdljavax.guichan.GUIException;
import sdljavax.guichan.evt.MouseListener;
import sdljavax.guichan.gfx.Image;

public class PlatformIcon extends PlatformWidget implements MouseListener{

	private SDLSurface iconImage;
	private boolean clicked = false;
	
	public PlatformIcon(Image image) throws GUIException{
		super();
		iconImage =(SDLSurface)image.getData();
		
		setHeight(image.getHeight());
		setWidth(image.getWidth());
		
		addMouseListener(this);
		
		
	}
	@Override
	public void draw(UnifiedGraphics graphics) throws GUIException {
		try {
			graphics.drawSDLSurface(iconImage, iconImage.getRect(), graphics.getTarget().getRect(getX(), getY()));
		} catch (SDLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//graphics.drawImage(iconImage, getX(), getY());
	}

	@Override
	public void drawBorder(UnifiedGraphics graphics) throws GUIException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setAlpha(int alphaIndex) {
		try {
			iconImage.setAlpha(Screen._alphaFlags, alphaIndex);
			updateListener.putRegionToUpdate(new WidgetUpdate(this, new SDLRect(getX(), getY(), getWidth(), getHeight())));
		} catch (SDLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	@Override
	public void delete() throws GUIException {
		// TODO Auto-generated method stub
		//iconImage.delete();
		try {
			iconImage.freeSurface();
		} catch (SDLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		super.delete();
	}
	public void mouseClick(int arg0, int y, int button, int count)
			throws GUIException {
			setAlpha((clicked == false) ? 100 : 255 );
			clicked = !clicked;
		
	}
	
	public void mouseIn() throws GUIException {
		
	}
	public void mouseMotion(int x, int y) throws GUIException {
		// TODO Auto-generated method stub
		
	}
	public void mouseOut() {
		// TODO Auto-generated method stub
		
	}
	public void mousePress(int x, int y, int button) throws GUIException {
		// TODO Auto-generated method stub
		
	}
	public void mouseRelease(int x, int y, int button) throws GUIException {
		// TODO Auto-generated method stub
		
	}
	public void mouseWheelDown(int x, int y) throws GUIException {
		// TODO Auto-generated method stub
		
	}
	public void mouseWheelUp(int x, int y) throws GUIException {
		// TODO Auto-generated method stub
		
	}
	
	

}
