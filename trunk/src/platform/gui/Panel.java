package platform.gui;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import sdljavax.guichan.GUIException;
import sdljavax.guichan.gfx.Graphics;
import sdljavax.guichan.widgets.Widget;

public class Panel extends Widget {

		
	public Panel(){
		super();
		
		
		
		calculateCellDimension();
	}
	
	public Panel(int xGrid,int yGrid){
		
		super();
		
		
		calculateCellDimension();
		
	}
	
	public void addToPanel(Widget widget, int offset) throws GUIException{
	
			
	}
	
	public void removeFromPanel(Widget widget) throws GUIException{
		
		
	}
	
	@Override
	public void draw(Graphics arg0) throws GUIException {
		
			
				
	}

	@Override
	public void drawBorder(Graphics arg0) throws GUIException {
		// nothing here
		
	}

	private void calculateCellDimension(){
		
		
	}
}
