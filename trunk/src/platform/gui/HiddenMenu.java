package platform.gui;

/**
*  arm-gui-framework -Java GUI based on sdljava for omap5912 board
*  Copyright (C) 2010  Bartosz Kędra (bartosz.kedra@gmail.com)
* 
*  This library is free software; you can redistribute it and/or
*  modify it under the terms of the GNU Lesser General Public
*  License as published by the Free Software Foundation; either
*  version 3.0 of the License, or (at your option) any later version.
* 
*  This library is distributed in the hope that it will be useful,
*  but WITHOUT ANY WARRANTY; without even the implied warranty of
*  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
*  Lesser General Public License for more details.
* 
*  You should have received a copy of the GNU Lesser General Public
*  License along with this library; if not, write to the Free Software
*  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
*  USA
*
*/

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import platform.gfx.UnifiedGraphics;
import platform.sdl.SDLGraphics;
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

/**
 * Class representing menu (container for other widgets) that is hidden by default and can appear and then hide again
 * @author Bartosz Kędra
 * @author bartosz.kedra@gmail.com
 *
 */
public class HiddenMenu extends PlatformWidget implements MouseListener, UpdateListener, Maintainable {

	/**
	 * Slider button surface
	 */
	protected SDLSurface slider;
	
	/**
	 * Background surface
	 */
	protected SDLSurface background;
	
	/**
	 * Background surface color
	 */
	protected SDLColor color;
	
	/**
	 * Surface drawing object
	 */
	protected UnifiedGraphics hiddenMenuGraphics;

	/**
	 * 
	 */
	protected int transition = 0;
	
	/**
	 * Orientation of menu
	 */
	protected Direction direction;
	
	/**
	 * List of widgets in menu
	 */
	protected List<PlatformWidget> widgetList = new ArrayList<PlatformWidget>();
	
	/**
	 * Constructor
	 * @param clr
	 * 			background surface color
	 * @param dir
	 * 			menu orientation
	 * @throws SDLException
	 * @throws InterruptedException
	 */
	public HiddenMenu(SDLColor clr, Direction dir) throws SDLException, InterruptedException {
		direction = dir;
		slider = SDLImage.load("resource" + File.separator + "images" + File.separator + "slider.png");
		
		if (direction == Direction.SOUTH || direction == Direction.NORTH) {
			background = SDLVideo.createRGBSurface(SDLVideo.SDL_HWSURFACE, Screen._screenWidth, 
												   (int) (Screen._screenHeight * 0.25), 16, 0, 0, 0, 0);
		} else {
			background = SDLVideo.createRGBSurface(SDLVideo.SDL_HWSURFACE, (int) (Screen._screenWidth * 0.25),
												   Screen._screenHeight, 16, 0, 0, 0, 0);
		}
		color = clr;
		background.fillRect(background.mapRGB(color.getRed(), color.getGreen(), color.getBlue()));
		
		//TODO change pixel format here?
		slider = SDLGfx.rotozoomSurface(slider, 90* dir.ordinal(), 1, true);
	
		m_bVisible = false;
		
		setWidth(background.getWidth());
		setHeight(background.getHeight());
	
		hiddenMenuGraphics = new SDLGraphics();
		hiddenMenuGraphics.setTarget(background);
		
		addMouseListener(this);
	}	
	
	/**
	 * Adds widget on the position specified by offset from the (0,0) cell
	 * @param widget
	 * 			widget to add
	 * @param offset
	 * 			offset from the beginning
	 * @throws GUIException
	 */
	@Override
	public void add(PlatformWidget widget, int offset) throws GUIException {
		widgetList.add(widget);
		
		if (widget.getWidth() > getWidth() || widget.getHeight() > getHeight()) {
			adjustSize(widget);
		}
			
		if (direction == Direction.NORTH || direction == Direction.SOUTH) {
			widget.setPosition((widget.getWidth() + 10) * ( widgetList.size() -1 ) + 10,
							   (getHeight()-widget.getHeight())/2 );
		} else {
			widget.setPosition((getWidth() - widget.getWidth()) / 2,
							   (widget.getHeight() + 10) * ( widgetList.size() -1 ) + 10 );
		}
		widget.setUpdateListener(this);
	}

	/**
	 * Adjust size of menu when widget size exceeds those of menu
	 * @param greaterWidget
	 * 			widget with sizes greater than menu
	 */
	protected void adjustSize(PlatformWidget greaterWidget) {
		try {
			background.freeSurface();
				
			if (direction == Direction.NORTH || direction == Direction.SOUTH) {
				background = SDLVideo.createRGBSurface(SDLVideo.SDL_HWSURFACE, Screen._screenWidth,
													   greaterWidget.getHeight(), 16, 0, 0, 0, 0);
				setHeight(greaterWidget.getHeight());
			} else {
				background = SDLVideo.createRGBSurface(SDLVideo.SDL_HWSURFACE, greaterWidget.getWidth(),
													   Screen._screenHeight, 16, 0, 0, 0, 0);
				setWidth(greaterWidget.getWidth());
			}
			background.fillRect(background.mapRGB(color.getRed(), color.getGreen(), color.getBlue()));
			hiddenMenuGraphics.setTarget(background);
			putRegionToUpdate(new WidgetUpdate(this, new SDLRect( getX(), getY(), getWidth(), getHeight() )));
		} catch (SDLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Cleans any dynamically reserved memory areas in C style 
	 * @throws GUIException
	 */
	public void delete() throws GUIException {
		for (PlatformWidget theWidget : widgetList) {
			theWidget.delete();		
		}
		widgetList.clear();
		
		try {
			slider.freeSurface();
			background.freeSurface();
			super.delete();
		} catch (SDLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	/**
	 * Draws menu on the target surface 
	 * @param graphics
	 * 			used for drawing menu on target surface
	 * @throws GUIException
	 */
	@Override
	public void draw(UnifiedGraphics graphics) throws GUIException {
		if (! m_bVisible) {
			drawBorder(graphics);
			return;
		}
		hiddenMenuGraphics.beginDraw();		
		for (PlatformWidget widget : widgetList) {
			widget.draw(hiddenMenuGraphics);
		}
		hiddenMenuGraphics.endDraw();
		drawBorder(graphics);
	}
	
	/**
	 * Draws background
	 * @param graphics
	 * 			used for drawing background on target surface
	 * @throws GUIException
	 */
	@Override
	public void drawBorder(UnifiedGraphics graphics) throws GUIException {
			try {
				if (! m_bVisible) {
					graphics.drawSDLSurface(slider, slider.getRect(), graphics.getTarget().getRect(getX() + ((direction.ordinal() + 1) % 2) * 110,
											getY() + (direction.ordinal() % 2) * 60  ));
				} else {
					graphics.drawSDLSurface(background, hiddenMenuGraphics.getTarget().getRect(), 
											graphics.getTarget().getRect(getX(), getY()));
				}
			} catch (SDLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	}

	/**
	 * MouseListener implementation - calls the same method on the appropriate widget if menu is visible
	 * In case menu is invisible it appears in a while, menu can become hidden when user clicks on the menu surface 
	 * outside any widget that menu contains
	 */
	public void mouseClick(int x, int y, int button, int count) throws GUIException {
		try {
			if (m_bVisible) {
				m_bVisible = !m_bVisible;
				new TransitionEffectHandler(this, updateListener, direction, m_bVisible );
				
				for (Widget widget : widgetList) {
					
					if (widget instanceof MouseListener) {
						if( x >= widget.getX() + getX() && x<= widget.getX() + widget.getWidth() + getX() 
								&& y>= widget.getY() + getY() && y<= widget.getY() + widget.getHeight() + getY()){
							((MouseListener) widget).mouseClick(x, y, button, count);
							//return;
						}
					}
				}
							
				//TODO is that safe at all platforms? we 're waiting till TransitionHandler finishes his job
				Thread.sleep(300);
				putRegionToUpdate( new WidgetUpdate (this,new SDLRect(getX(),getY(),background.getWidth(), background.getHeight() ) ) );		
			} else {
				new TransitionEffectHandler(this, updateListener, direction, !m_bVisible);
				m_bVisible = !m_bVisible;
			}
		} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
	}
	
	/**
	 * Acknowledges mouse over menu
	 */
	public void mouseIn() throws GUIException {
		m_bHasMouse =  true;
		requestFocus();		
	}

	/**
	 * Not used
	 */
	public void mouseMotion(int x, int y) throws GUIException {
		// TODO Auto-generated method stub
	}

	/**
	 * Acknowledges mouse leaving menu
	 */
	public void mouseOut() {
		m_bHasMouse =  false;
		try {
			lostFocus();
		} catch (GUIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Not used
	 */
	public void mousePress(int x, int y, int button) throws GUIException {
		// TODO Auto-generated method stub	
	}

	/**
	 * Not used
	 */
	public void mouseRelease(int x, int y, int button) throws GUIException {
		// TODO Auto-generated method stub
	}

	/**
	 * Not used
	 */
	public void mouseWheelDown(int x, int y) throws GUIException {
		// TODO Auto-generated method stub
	}

	/**
	 * Not used
	 */
	public void mouseWheelUp(int x, int y) throws GUIException {
		// TODO Auto-generated method stub
	}


	/**
	 * Passes request for an update to the higher container
	 * @param updateInfo
	 * 			<code> WidgetUpdate </code> update information
	 * @throws InterruptedException
	 */
	public void putRegionToUpdate(WidgetUpdate updateInfo) throws InterruptedException {
		SDLRect region = updateInfo.getWidgetRegion();
		updateListener.putRegionToUpdate(new WidgetUpdate(this,new SDLRect(region.x + getX(), region.y + getY(), region.width, region.height)));
	}
	
	@Override
	public void setAlpha(int alphaIndex) {
		// TODO Auto-generated method stub
		//Nothing in here
	}
}
