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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import platform.gfx.UnifiedGraphics;
import platform.sdl.SDLGraphics;
import platform.util.Maintainable;
import platform.util.UpdateListener;
import platform.util.WidgetUpdate;
import sdljava.SDLException;
import sdljava.image.SDLImage;
import sdljava.video.SDLColor;
import sdljava.video.SDLRect;
import sdljava.video.SDLSurface;
import sdljava.video.SDLVideo;
import sdljavax.guichan.GUIException;
import sdljavax.guichan.evt.FocusHandler;
import sdljavax.guichan.gfx.Image;

/**
 * Widgets container, represents single layer(surface) of screen that we can draw to
 * @author Bartosz Kędra
 * @author bartosz.kedra@gmail.com
 *
 */
public class Area extends PlatformWidget implements UpdateListener {

	/**
	 * Thread that is responsible for refreshing the Area when necessary 
	 */
	private class AreaUpdateHandler extends Thread {
		
		/**
		 * Flag set to true when update is necessary
		 */
		boolean needsUpdate = false;
		
		/**
		 * Constructor that also starts the thread
		 */
		public AreaUpdateHandler() {
			super ();
			start();
		}
		
		/**
		 * Thread task function
		 * waits until there is request pending 
		 * and updates the only part of the screen that is necessary
		 */
		public void run() {
			//TODO solve the problem of whole screen being changed?
			try {
				while (Screen.getScreen().isRunning()) {
					WidgetUpdate widgetToUpdate;
						while (widgetUpdateInfo.size() > 0) {
							surfaceGraphics.beginDraw();
							
								if ((widgetToUpdate = widgetUpdateInfo.poll(5, TimeUnit.MILLISECONDS)) != null) {
									
									drawOriginal(widgetToUpdate.getWidgetRegion());
									
									drawWidgets(widgetToUpdate.getWidgetRegion(), widgetToUpdate.getWidget());
									widgetToUpdate.getWidget().draw(surfaceGraphics);
									
									draw(widgetToUpdate.getWidgetRegion());
									//draw(surfaceGraphics);
									
									needsUpdate = true;
								}
							
							surfaceGraphics.endDraw();
						}
						if (needsUpdate == true) {
							needsUpdate = false;
							
							Screen.getScreen().refresh();
						}
						Thread.sleep(20);
				}
				
			} catch (SDLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (GUIException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * grid dimensions table
	 */
	protected int grid[];
	
	/**
	 * width of the cell on the screen
	 */
	protected int xCellDimension;
	
	/**
	 * height of the cell on the screen
	 */
	protected int yCellDimension;
	
	/**
	 * working surface where everything will be drawn
	 */
	protected SDLSurface surface;
	
	/**
	 * original surface, the one that is loaded on object creation
	 * that surface is preserved for drawing original background
	 */
	protected SDLSurface originalSurface;
	
	/**
	 * object capable of drawing SDLSurfaces
	 * @see UnifiedGrapics
	 */
	protected UnifiedGraphics surfaceGraphics;
	
	/**
	 * keeps track of widgets focus
	 */
	protected FocusHandler focusHandler = new FocusHandler();
	
	/**
	 * An association between widgets on the Area and cell numbers occupied  
	 */
	protected Map<PlatformWidget, Set<Integer> > widgetMap = new HashMap<PlatformWidget , Set<Integer> >();
	
	/**
	 * A 'thread-safe' queue that stores the requests for screen update 
	 */
	protected BlockingQueue<WidgetUpdate> widgetUpdateInfo = new LinkedBlockingQueue<WidgetUpdate>();
	
	/**
	 * Default x grid dimension 
	 */
	private final int defXGrid = 5;
	
	/**
	 * Default Y grid dimension
	 */
	private final int defYGrid = 4;
	
	/**
	 * Constructor 
	 * @param image
	 * 			surface background <code> Image </code>
	 * @param args
	 * 			grid dimension arguments, it may be specified no arguments (default grid dimension), 
	 * one argument ( X grid value) or two arguments ( X, Y value)
	 * @throws GUIException
	 */
	public Area(Image image, int... args) throws GUIException {	
		originalSurface = (SDLSurface) image.getData();
		surface = (SDLSurface) image.getData();
				
		if (surface == null) {
			throw new GUIException("Unable to create surface from image");
		}
				
		surfaceGraphics = new SDLGraphics();
		surfaceGraphics.setTarget(surface);		
		setGrid(args);
		
		startAreaUpdateHandler();
		widgetUpdateInfo.add(new WidgetUpdate(this , new SDLRect(0, 0, getWidth(), getHeight() )));
	}
	
	/**
	 * Constructor
	 * @param color
	 * 			background color
	 * @param args
	 * 			grid dimension arguments, it may be specified no arguments (default grid dimension), 
	 * one argument ( X grid value) or two arguments ( X, Y value)
	 * @throws GUIException
	 */
	public Area(SDLColor color, int... args) throws GUIException {
		try {	
			originalSurface = SDLVideo.createRGBSurface(SDLVideo.SDL_HWSURFACE, Screen._screenWidth, 
														Screen._screenHeight, 16 , 0, 0, 0, 0);
			//surface.fillRect( surface.mapRGBA(color.getRed(),color.getGreen(), color.getBlue(), 255));
			originalSurface.fillRect(originalSurface.mapRGBA(color.getRed(),color.getGreen(),color.getBlue(), 255));
			
			surface = SDLVideo.createRGBSurface(SDLVideo.SDL_HWSURFACE, Screen._screenWidth, 
											   Screen._screenHeight, 16 , 0, 0, 0, 0);
			surface.fillRect(originalSurface.mapRGB(color.getRed(),color.getGreen(),color.getBlue()));
			
			surfaceGraphics = new SDLGraphics();
			surfaceGraphics.setTarget(surface);
			setGrid(args);
			
			setWidth(surface.getWidth());
			setHeight(surface.getHeight());
			
			startAreaUpdateHandler();
			widgetUpdateInfo.add(new WidgetUpdate(this, new SDLRect(0, 0, getWidth(), getHeight() )));
		} catch (SDLException e) {
			e.printStackTrace();
			throw new GUIException("Unable to create surface from color");
		}	
	}
	
	/**
	 * Constructor
	 * @param filename
	 * 			surface image path
	 * @param args
	 * 			grid dimension arguments, it may be specified no arguments (default grid dimension), 
	 * one argument ( X grid value) or two arguments ( X, Y value)
	 * @throws GUIException
	 */
	public Area(String filename, int... args ) throws GUIException {
		try {
			originalSurface = SDLImage.load(filename);
			surface = SDLImage.load(filename);
			
			surfaceGraphics = new SDLGraphics();
			surfaceGraphics.setTarget(surface);
			setGrid(args);
				
			//TODO check this !!!
			setWidth(surface.getWidth());
			setHeight(surface.getHeight());
			
			startAreaUpdateHandler();
			widgetUpdateInfo.add(new WidgetUpdate(this, new SDLRect(0, 0, getWidth(), getHeight() )));
		}  catch (SDLException e) {
			e.printStackTrace();
			throw new GUIException("Unable to load image");
		}
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
		int returnValue,xCellNeeded,yCellNeeded;
		
		if (widget == null) {	
			throw new GUIException("Widget doesn't exist");
		} else if ( offset > (grid[0] * grid[1] - 1)  ) {	
			throw new GUIException("Offset out of range for the area.");
		}

		returnValue= (widget.getWidth() / xCellDimension);
		xCellNeeded = (returnValue > 0) ? returnValue : 1;
		
		returnValue = (widget.getHeight() / yCellDimension);
		yCellNeeded = (returnValue > 0) ? returnValue : 1;
		
		Set<Integer> cellsNr = new HashSet<Integer>();
		
		//TODO check here if cells are not reserved
		for (int xIndex = offset; xIndex <= offset+xCellNeeded; xIndex++) {	
			for (int yIndex = 0; yIndex <= yCellNeeded; yIndex++) {
				cellsNr.add(new Integer(yIndex*grid[0] + xIndex));
			}
		}
		
		widgetMap.put(widget, cellsNr);
	
		if (widget instanceof Maintainable) {
			//widget.setFocusHandler(focusHandler);
			//((Panel)widget).setWidgetsFocusHandler(focusHandler);		
			widget.setParentWidget(this);
		}
		//else{
			widget.setFocusHandler(focusHandler);
			widget.setFocusable(true);
			//widget.requestFocus();
		//}	
		widget.setPosition( (offset % grid[0]) * xCellDimension, (offset / grid[1] ) * yCellDimension);
		widget.setUpdateListener(this);
		
		widgetUpdateInfo.add(new WidgetUpdate(widget, new SDLRect(widget.getX(), widget.getY(),
																  widget.getWidth(), widget.getHeight() )));
	}
	
	/**
	 * Calculates cell dimension on given grid dimension 
	 */
	private void calculateCellDimension() {		
		xCellDimension = Screen._screenWidth / grid[0];
		yCellDimension = Screen._screenHeight / grid[1];	
	}
	
	/**
	 * Cleans any dynamically reserved memory areas in C style 
	 * @throws GUIException
	 */
	public void delete() throws GUIException {
		for (PlatformWidget theWidget : widgetMap.keySet()){
			theWidget.delete();		
		}
		
		widgetMap.clear();
		try {
			surface.freeSurface();
			originalSurface.freeSurface();
			super.delete();
		} catch (SDLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Draws part of the surface specified by rect argument on the screen  
	 * @param rect
	 * 			<code> SDLRect </code> specifying rectangle to draw 
	 * @throws GUIException
	 */
	public void draw(SDLRect rect) throws GUIException {
		try {
			UnifiedGraphics screenGraphics = Screen.getScreen().getGraphics();
								
			screenGraphics.beginDraw();
			screenGraphics.drawSDLSurface(surface, rect, rect);
			screenGraphics.endDraw();
		} catch (SDLException e) {
			e.printStackTrace();
			throw new GUIException("Exception while drawing on target surface");
		}
	}

	
	/**
	 * Draws whole surface on the screen 
	 * @param graphics
	 * 			not used here
	 * @throws GUIException
	 */
	@Override
	public void draw(UnifiedGraphics graphics) throws GUIException {
		try {
			UnifiedGraphics screenGraphics = Screen.getScreen().getGraphics();
			
			for (PlatformWidget widget : widgetMap.keySet()) {
				//TODO check this
				widget.draw(surfaceGraphics);
			}
									
			screenGraphics.beginDraw();
			screenGraphics.drawSDLSurface(surface, surface.getRect(), screenGraphics.getTarget().getRect());
			screenGraphics.endDraw();
		} catch (SDLException e) {
			e.printStackTrace();
			throw new GUIException("Exception while drawing on target surface");
		}
	}

	/**
	 * Not used
	 */
	@Override
	public void drawBorder(UnifiedGraphics graphics) throws GUIException {
		// TODO Auto-generated method stub
	}

	/**
	 * Draws part of original background surface specified by rect argument
	 * @param rect
	 * 			<code> SDLRect </code> specifying rectangle to draw 
	 * @throws GUIException
	 */
	public void drawOriginal(SDLRect rect) throws GUIException {
		try {
	
			surfaceGraphics.beginDraw();
			surfaceGraphics.drawSDLSurface(originalSurface, rect, rect);
			surfaceGraphics.endDraw();
			
		} catch (SDLException e) {
			e.printStackTrace();
			throw new GUIException("Exception while drawing on target surface");
		}
	}
	
	/**
	 * Draws all widgets that are found in the rect argument
	 * @param rect
	 * 			<code> SDLRect </code> specifying rectangle to look for widgets
	 * @param widgetNotToDraw
	 * 			widget that mustn't be redrawn even if found in that rectangle 
	 * @throws GUIException
	 */
	public void drawWidgets(SDLRect rect, PlatformWidget widgetNotToDraw) throws GUIException {
		for (PlatformWidget widget : widgetMap.keySet()) {
			if (! widget.equals(widgetNotToDraw)) {
				if (widget.getX() >= rect.x && widget.getX() <= rect.x + rect.width 
						&& widget.getY() >= rect.y && widget.getY() <= rect.y + rect.height) {
					widget.draw(surfaceGraphics);
				}
			}
		}
	}
	
	/**
	 * Get default x grid dimension
	 * @return default x grid dimension
	 */
	public int getDefXGrid() {
		return defXGrid;
	}

	/**
	 * Get default y grid dimension
	 * @return default y grid dimension
	 */
	public int getDefYGrid() {
		return defYGrid;
	}
		
	/**
	 * Get widgets focusHandler
	 * @return <code> FocusHandler </code>
	 */
	public FocusHandler getFocusHandler() {
		return focusHandler;
	}

	/**
	 * Get array with grid dimension
	 * @return array with grid dimension
	 */
	public int[] getGrid() {
		return grid;
	}

	/**
	 * Get working surface
	 * @return <code> SDLSurface </code>
	 */
	public SDLSurface getSurface() {
		return surface;
	}

	/**
	 * Get surface drawing object
	 * @return <code> UnifiedGraphics </code> drawing object
	 */
	public UnifiedGraphics getSurfaceGraphics() {
		return surfaceGraphics;
	}

	/**
	 * Get association between widgets on the Area and cell numbers occupied
	 * @return
	 * 		Map<PlatformWidget, Set<Integer>>
	 */
	public Map<PlatformWidget, Set<Integer>> getWidgetMap() {
		return widgetMap;
	}

	/**
	 * Get cell width on the screen
	 * @return cell width
	 */
	public int getxCellDimension() {
		return xCellDimension;
	}

	/**
	 * Get cell height on the screen
	 * @return cell height
	 * 
	 */
	public int getyCellDimension() {
		return yCellDimension;
	}

	/**
	 * Puts a request object into queue
	 * @param updateinfo
	 * 			<code> WidgetUpdate </code>
	 */
	public void putRegionToUpdate(WidgetUpdate updateInfo) throws InterruptedException {
		widgetUpdateInfo.offer(updateInfo, 50L, TimeUnit.MILLISECONDS);
	}


	/**
	 * Remove widget from an area
	 * @param widget
	 * 			<code> PlatformWidget </code> to be removed
	 * @throws GUIException
	 */
	@Override
	public void remove(PlatformWidget widget) throws GUIException {	
		for (PlatformWidget theWidget : widgetMap.keySet()) {
			if (theWidget.equals(widget)) {
				//TOD0 will this really work?
				widgetUpdateInfo.add(new WidgetUpdate(this , new SDLRect(widget.getX(), widget.getY(), widget.getWidth(), widget.getHeight() )));
				widget.delete();
				widgetMap.remove(widget);
				return;
			}		
		}
		throw new GUIException("No such widget in this area");
	}
	
	/**
	 * Set transparency coefficient for working surface
	 * @parem alphaIndex
	 * 			transparency index to be set - 0 is totally transparent, 255 means no transparency 
	 */
	@Override
	public void setAlpha(int alphaIndex) {
		try {
			originalSurface.setAlpha(Screen._alphaFlags, alphaIndex);
			surface.setAlpha(Screen._alphaFlags, alphaIndex);
		} catch (SDLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		widgetUpdateInfo.add(new WidgetUpdate(this, new SDLRect(0, 0, getWidth(), getHeight() )));
	}
	
	/**
	 * Set widgets focus handler
	 * @param focusHandler 
	 *			<code> FocusHandler </code>
	 */
	public void setFocusHandler(FocusHandler focusHandler) {
		
		this.focusHandler = focusHandler;
	}
	
	/**
	 * Set grid for surface
	 * @param args
	 * 			table with x grid dimensions - args[0] and y grid dimensions args[1]
	 */
	private void setGrid(int []args) {
		int index=0;
		grid = new int[2];
		
		grid[0] = defXGrid;
		grid[1] = defYGrid;
		
		for (int i : args) {
			grid[index] = i;
			index++;
		}
		calculateCellDimension();
	}
	
	/**
	 * Set working surface
	 * @param surface
	 * 			<code> SDLSurface </code>
	 */
	public void setSurface(SDLSurface surface) {
		this.surface = surface;
	}

	/**
	 * Starts handler responsible for updating screen on request
	 */
	private void startAreaUpdateHandler() {
		//AreaUpdateHandler areaUpdateHandler = 
		new AreaUpdateHandler();
	}
}

