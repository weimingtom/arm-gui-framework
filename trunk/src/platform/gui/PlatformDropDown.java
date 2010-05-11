package platform.gui;

import platform.font.CalibriFont;

import platform.util.UpdateListener;
import platform.util.WidgetUpdate;
import sdljava.SDLException;
import sdljava.video.SDLColor;
import sdljava.video.SDLRect;
import sdljavax.guichan.GUIException;
import sdljavax.guichan.widgets.DropDown;
import sdljavax.guichan.widgets.ListBox;
import sdljavax.guichan.widgets.ListModel;
import sdljavax.guichan.widgets.ScrollArea;

public class PlatformDropDown extends DropDown implements UpdateListener{

	ListBox listBox; 
	ScrollArea scrollArea; 
	
	public PlatformDropDown(ListModel list) throws GUIException, SDLException{
		super(list);
		
		listBox = new ListBox(list);
		listBox.setSelected(2);
		listBox.setBorderSize(1);
		listBox.setFont(new CalibriFont(20, new SDLColor(0,0,0,0), true));
			
		scrollArea = new ScrollArea();
		scrollArea.setBorderSize(1);
		scrollArea.setContent(listBox);
   
	    super.setFont(listBox.getFont());
	    super.setListBox(listBox);
		super.setScrollArea(scrollArea); ///It will adjust height automatically
		super.logic(); 
	}
	
	

	@Override
	public void delete() throws GUIException {
		listBox.delete();
		scrollArea.delete();
		super.delete();
	}



	@Override
	public void mousePress(int x, int y, int button) throws GUIException {
		// TODO Auto-generated method stub
		super.mousePress(x, y, button);
		try {
			putRegionToUpdate(new WidgetUpdate(this, new SDLRect(getX(), getY(), super.getWidth(), super.getHeight())));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void mouseOutMessage() {
		// TODO Auto-generated method stub
		super.mouseOutMessage();
		try{
			int width=super.getWidth();
			int height= super.getHeight();
			 
			lostFocus();
			putRegionToUpdate(new WidgetUpdate(this, new SDLRect(getX(), getY(), super.getWidth(), super.getHeight())));
			
			Thread.sleep(300);
			putRegionToUpdate(new WidgetUpdate(this, new SDLRect(getX(), getY(), width, height )));
		}
		 catch (InterruptedException e) {
			e.printStackTrace();
		} catch (GUIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public boolean putRegionToUpdate(WidgetUpdate updateInfo)
			throws InterruptedException {
		// TODO Auto-generated method stub
		return ((UpdateListener)getParent()).putRegionToUpdate(updateInfo);
	}
	
	//public FocusHandler getFocusHandler(){
	//	return m_focusHandler;
	//}
}
