package platform.gui;

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


public class Area extends PlatformWidget implements UpdateListener {

	private class AreaUpdateHandler extends Thread {
		boolean needsUpdate = false;
		
		public AreaUpdateHandler() {
			super();
			start();
		}
		
		public void run() {
			//TODO solve the problem of whole screen being changed?
			try {
				while (Screen.getScreen().isRunning()) {
					WidgetUpdate widgetToUpdate;
						while (widgetUpdateInfo.size() > 0) {
							surfaceGraphics.beginDraw();
							
								if ((widgetToUpdate= widgetUpdateInfo.poll(5, TimeUnit.MILLISECONDS)) != null) {
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
	
	protected int grid[];
	protected int xCellDimension;
	protected int yCellDimension;
	protected SDLSurface surface;
	protected SDLSurface originalSurface;
	protected UnifiedGraphics surfaceGraphics;
	
	protected FocusHandler focusHandler = new FocusHandler();
	protected Map<PlatformWidget, Set<Integer> > widgetMap = new HashMap<PlatformWidget , Set<Integer> >();
	
	protected BlockingQueue<WidgetUpdate> widgetUpdateInfo = new LinkedBlockingQueue<WidgetUpdate>();
	
	private final int defXGrid = 5;
	private final int defYGrid = 4;
	
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
		
	public Area(SDLColor color, int... args) throws GUIException {
		try {	
			originalSurface = SDLVideo.createRGBSurface(SDLVideo.SDL_HWSURFACE, Screen._screenWidth, 
														Screen._screenHeight, 16 , 0, 0, 0, 0);
			//surface.fillRect( surface.mapRGBA(color.getRed(),color.getGreen(), color.getBlue(), 255));
			originalSurface.fillRect(originalSurface.mapRGBA(color.getRed(),color.getGreen(),color.getBlue(), 255));
			
			surface =SDLVideo.createRGBSurface(SDLVideo.SDL_HWSURFACE, Screen._screenWidth, 
											   Screen._screenHeight, 16 , 0, 0, 0, 0);
			surface.fillRect(originalSurface.mapRGB(color.getRed(),color.getGreen(),color.getBlue()));
			
			surfaceGraphics = new SDLGraphics();
			surfaceGraphics.setTarget(surface);
			setGrid(args);
			
			setWidth(surface.getWidth());
			setHeight(surface.getHeight());
			
			startAreaUpdateHandler();
			widgetUpdateInfo.add(new WidgetUpdate(this , new SDLRect(0, 0, getWidth(), getHeight() )));
		} catch (SDLException e) {
			e.printStackTrace();
			throw new GUIException("Unable to create surface from color");
		}	
	}
	
	public Area(String filename, int... args ) throws GUIException {
		try {
			originalSurface= SDLImage.load(filename);
			surface = SDLImage.load(filename);
			
			surfaceGraphics = new SDLGraphics();
			surfaceGraphics.setTarget(surface);
			setGrid(args);
				
			//TODO check this !!!
			setWidth(surface.getWidth());
			setHeight(surface.getHeight());
			
			startAreaUpdateHandler();
			widgetUpdateInfo.add(new WidgetUpdate(this , new SDLRect(0, 0, getWidth(), getHeight() )));
		}  catch (SDLException e) {
			e.printStackTrace();
			throw new GUIException("Unable to load image");
		}
	}
	
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
		for (int xIndex=offset; xIndex<= offset+xCellNeeded; xIndex ++) {	
			for (int yIndex= 0; yIndex<= yCellNeeded; yIndex++) {
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
		widget.setPosition( (offset % grid[0]) * xCellDimension ,(offset / grid[1] ) * yCellDimension);
		widget.setUpdateListener(this);
		
		widgetUpdateInfo.add(new WidgetUpdate(widget, new SDLRect(widget.getX(), widget.getY(),
																  widget.getWidth(), widget.getHeight() )));
	}
	
	private void calculateCellDimension() {		
		xCellDimension = Screen._screenWidth / grid[0] ;
		yCellDimension = Screen._screenHeight / grid[1] ;	
	}
	
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

	@Override
	public void drawBorder(UnifiedGraphics graphics) throws GUIException {
		// TODO Auto-generated method stub
	}

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
	
	public void drawWidgets(SDLRect rect, PlatformWidget widgetNotToDraw) throws GUIException {
		for (PlatformWidget widget : widgetMap.keySet()) {
			if (! widget.equals(widgetNotToDraw)) {
				if (widget.getX() >= rect.x && widget.getX() <= rect.x + rect.width && widget.getY() >= rect.y && widget.getY() <= rect.y + rect.height) {
					widget.draw(surfaceGraphics);
				}
			}
		}
	}
	
	public int getDefXGrid() {
		return defXGrid;
	}

	public int getDefYGrid() {
		return defYGrid;
	}
		
	public FocusHandler getFocusHandler() {
		return focusHandler;
	}

	public int[] getGrid() {
		return grid;
	}

	public SDLSurface getSurface() {
		return surface;
	}

	public UnifiedGraphics getSurfaceGraphics() {
		return surfaceGraphics;
	}

	public Map<PlatformWidget, Set<Integer>> getWidgetMap() {
		return widgetMap;
	}

	public int getxCellDimension() {
		return xCellDimension;
	}

	public int getyCellDimension() {
		return yCellDimension;
	}

	public void putRegionToUpdate(WidgetUpdate updateInfo) throws InterruptedException {
		widgetUpdateInfo.offer(updateInfo, 50L, TimeUnit.MILLISECONDS);
	}


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
	
	public void setFocusHandler(FocusHandler focusHandler) {
		
		this.focusHandler = focusHandler;
	}
	
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
	
	public void setSurface(SDLSurface surface) {
		this.surface = surface;
	}

	private void startAreaUpdateHandler() {
		AreaUpdateHandler areaUpdateHandler = new AreaUpdateHandler();
	}
}

