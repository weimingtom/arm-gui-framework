package platform.gui;


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

public class Panel extends PlatformWidget implements MouseListener, UpdateListener {

	//protected Image frame;
	protected SDLSurface frame;
	protected List<PlatformWidget> widgetList = new ArrayList<PlatformWidget>();
	protected Integer xFormat;
	protected Integer yFormat;
	protected UnifiedGraphics panelGraphics;
	protected boolean alphaSet=false;
	protected int alpha;
	
	private final int horizontalPixelShift = 3;
	private final int verticalPixelShift = 10;
	
	public Panel(int xFormat, int yFormat) throws GUIException, SDLException {		
		super();
		
		this.xFormat = new Integer( ( xFormat > 4 ) ? 4 : xFormat );
		this.yFormat = new Integer( ( yFormat > 3 ) ? 3 : yFormat );
		
		String commonFramesName="_Widget_Frame_Landscape.png";
		String frameName = "resource" + File.separator+ "PNG" + File.separator + this.xFormat.toString() + "x" + this.yFormat.toString() + commonFramesName;
		
		frame = (SDLSurface) (new Image(frameName)).getData();
		setHeight(frame.getHeight());
		setWidth(frame.getWidth());
		
		panelGraphics = new SDLGraphics();
		//long colorKey = SDLVideo.mapRGB(frame.getFormat(), 0, 0, 0);
		//frame.setColorKey(SDLVideo.SDL_SRCCOLORKEY, colorKey);
		panelGraphics.setTarget(frame);
		addMouseListener(this);
	}
	
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
			putRegionToUpdate( new WidgetUpdate(this, new SDLRect(widget.getX() + getX(), widget.getY() + getY(),
																  widget.getWidth(), widget.getHeight() )));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
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
		for (PlatformWidget widget : widgetList) {
			widget.draw(panelGraphics);
		}
		panelGraphics.endDraw();
		drawBorder(graphics);
	}

	@Override
	public void drawBorder(UnifiedGraphics graphics) throws GUIException {
		try {
			graphics.drawSDLSurface(frame, panelGraphics.getTarget().getRect(), graphics.getTarget().getRect(getX(), getY()));
		} catch (SDLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
		}
		//graphics.drawImage(frame, getX(), getY() );
	}

	
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
		for (Widget widget : widgetList) {
			if (widget instanceof MouseListener) {
				if( x >= widget.getX() + getX() && x<= widget.getX() + widget.getWidth() + getX() 
					&& y>= widget.getY() + getY() && y<= widget.getY() + widget.getHeight() + getY()){
					((MouseListener) widget).mousePress(x, y, b);
				}
			}
		}
	}

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

	public void mouseWheelDown(int x, int y) throws GUIException {
		// TODO Auto-generated method stub
	}

	public void mouseWheelUp(int x, int y) throws GUIException {
		// TODO Auto-generated method stub
	}

	public void putRegionToUpdate(WidgetUpdate updateInfo) throws InterruptedException {
		SDLRect region = updateInfo.getWidgetRegion();
		updateListener.putRegionToUpdate(new WidgetUpdate(this, new SDLRect(region.x + getX(), region.y + getY(), 
																			region.width, region.height) ));
		//updateListener.putRegionToUpdate(updateInfo);
	}

	@Override
	public void remove(PlatformWidget widget) throws GUIException {
		for (Widget theWidget : widgetList) {
			if (widget.equals(theWidget)) {
				try {
					System.out.println(new SDLRect( widget.getX() + getX(), widget.getY() + getY(), widget.getWidth(), widget.getHeight()).toString() );
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

	public void setWidgetsFocusHandler(FocusHandler focusHandler) throws GUIException {
		for (Widget widget : widgetList) {
			widget.setFocusHandler(focusHandler);
			widget.setFocusable(true);
			widget.requestFocus();
		}
	}
}
