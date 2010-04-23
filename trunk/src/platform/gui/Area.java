package platform.gui;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import sdljava.SDLException;
import sdljava.image.SDLImage;
import sdljava.video.SDLColor;
import sdljava.video.SDLSurface;
import sdljava.video.SDLVideo;
import sdljavax.guichan.GUIException;
import sdljavax.guichan.evt.FocusHandler;
import sdljavax.guichan.evt.Input;
import sdljavax.guichan.evt.Key;
import sdljavax.guichan.evt.KeyInput;
import sdljavax.guichan.gfx.Image;
import sdljavax.guichan.sdl.SDLGraphics;
import sdljavax.guichan.sdl.SDLUtils;
import sdljavax.guichan.widgets.Widget;

public class Area {

	protected SDLSurface surface;
	protected SDLGraphics surfaceGraphics;
	protected FocusHandler focusHandler = new FocusHandler();
	
	protected Map<Widget, Set<Integer> > widgetMap = new HashMap<Widget , Set<Integer> >();
	
	protected int grid[];
	protected int xCellDimension, yCellDimension;
	private final int defXGrid = 5;
	private final int defYGrid = 4;
	
	public Area(String filename, int... args ) throws GUIException {
		try {
			surface= SDLImage.load(filename);
			surfaceGraphics = new SDLGraphics();
			surfaceGraphics.setTarget(surface);
			setGrid(args);
		}  catch (SDLException e) {
			e.printStackTrace();
			throw new GUIException("Unable to load image");
			
		}
	}
	
	public Area(Image image, int... args) throws GUIException {	
		surface = (SDLSurface) image.getData();
		
		if(surface == null){
			throw new GUIException("Unable to create surface from image");
		}
				
		surfaceGraphics = new SDLGraphics();
		surfaceGraphics.setTarget(surface);		
		setGrid(args);
	}
	
	
	public Area(SDLColor color, int... args) throws GUIException{
		int rmask,gmask,bmask,amask;
		
		if (SDLUtils.SDL_BYTEORDER == SDLUtils.SDL_BIG_ENDIAN) {
			rmask = 0xff000000;
			gmask = 0x00ff0000;
			bmask = 0x0000ff00;
			amask = 0x000000ff;
		} else {
			rmask = 0x000000ff;
			gmask = 0x0000ff00;
			bmask = 0x00ff0000;
			amask = 0xff000000;
		}
		
		try {	
			//TODO mask problem and transparency
			surface = SDLVideo.createRGBSurface(SDLVideo.SDL_HWSURFACE,Screen._screenWidth, Screen._screenHeight, 16 , 0, 0, 0, 0);
			surface.fillRect( surface.mapRGBA(color.getRed(),color.getGreen(), color.getBlue(), 0));
			surfaceGraphics = new SDLGraphics();
			surfaceGraphics.setTarget(surface);
			setGrid(args);
				
		} 
		catch (SDLException e) {
			e.printStackTrace();
			throw new GUIException("Unable to create surface from color");
		}	
	}
	
	public void add(Widget widget, int offset) throws GUIException{
		int returnValue,xCellNeeded,yCellNeeded;
		
		if(widget == null){	
			throw new GUIException("Widget doesn't exist");
		}
		else if ( offset > grid[0] * grid[1] - 1 ){	
			throw new GUIException("Offset out of range for the area.");
		}

		returnValue= (widget.getWidth() / xCellDimension);
		xCellNeeded = (returnValue > 0) ? returnValue : 1;
		
		returnValue = (widget.getHeight() / yCellDimension);
		yCellNeeded = (returnValue > 0) ? returnValue : 1;
		
		Set<Integer> cellsNr = new HashSet<Integer>();
		
		//TODO check here if cells are not reserved
		for(int xIndex=offset; xIndex<= offset+xCellNeeded; xIndex ++){	
			for(int yIndex= 0; yIndex<= yCellNeeded; yIndex++){
				cellsNr.add(new Integer(yIndex*grid[0] + xIndex));
			}
		}
		
		widgetMap.put(widget, cellsNr);
		
		if( widget instanceof Panel ){
			widget.setFocusHandler(focusHandler);
			((Panel)widget).setWidgetsFocusHandler(focusHandler);		
		}
		else{
			widget.setFocusHandler(focusHandler);
			widget.setFocusable(true);
			widget.requestFocus();
		}	
		widget.setPosition( (offset % grid[0])* xCellDimension ,(offset / grid[1] ) * yCellDimension);
	}
	
	public void remove(Widget widget) throws GUIException{	
		for( Widget theWidget: widgetMap.keySet()){
			if(theWidget.equals(widget)){
				widgetMap.remove(widget);
				return;
			}		
		}
		throw new GUIException("No such widget in this area");
	}
	
	public void refreshArea() throws GUIException, SDLException{
		//TODO refreshing function if really needed
		surfaceGraphics.beginDraw();
		
		for ( Widget widgetToDraw : widgetMap.keySet()){
			//TODO if it needs to be updated
			 widgetToDraw.draw(surfaceGraphics);
		}
		surfaceGraphics.endDraw();
		drawSurface();	
	}
		
	public void setAlpha(int alphaIndex) throws SDLException{
		//TODO fast alpha blitting? 
		surface.setAlpha(Screen._alphaFlags, alphaIndex);
		//SDLSurface optimizedAlphaSurface = area.getSurface().displayFormatAlpha();
		//area.setSurface(optimizedAlphaSurface);
	}
	
	public SDLSurface getSurface() {
		return surface;
	}

	public void setSurface(SDLSurface surface) {
		this.surface = surface;
	}
	
	
	public FocusHandler getFocusHandler() {
		return focusHandler;
	}


	public void setFocusHandler(FocusHandler focusHandler) {
		this.focusHandler = focusHandler;
	}


	protected void drawSurface() throws GUIException{
		try {
			SDLGraphics screenGraphics = Screen.getScreen().getGraphics();
				
			screenGraphics.beginDraw();
			screenGraphics.drawSDLSurface(surface, surface.getRect(), screenGraphics.getTarget().getRect());
			screenGraphics.endDraw();
		} catch (SDLException e) {
			e.printStackTrace();
			throw new GUIException("Exception while drawing on target surface");
		}
	}

	private void calculateCellDimension(){		
		xCellDimension = Screen._screenWidth / grid[0] ;
		yCellDimension = Screen._screenHeight / grid[1] ;	
	}
	
	private void setGrid(int []args){
		int index=0;
		grid = new int[2];
		
		grid[0] = defXGrid;
		grid[1] = defYGrid;
		
		for(int i: args){
			grid[index] = i;
			index++;
		}
		calculateCellDimension();
	}	
		
}
