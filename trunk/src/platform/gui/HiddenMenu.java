package platform.gui;

import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import platform.thread.TransitionEffectHandler;
import platform.util.Direction;
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
	protected int transition=0;
	protected Direction direction;
	protected List<PlatformIcon> iconList = new ArrayList<PlatformIcon>();
	
	public HiddenMenu(SDLColor clr, Direction dir) throws SDLException, InterruptedException{
		
		direction = dir;
		slider = SDLImage.load("resource" + File.separator + "images" + File.separator + "slider.png");
		
		if( direction == Direction.SOUTH || direction == Direction.NORTH){
			background = SDLVideo.createRGBSurface(SDLVideo.SDL_HWSURFACE, Screen._screenWidth, (int)(Screen._screenHeight * 0.25) , 16, 0, 0, 0, 0);
		}
		else {
			background = SDLVideo.createRGBSurface(SDLVideo.SDL_HWSURFACE, (int)(Screen._screenWidth * 0.25) , Screen._screenHeight , 16, 0, 0, 0, 0);
		}
		
		background.fillRect(background.mapRGB(clr.getRed(), clr.getGreen(), clr.getBlue()));
		
		//TODO change pixel format here?
		slider = SDLGfx.rotozoomSurface(slider, 90* dir.ordinal(), 1, true);
	
		m_bVisible = false;
		
		setWidth(background.getWidth());
		setHeight(background.getHeight());
		addMouseListener(this);
					
	}	
	
	public void addIcon(PlatformIcon icon){
		iconList.add(icon);
				
		if(direction == Direction.NORTH || direction == Direction.SOUTH){
						
			icon.setPosition(getX() + (icon.getWidth() + 10) * ( iconList.size() -1 ) + 10, getY() + 2 );
		}
		else{
			icon.setPosition(getX() +2, getY() +  (icon.getHeight() + 10) * ( iconList.size() -1 ) + 10 );
			
		}
		icon.setParent(this);
	}
		
	@Override
	protected void announceDeath(Widget widget) throws GUIException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Dimension getDrawSize(Widget widget) throws GUIException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void moveToBottom(Widget widget) throws GUIException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void moveToTop(Widget widget) throws GUIException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void draw(Graphics graphics) throws GUIException {
	
		drawBorder(graphics);
		if(!m_bVisible) return;
				
		for ( PlatformIcon icon : iconList){
			icon.draw(graphics);
		}
		
	}

	
	@Override
	public void drawBorder(Graphics graphics) throws GUIException {
	
		
		if( graphics instanceof SDLGraphics){
			try {
				
				if(!m_bVisible ){
					((SDLGraphics)graphics).drawSDLSurface(slider, slider.getRect(), ((SDLGraphics) graphics).getTarget().getRect(getX()+ ((direction.ordinal()+1) % 2) * 110, getY() + (direction.ordinal() %2) * 60  ));
				}
				else{
					((SDLGraphics)graphics).drawSDLSurface(background, background.getRect(), ((SDLGraphics) graphics).getTarget().getRect(getX(),getY()));
				}
			} catch (SDLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
		}
		
	}
	
	public void mouseClick(int x, int y, int button, int count)
			throws GUIException {
		try {
			
			
			if(m_bVisible){
				
				new TransitionEffectHandler(getParent(), background, direction,!m_bVisible );
			
				//TODO is that safe at all platforms?
				Thread.sleep(300);
				m_bVisible = !m_bVisible;
				slider = SDLGfx.rotozoomSurface(slider, 180, 1, true);
			
				putRegionToUpdate( new WidgetUpdate (this,new SDLRect(getX(),getY(),background.getWidth(), background.getHeight() ) ) );		
			}
			
			else {
				new TransitionEffectHandler(this, background, direction, !m_bVisible);
				m_bVisible = !m_bVisible;
				slider = SDLGfx.rotozoomSurface(slider, 180, 1, true);
			}
			
			
			
		}
		
		catch (SDLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
	}


	public void mouseIn() throws GUIException {
		m_bHasMouse =  true;
		requestFocus();		
	}


	public void mouseMotion(int x, int y) throws GUIException {
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


	public void mousePress(int x, int y, int button) throws GUIException {
		
				
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


	public boolean putRegionToUpdate(WidgetUpdate updateInfo)
			throws InterruptedException {
		// TODO Auto-generated method stub
		return ((UpdateListener)getParent()).putRegionToUpdate(updateInfo);
	}
	
}