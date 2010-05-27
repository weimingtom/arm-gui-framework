package platform.gui;

import platform.gfx.UnifiedGraphics;
import platform.util.UpdateListener;
import sdljavax.guichan.GUIException;
import sdljavax.guichan.gfx.Graphics;
import sdljavax.guichan.widgets.Widget;

public abstract class PlatformWidget extends Widget{

	protected UpdateListener updateListener = null;
	
	@Override
	public void draw(Graphics graphics) throws GUIException {
		// Nothing in here
	}

	@Override
	public void drawBorder(Graphics graphics) throws GUIException {
		// Nothing in here
	}

	public abstract void draw(UnifiedGraphics graphics) throws GUIException;
	
	public abstract void drawBorder(UnifiedGraphics graphics) throws GUIException;
	
	public void add(PlatformWidget pltaformWidget, int offset) throws GUIException{
		
		throw new UnsupportedOperationException();
	}
	
	public void remove(PlatformWidget platformWidget) throws GUIException {
		throw new UnsupportedOperationException();
	}
	
	public void setUpdateListener(UpdateListener listener){
		updateListener = listener;
	}
	
	public UpdateListener getUpdateListener(){
		return updateListener;
	}
	
	
}
