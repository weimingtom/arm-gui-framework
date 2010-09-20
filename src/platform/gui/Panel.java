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
import platform.util.UpdateListener;
import platform.util.WidgetUpdate;
import sdljava.SDLException;
import sdljava.video.SDLRect;
import sdljava.video.SDLSurface;
import sdljavax.guichan.GUIException;
import sdljavax.guichan.evt.FocusHandler;
import sdljavax.guichan.evt.MouseListener;
import sdljavax.guichan.gfx.Image;
import sdljavax.guichan.widgets.Widget;

/**
 * Class representing panel ( container for other widgets)
 * @author Bartosz Kędra
 * @author bartosz.kedra@gmail.com
 *
 */
public class Panel extends PlatformWidget implements MouseListener, UpdateListener {

	/**
	 * Frame - background for panel
	 */
	protected SDLSurface frame;
	
	protected SDLSurface originalSurface;
	/**
	 * List of panel widgets
	 */
	protected List<PlatformWidget> widgetList = new ArrayList<PlatformWidget>();
	
	/**
	 * x panel format
	 */
	protected Integer xFormat;
	
	/**
	 * y panel format
	 */
	protected Integer yFormat;
	
	/**
	 * Surface drawing object
	 */
	protected UnifiedGraphics panelGraphics;
	
	/**
	 * Flag indicating whether transparency is set
	 */
	protected boolean alphaSet=false;
	
	/**
	 * Transparency coefficient
	 */
	protected int alpha;
	
	/**
	 * Shift added to every widget position for correct positioning
	 */
	private final int horizontalPixelShift = 3;
	
	/**
	 * Shift added to every widget for correct positioning
	 */
	private final int verticalPixelShift = 10;
	
	/**
	 * Constructor 
	 * @param xFormat
	 * 			panel x format
	 * @param yFormat
	 * 			panel y format
	 * @throws GUIException
	 * @throws SDLException
	 */
	public Panel(int xFormat, int yFormat) throws GUIException, SDLException {		
		super();
		
		this.xFormat = new Integer( ( xFormat > 4 ) ? 4 : xFormat );
		this.yFormat = new Integer( ( yFormat > 3 ) ? 3 : yFormat );
		
		String commonFramesName="_Widget_Frame_Landscape.png";
		String frameName = "resource" + File.separator+ "PNG" + File.separator + this.xFormat.toString() + "x" + this.yFormat.toString() + commonFramesName;
		
		frame = (SDLSurface) (new Image(frameName)).getData();
		originalSurface = (SDLSurface) (new Image(frameName)).getData();
		setHeight(frame.getHeight());
		setWidth(frame.getWidth());
		
		panelGraphics = new SDLGraphics();
		//long colorKey = SDLVideo.mapRGB(frame.getFormat(), 0, 0, 0);
		//frame.setColorKey(SDLVideo.SDL_SRCCOLORKEY, colorKey);
		panelGraphics.setTarget(frame);
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
		if (widget == null) {
			throw new GUIException("Widget doesn't exist");
		}  else if ( offset > xFormat.intValue() * yFormat.intValue() - 1 ){
			throw new GUIException("Offset out of range for the panel.");
		}
		int verticalShift = (offset % xFormat.intValue()) * ( getWidth() / xFormat.intValue() ) + verticalPixelShift;  
		int horizontalShift = (offset / xFormat.intValue()) * ( getHeight() / yFormat.intValue()) + (getHeight()- widget.getHeight()) / 2;
		
		//int horizontalShift = getX() + (offset % xFormat.intValue()) * ( getWidth() / xFormat.intValue() ) + horizontalPixelShift;  
		//int verticalShift = getY() + (offset / xFormat.intValue()) * ( getHeight() / yFormat.intValue()) + verticalPixelShift;
		
		widgetList.add(widget);
		
		widget.setPosition(verticalShift, horizontalShift);
		widget.setUpdateListener(this);
		
		if (this.getFocusHandler() != null) {	
			widget.setFocusHandler(getFocusHandler());
			widget.setFocusable(true);
			widget.requestFocus();
		}
		
		try {
			updateListener.putRegionToUpdate( new WidgetUpdate(this, new SDLRect(widget.getX() + getX(), widget.getY() + getY(),
																  widget.getWidth(), widget.getHeight() )));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Cleans any dynamically reserved memory areas in C style 
	 * @throws GUIException
	 */
	@Override
	public void delete() throws GUIException {
		// TODO Auto-generated method stub
		try {
			frame.freeSurface();
		} catch (SDLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (Widget widget : widgetList) {
			widget.delete();
		}
		widgetList.clear();
		super.delete();
	}
	
	/**
	 * Draws menu on the surface 
	 * @param graphics
	 * 			used for drawing menu on target surface
	 * @throws GUIException
	 */
	@Override
	public void draw(UnifiedGraphics graphics) throws GUIException {
		if (alphaSet) {
			try {
				frame.setAlpha(Screen._alphaFlags, alpha);
			} catch (SDLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		panelGraphics.beginDraw();
		try {
			panelGraphics.drawSDLSurface(originalSurface, panelGraphics.getTarget().getRect(), panelGraphics.getTarget().getRect());
		} catch (SDLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (PlatformWidget widget : widgetList) {
			widget.draw(panelGraphics);
		}
		panelGraphics.endDraw();
		drawBorder(graphics);
	}

	/**
	 * Draws background of the panel
	 * @param graphics
	 * 			used for drawing background on target surface
	 * @throws GUIException
	 */
	@Override
	public void drawBorder(UnifiedGraphics graphics) throws GUIException {
		try {
			graphics.drawSDLSurface(originalSurface, panelGraphics.getTarget().getRect(), graphics.getTarget().getRect(getX(), getY()));
			graphics.drawSDLSurface(frame, panelGraphics.getTarget().getRect(), graphics.getTarget().getRect(getX(), getY()));
			
		} catch (SDLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
		}
		//graphics.drawImage(frame, getX(), getY() );
	}
	
	/**
	 * MouseListener implementation - calls the same method on the appropriate widget
	 */
	public void mouseClick(int x, int y, int b, int ts) throws GUIException {
		for (Widget widget : widgetList) {
			if (widget instanceof MouseListener) {
				if (x >= widget.getX() + getX() && x<= widget.getX() + widget.getWidth() + getX() 
					&& y>= widget.getY() + getY() && y<= widget.getY() + widget.getHeight() +getY() ){
					((MouseListener) widget).mouseClick(x, y, b, ts);
				}
			}
		}
	}

	/**
	 * Not used
	 */
	public void mouseIn() throws GUIException {
		// TODO Auto-generated method stub
	}
	
	/**
	 * Not used
	 */
	public void mouseMotion(int arg0, int arg1) throws GUIException {
		// TODO Auto-generated method stub
	}

	/**
	 * Not used
	 */
	public void mouseOut() {
		// TODO Auto-generated method stub
	}
	
	/**
	 * MouseListener implementation - calls the same method on the appropriate widget
	 */
	public void mousePress(int x, int y, int b) throws GUIException {
		for (Widget widget : widgetList) {
			if (widget instanceof MouseListener) {
				if( x >= widget.getX() + getX() && x<= widget.getX() + widget.getWidth() + getX() 
					&& y>= widget.getY() + getY() && y<= widget.getY() + widget.getHeight() + getY()){
					((MouseListener) widget).mousePress(x, y, b);
				}
			}
		}
	}

	/**
	 * MouseListener implementation - calls the same method on the appropriate widget
	 */
	public void mouseRelease(int x, int y, int b) throws GUIException {
		for (Widget widget : widgetList) {
			if (widget instanceof MouseListener) {
				if( x >= widget.getX() + getX() && x<= widget.getX() + widget.getWidth()+ getX() 
					&& y>= widget.getY() + getY()  && y<= widget.getY() + widget.getHeight()+ getY()){
					((MouseListener) widget).mouseRelease(x, y, b);
				}
			}
		}
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
		updateListener.putRegionToUpdate(new WidgetUpdate(this, new SDLRect(region.x + getX(), region.y + getY(), 
																			region.width, region.height) ));
		//updateListener.putRegionToUpdate(updateInfo);
	}

	/**
	 * Removes widget from panel
	 * @param widget
	 * 			widget to be removed
	 */
	@Override
	public void remove(PlatformWidget widget) throws GUIException {
		for (Widget theWidget : widgetList) {
			if (widget.equals(theWidget)) {
				try {
					
					putRegionToUpdate(new WidgetUpdate(this, new SDLRect(widget.getX() + getX(), widget.getY() + getY(),
																		 widget.getWidth(), widget.getHeight() )));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				theWidget.delete();
				widgetList.remove(theWidget);
				return;
			}
		}
		throw new GUIException("No such widget in this panel");
	}

	/**
	 * Set transparency coefficient for working surface
	 * @parem alphaIndex
	 * 			transparency index to be set - 0 is totally transparent, 255 means no transparency 
	 */
	@Override
	public void setAlpha(int alphaIndex) {
		if (alphaIndex < 255) {
			alphaSet = true;
			alpha = alphaIndex;
		} else {
			alphaSet = false;
			alpha = 255;
		}
		try {
			frame.setAlpha(Screen._alphaFlags, alpha);
			updateListener.putRegionToUpdate(new WidgetUpdate(this, new SDLRect(getX(), getY(), 
															  getWidth(), getHeight() )));
		} catch (SDLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Set focus handler for panel
	 * @param focusHandler
	 * 			focus handler for panel
	 * @throws GUIException
	 */
	public void setWidgetsFocusHandler(FocusHandler focusHandler) throws GUIException {
		for (Widget widget : widgetList) {
			widget.setFocusHandler(focusHandler);
			widget.setFocusable(true);
			widget.requestFocus();
		}
	}
}
