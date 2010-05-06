package platform.gui;

import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import platform.gui.PlayButton.ButtonStates;
import platform.util.UpdateListener;
import platform.util.WidgetUpdate;
import sdljava.SDLException;
import sdljava.image.SDLImage;
import sdljava.video.SDLColor;
import sdljava.video.SDLRect;
import sdljava.video.SDLSurface;
import sdljava.video.SDLVideo;
import sdljavax.gfx.SDLGfx;
import sdljavax.guichan.GUIException;
import sdljavax.guichan.evt.MouseListener;
import sdljavax.guichan.gfx.Graphics;
import sdljavax.guichan.sdl.SDLGraphics;
import sdljavax.guichan.widgets.BasicContainer;
import sdljavax.guichan.widgets.Widget;


public class HiddenMenu extends BasicContainer implements MouseListener,UpdateListener{

	protected SDLSurface slider;
	protected SDLSurface background;
	protected boolean sliderVisible=true;
	protected Direction direction;
	protected List<PlatformIcon> iconList = new ArrayList<PlatformIcon>();
	
	public HiddenMenu(SDLColor clr, Direction dir) throws SDLException{
		
		direction = dir;
		slider = SDLImage.load("resource" + File.separator + "images" + File.separator + "slider.png");
		
		if( direction == Direction.SOUTH || direction == Direction.NORTH){
			background = SDLVideo.createRGBSurface(SDLVideo.SDL_HWSURFACE, Screen._screenWidth,(int) (Screen._screenHeight * 0.25), 16, 0, 0, 0, 0);
		}
		else {
			background = SDLVideo.createRGBSurface(SDLVideo.SDL_HWSURFACE, (int) (Screen._screenWidth * 0.25), Screen._screenHeight , 16, 0, 0, 0, 0);
		}
		
		background.fillRect(background.mapRGB(clr.getRed(), clr.getGreen(), clr.getBlue()));
		
		//TODO change pixel format here?
		slider = SDLGfx.rotozoomSurface(slider, 90* dir.ordinal(), 1, true);
		
		addMouseListener(this);
	}	
	
	public void addIcon(PlatformIcon icon){
		
		iconList.add(icon);
		
		if(direction == Direction.NORTH || direction == Direction.SOUTH){
			icon.setPosition(getX() + 20 + 80 * ( iconList.size() -1 ), getY() + 2 );
		}
		else{
			icon.setPosition(getX() +2, getY() + 20 + 60 * ( iconList.size() -1 )  );
			
		}
	}
		
	@Override
	protected void announceDeath(Widget arg0) throws GUIException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Dimension getDrawSize(Widget arg0) throws GUIException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void moveToBottom(Widget arg0) throws GUIException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void moveToTop(Widget arg0) throws GUIException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void draw(Graphics graphics) throws GUIException {
			
		drawBorder(graphics);
		if(sliderVisible) return;
				
		for ( PlatformIcon icon : iconList){
			
			icon.draw(graphics);
		}
		
	}


	@Override
	public void drawBorder(Graphics graphics) throws GUIException {
	
		if( graphics instanceof SDLGraphics){
			try {
				if(sliderVisible){
					((SDLGraphics)graphics).drawSDLSurface(slider, slider.getRect(), ((SDLGraphics) graphics).getTarget().getRect());
				}
				else{
					((SDLGraphics)graphics).drawSDLSurface(background, background.getRect(), ((SDLGraphics) graphics).getTarget().getRect());
				}
			} catch (SDLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	public void mouseClick(int arg0, int arg1, int arg2, int arg3)
			throws GUIException {
		
		sliderVisible = !sliderVisible;
		
		try {
			((Area)getParent()).putRegionToUpdate(new WidgetUpdate(this,new SDLRect(getX(),getY(),background.getWidth(), background.getHeight())));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void mouseIn() throws GUIException {
		m_bHasMouse =  true;
		requestFocus();		
	}


	public void mouseMotion(int arg0, int arg1) throws GUIException {
		// TODO Auto-generated method stub
		
	}


	public void mouseOut() {
		m_bHasMouse =  false;
		try {
			lostFocus();
		} catch (GUIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	public void mousePress(int arg0, int arg1, int arg2) throws GUIException {
		
				
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


	public boolean putRegionToUpdate(WidgetUpdate updateInfo)
			throws InterruptedException {
		// TODO Auto-generated method stub
		return ((UpdateListener)getParent()).putRegionToUpdate(updateInfo);
	}
	
	public enum Direction{
		NORTH, WEST, SOUTH, EAST
	}
}
