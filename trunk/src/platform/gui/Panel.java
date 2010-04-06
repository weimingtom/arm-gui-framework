package platform.gui;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import sdljavax.guichan.GUIException;
import sdljavax.guichan.gfx.Graphics;
import sdljavax.guichan.widgets.Widget;

public class Panel extends Widget {

	protected Map<Widget, Set<Integer> > widgetMap = new HashMap<Widget , Set<Integer> >();
	
	protected int xGrid, yGrid;
	protected int xCellDimension, yCellDimension;
	private final int defXGrid = 5;
	private final int defYGrid = 4;
	
	public Panel(){
		super();
		
		xGrid = defXGrid;
		yGrid = defYGrid;
		
		calculateCellDimension();
	}
	
	public Panel(int xGrid,int yGrid){
		
		super();
		
		this.xGrid = xGrid;
		this.yGrid = yGrid;
	
		calculateCellDimension();
		
	}
	
	public void addToPanel(Widget widget, int offset) throws GUIException{
	
		int returnValue,xCellNeeded,yCellNeeded;
		
		if(widget == null){
			
			throw new GUIException("Widget doesn't exist");
		}

		returnValue= (widget.getWidth() / xCellDimension);
		xCellNeeded = (returnValue > 0) ? returnValue : 1;
		
		returnValue = (widget.getHeight() / yCellDimension);
		yCellNeeded = (returnValue > 0) ? returnValue : 1;
		
		Set<Integer> cellsNr = new HashSet<Integer>();
		
		for(int xIndex=offset; xIndex<= offset+xCellNeeded; xIndex ++){
			
			for(int yIndex= 0; yIndex<= yCellNeeded; yIndex++){
				
				cellsNr.add(new Integer(yIndex*xGrid + xIndex));
								
			}
			
		}
		
		widgetMap.put(widget, cellsNr);
			
		
		//widget.setPosition(, 100);
		widget.setPosition( (offset % xGrid)* xCellDimension ,(offset / xGrid) * yCellDimension);
		
		
	}
	
	public void removeFromPanel(Widget widget) throws GUIException{
		
		for( Widget theWidget: widgetMap.keySet()){
			
			if(theWidget.equals(widget)){
				
				widgetMap.remove(widget);
				return;
			}
			
		}
		
		
		throw new GUIException("No such widget in this container");
		
	}
	
	@Override
	public void draw(Graphics arg0) throws GUIException {
		
			
		for ( Widget widgetToDraw : widgetMap.keySet()){
	
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