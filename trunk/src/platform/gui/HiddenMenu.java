package platform.gui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import platform.gfx.UnifiedGraphics;
import platform.thread.TransitionEffectHandler;
import platform.util.Direction;
import platform.util.Maintainable;
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
import sdljavax.guichan.widgets.Widget;



public class HiddenMenu extends PlatformWidget implements MouseListener,UpdateListener, Maintainable{

	protected SDLSurface slider;
	protected SDLSurface background;
	protected int transition=0;
	protected Direction direction;
	protected List<PlatformWidget> widgetList = new ArrayList<PlatformWidget>();
	
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
	
	@Override
	public void add(PlatformWidget widget, int offset) throws GUIException{
		widgetList.add(widget);
				
		if(direction == Direction.NORTH || direction == Direction.SOUTH){
						
			widget.setPosition(getX() + (widget.getWidth() + 10) * ( widgetList.size() -1 ) + 10, getY() + 2 );
		}
		else{
			widget.setPosition(getX() +2, getY() +  (widget.getHeight() + 10) * ( widgetList.size() -1 ) + 10 );
			
		}
		widget.setUpdateListener(this);
		if(widget.getWidth() > getWidth() || widget.getHeight() > getHeight()){
			adjustSize(widget);
		}
	}

	@Override
	public void draw(UnifiedGraphics graphics) throws GUIException {
	
		drawBorder(graphics);
		if(!m_bVisible) return;
				
		for ( PlatformWidget widget : widgetList){
			widget.draw(graphics);
		}
		
	}
	
	@Override
	public void drawBorder(UnifiedGraphics graphics) throws GUIException {
	
			try {
				
				if(!m_bVisible ){
					graphics.drawSDLSurface(slider, slider.getRect(), graphics.getTarget().getRect(getX()+ ((direction.ordinal()+1) % 2) * 110, getY() + (direction.ordinal() %2) * 60  ));
				}
				else{
					graphics.drawSDLSurface(background, background.getRect(), graphics.getTarget().getRect(getX(),getY()));
				}
			} catch (SDLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	}
	
	public void delete() throws GUIException{
		
		for( PlatformWidget theWidget: widgetList){
			theWidget.delete();		
		}
		
		try {
			slider.freeSurface();
			background.freeSurface();
			super.delete();
		} catch (SDLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	public void mouseClick(int x, int y, int button, int count)
			throws GUIException {
		try {

			if(m_bVisible){
				for( Widget widget : widgetList){
					
					if(widget instanceof MouseListener){
						if( x >= widget.getX() && x<= widget.getX() + widget.getWidth() && y>= widget.getY() && y<= widget.getY() + widget.getHeight()){
							((MouseListener)widget).mouseClick(x, y, button, count);
							return;
						}
					}
				}
				m_bVisible = !m_bVisible;
				new TransitionEffectHandler(this,updateListener, direction, m_bVisible );
			
				//TODO is that safe at all platforms? we 're waiting till TransitionHandler finishes his job
				Thread.sleep(300);
				putRegionToUpdate( new WidgetUpdate (this,new SDLRect(getX(),getY(),background.getWidth(), background.getHeight() ) ) );		
			}
			
			else {
				new TransitionEffectHandler(this, updateListener, direction, !m_bVisible);
				m_bVisible = !m_bVisible;
			}
		}catch (InterruptedException e) {
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


	public void putRegionToUpdate(WidgetUpdate updateInfo)
			throws InterruptedException {
		// TODO Auto-generated method stub
		updateListener.putRegionToUpdate(updateInfo);
	}

	protected void adjustSize(PlatformWidget greaterWidget) {
		
		try {
			background.freeSurface();
				
			if(direction == Direction.NORTH || direction == Direction.SOUTH){
				background = SDLVideo.createRGBSurface(SDLVideo.SDL_HWSURFACE, Screen._screenWidth, greaterWidget.getHeight() , 16, 0, 0, 0, 0);
				setHeight(greaterWidget.getHeight());
			}
			else{
				background = SDLVideo.createRGBSurface(SDLVideo.SDL_HWSURFACE, greaterWidget.getWidth() , Screen._screenHeight , 16, 0, 0, 0, 0);
				setWidth(greaterWidget.getWidth());
			}
			putRegionToUpdate(new WidgetUpdate(this, new SDLRect( getX(), getY(), getWidth(), getHeight())));
		
		} catch (SDLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}