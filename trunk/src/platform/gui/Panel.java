package platform.gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import sdljavax.guichan.GUIException;
import sdljavax.guichan.gfx.Graphics;
import sdljavax.guichan.sdl.SDLGraphics;
import sdljavax.guichan.widgets.Widget;

public class Panel extends Widget {

	
	protected List<Widget> listOfWidgets ;//= new ArrayList<Widget>();
	protected List<Boolean> cells ;
	
	protected int xGrid, yGrid;
	protected int xCellDimension, yCellDimension;
	private final int defXGrid = 5;
	private final int defYGrid = 4;
	
	public Panel(){
		super();
		
		xGrid = defXGrid;
		yGrid = defYGrid;
		
		listOfWidgets = new ArrayList<Widget>( xGrid * yGrid);
		cells = new ArrayList<Boolean> ( xGrid * yGrid );
		calculateCellDimension();
		
	}
	
	public Panel(int xGrid,int yGrid){
		
		super();
		
		this.xGrid = xGrid;
		this.yGrid = yGrid;
	
		listOfWidgets = new ArrayList<Widget>( xGrid * yGrid);
		cells = new ArrayList<Boolean> ( xGrid * yGrid );
		calculateCellDimension();
		
	}
	
	public void addToPanel(Widget widget, int offset) throws GUIException{
	
		int returnValue,xCellNeeded,yCellNeeded;
		
		//xCellNeeded = (widget.getWidth() / xCellDimension);
		//xCellNeeded = (returnValue > 0) ? returnValue : 1;
		
		//yCellNeeded = (widget.getHeight() / yCellDimension);
		//yCellNeeded = (returnValue > 0) ? returnValue : 1;
		if(widget == null){
			
			throw new GUIException("Widget doesn't exist");
		}
		
		listOfWidgets.add(offset, widget);
		
		widget.setPosition(100, 100);
		//widget.setPosition( (offset % xGrid)* xCellDimension ,(offset / xGrid) * yCellDimension);
		
		
	}
	
	public void removeFromPanel(Widget widget) throws GUIException{
		
		for (Iterator<Widget> it = listOfWidgets.iterator(); it.hasNext(); ){
			
			if(it.next().equals(widget)){
				
				listOfWidgets.remove(widget);
				return;
			}
			
		}
		
		throw new GUIException("No such widget in this container");
		
	}
	
	@Override
	public void draw(Graphics arg0) throws GUIException {
		
			
		for ( Widget widgetToDraw : listOfWidgets){
	
			//TODO if it needs to be updated
			 widgetToDraw.draw(arg0);
		}
		
	}

	@Override
	public void drawBorder(Graphics arg0) throws GUIException {
		// nothing here
		
	}

	private void calculateCellDimension(){
		
		xCellDimension = Screen._screenWidth / xGrid ;
		yCellDimension = Screen._screenHeight / yGrid ;
		
	}
}
/*
for(int xIndex=offset; xIndex<= offset+xCellNeeded; xIndex ++){
	
	for(int yIndex= 0; yIndex<= yCellNeeded; yIndex++){
		
		if(cells.get(yIndex*xGrid + xIndex).booleanValue() == true){
			
			throw new GUIException("Can't put widget on specified positon, it is already used");
			
		}
		
	}
	
}


listOfWidgets.set(offset, widget);

for(int xIndex=offset; xIndex<= offset+xCellNeeded; xIndex ++){
	
	for(int yIndex= 0; yIndex<= yCellNeeded; yIndex++){
		
		cells.set(yIndex*xGrid + xIndex, Boolean.TRUE);
		
	}
	
}
*/