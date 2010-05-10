package platform.gui;

import platform.font.CalibriFont;
import platform.util.DropDown;
import platform.util.ListBox;
import platform.util.ScrollArea;
import platform.util.UpdateListener;
import platform.util.WidgetUpdate;
import sdljava.SDLException;
import sdljava.video.SDLColor;
import sdljava.video.SDLRect;
import sdljavax.guichan.GUIException;
import sdljavax.guichan.evt.FocusHandler;
import sdljavax.guichan.evt.MouseInput;
import sdljavax.guichan.widgets.ListModel;

public class PlatformDropDown extends DropDown implements UpdateListener{

	ListBox listBox; 
	ScrollArea scrollArea; 
	
	public PlatformDropDown(ListModel list) throws GUIException, SDLException{
		super(list);
		
		listBox = new ListBox(list);
		listBox.setBorderSize(1);
		listBox.setFont(new CalibriFont(20, new SDLColor(0,0,0,0), true));
		listBox.setSelected(2);
		
		
		scrollArea = new ScrollArea();
		scrollArea.setBorderSize(1);
		scrollArea.setContent(listBox);
		
	   
	    super.setFont(listBox.getFont());
	    super.setListBox(listBox);
		super.setScrollArea(scrollArea); ///It will adjust height automatically
	
		//TODO is it really needed?
		super.logic(); 
	}
	
	
	@Override
	public void mouseInputMessage(MouseInput arg0) throws GUIException {
		// TODO Auto-generated method stub
		
		super.mouseInputMessage(arg0);
		
		try{
			//logic();
			System.out.println(new SDLRect(getX(), getY(), super.getWidth(), super.getHeight()).toString());
			putRegionToUpdate(new WidgetUpdate(this, new SDLRect(getX(), getY(), super.getWidth(), super.getHeight())));
			
			/*} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();*/
		}
		catch(Exception e){
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
			System.out.println(new SDLRect(getX(), getY(), super.getWidth(), super.getHeight()).toString());
			putRegionToUpdate(new WidgetUpdate(this, new SDLRect(getX(), getY(), width, height )));
			lostFocus();
			/*} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();*/
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	public boolean putRegionToUpdate(WidgetUpdate updateInfo)
			throws InterruptedException {
		// TODO Auto-generated method stub
		return ((UpdateListener)getParent()).putRegionToUpdate(updateInfo);
	}
	
	public FocusHandler getFocusHandler(){
		return m_focusHandler;
	}
}
