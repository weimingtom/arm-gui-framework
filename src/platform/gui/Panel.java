package platform.gui;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import platform.gfx.UnifiedGraphics;
import platform.util.UpdateListener;
import platform.util.WidgetUpdate;
import sdljava.video.SDLRect;
import sdljavax.guichan.GUIException;
import sdljavax.guichan.evt.FocusHandler;
import sdljavax.guichan.evt.MouseListener;
import sdljavax.guichan.gfx.Image;
import sdljavax.guichan.widgets.Widget;

public class Panel extends PlatformWidget implements MouseListener, UpdateListener {

	protected Image frame;
	protected List<PlatformWidget> widgetList = new ArrayList<PlatformWidget>();
	protected Integer xFormat, yFormat;
	
	private final int horizontalPixelShift = 10;
	private final int verticalPixelShift = 10;
	
	public Panel(int xFormat,int yFormat) throws GUIException{		
		super();
		
		this.xFormat = new Integer( ( xFormat > 4 ) ? 4 : xFormat );
		this.yFormat = new Integer( ( yFormat > 3 ) ? 3 : yFormat );
		
		String commonFramesName="_Widget_Frame_Landscape.png";
		String frameName = "resource" + File.separator+ "PNG" + File.separator + this.xFormat.toString() + "x" + this.yFormat.toString() + commonFramesName;
		
		frame = new Image(frameName);
		setHeight(frame.getHeight());
		setWidth(frame.getWidth());
		
		addMouseListener(this);
	}
	
	@Override
	public void add(PlatformWidget widget, int offset) throws GUIException{
		if(widget == null){
			
			throw new GUIException("Widget doesn't exist");
		} 
		else if ( offset > xFormat.intValue() * yFormat.intValue() - 1 ){
			
			throw new GUIException("Offset out of range for the panel.");
		}
		int horizontalShift = getX() + (offset % xFormat.intValue()) * ( getWidth() / xFormat.intValue() ) + horizontalPixelShift;  
		int verticalShift = getY() + (offset / xFormat.intValue()) * ( getHeight() / yFormat.intValue()) + verticalPixelShift;
		
		widgetList.add(widget);
		widget.setPosition(horizontalShift, verticalShift);
		widget.setUpdateListener(this);
		
		if( this.getFocusHandler() != null){	
			widget.setFocusHandler(getFocusHandler());
			widget.setFocusable(true);
			widget.requestFocus();
		}
		
		try {
			putRegionToUpdate( new WidgetUpdate( widget, new SDLRect( widget.getX(), widget.getY(), widget.getWidth(), widget.getHeight()) ) );
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void remove(PlatformWidget widget) throws GUIException{
		for( Widget theWidget: widgetList){
			if(widget.equals(theWidget)){
				widgetList.remove(widget);
				return;
			}
		}
		throw new GUIException("No such widget in this panel");
	}
	
	@Override
	public void draw(UnifiedGraphics graphics) throws GUIException {
			drawBorder(graphics);
			
			for ( Widget widget : widgetList){
				widget.draw(graphics);
			}
	}

	@Override
	public void drawBorder(UnifiedGraphics graphics) throws GUIException {
			graphics.drawImage(frame, getX(), getY() );
	}

	public void setWidgetsFocusHandler(FocusHandler focusHandler) throws GUIException{
		for( Widget widget : widgetList){
			widget.setFocusHandler(focusHandler);
			widget.setFocusable(true);
			widget.requestFocus();
		}
	}
	
	public void mouseClick(int x, int y, int b, int ts) throws GUIException {
		for( Widget widget : widgetList){
			
			if(widget instanceof MouseListener){
				if( x >= widget.getX() && x<= widget.getX() + widget.getWidth() && y>= widget.getY() && y<= widget.getY() + widget.getHeight())
				((MouseListener)widget).mouseClick(x, y, b, ts);
			}
		}
	}

	public void mouseIn() throws GUIException {
		// TODO Auto-generated method stub
		
	}

	public void mouseMotion(int arg0, int arg1) throws GUIException {
		// TODO Auto-generated method stub
		
	}

	public void mouseOut() {
		// TODO Auto-generated method stub
		
	}

	public void mousePress(int x, int y, int b) throws GUIException {
		for( Widget widget : widgetList){
			
			if(widget instanceof MouseListener){
				if( x >= widget.getX() && x<= widget.getX() + widget.getWidth() && y>= widget.getY() && y<= widget.getY() + widget.getHeight())
				((MouseListener)widget).mousePress(x, y, b);
			}
		}
		
	}

	public void mouseRelease(int x, int y, int b) throws GUIException {
		for( Widget widget : widgetList){
			
			if(widget instanceof MouseListener){
				if( x >= widget.getX() && x<= widget.getX() + widget.getWidth() && y>= widget.getY() && y<= widget.getY() + widget.getHeight())
				((MouseListener)widget).mouseRelease(x, y, b);
			}
		}
	}

	public void mouseWheelDown(int x, int y) throws GUIException {
		// TODO Auto-generated method stub
		
	}

	public void mouseWheelUp(int x, int y) throws GUIException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete() throws GUIException {
		// TODO Auto-generated method stub
		frame.delete();
		for (Widget widget: widgetList){
			
			widget.delete();
		}
		super.delete();
		
	}

	public void putRegionToUpdate(WidgetUpdate updateInfo) throws InterruptedException {
		
		updateListener.putRegionToUpdate(updateInfo);
	}

		
}
