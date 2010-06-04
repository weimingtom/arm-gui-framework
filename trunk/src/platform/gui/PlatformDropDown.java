package platform.gui;

import platform.font.CalibriFont;
import platform.util.Maintainable;
import platform.util.UpdateListener;
import platform.util.WidgetUpdate;
import sdljava.SDLException;
import sdljava.video.SDLColor;
import sdljava.video.SDLRect;
import sdljavax.guichan.GUIException;
import sdljavax.guichan.evt.MouseInput;
import sdljavax.guichan.gfx.Color;
import sdljavax.guichan.widgets.ListBox;
import sdljavax.guichan.widgets.ListModel;
import sdljavax.guichan.widgets.ScrollArea;

public class PlatformDropDown extends DropDown implements UpdateListener, Maintainable{

	ListBox listBox; 
	ScrollArea scrollArea; 
	
	public PlatformDropDown(ListModel list) throws GUIException, SDLException{
		super(list);
		
		//setBaseColor(new Color(128,128,144, 255));
		//setForegroundColor(new Color(0,0,0,255));
		//setBackgroundColor(new Color(255,255,255,255));
		
		listBox = new ListBox(list);
		listBox.setSelected(2);
		listBox.setBorderSize(1);
		listBox.setFont(new CalibriFont(20, new SDLColor(0,0,0,0), true));
		listBox.setBackgroundColor(new Color(255,255,255,128));
		
		scrollArea = new ScrollArea();
		scrollArea.setBorderSize(1);
		scrollArea.setContent(listBox);
		scrollArea.setBackgroundColor(new Color(255,255,255,128));
		
	    super.setFont(listBox.getFont());
	    super.setListBox(listBox);
		super.setScrollArea(scrollArea); ///It will adjust height automatically
		//super.logic(); 
				
		
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
			
		try {
			
			if (button == MouseInput.LEFT && hasMouse() && !m_bDroppedDown) {
				m_bPushed = true;
				dropDown();
				putRegionToUpdate(new WidgetUpdate(this, new SDLRect(getX(), getY(), super.getWidth(), super.getHeight())));
				
			}
			// Fold up the listbox if the upper part is clicked after fold down
			else if (button == MouseInput.LEFT  && hasMouse() && m_bDroppedDown && y < m_nOldH) {
				int width=super.getWidth();
				int height= super.getHeight();
				foldUp();
				putRegionToUpdate(new WidgetUpdate(this, new SDLRect(getX(), getY(), super.getWidth(), super.getHeight())));
				
				Thread.sleep(300);
				putRegionToUpdate(new WidgetUpdate(this, new SDLRect(getX(), getY(), width, height )));
				
			} else if (!hasMouse()) {
				int width=super.getWidth();
				int height= super.getHeight();
				foldUp();
				putRegionToUpdate(new WidgetUpdate(this, new SDLRect(getX(), getY(), super.getWidth(), super.getHeight())));
				
				Thread.sleep(300);
				putRegionToUpdate(new WidgetUpdate(this, new SDLRect(getX(), getY(), width, height )));
			}
		//	System.out.println("Mouse pressed on dropdown");
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void mouseRelease(int x, int y, int button) {
		if (button == MouseInput.LEFT) {
			m_bPushed = false;
			try {
				putRegionToUpdate(new WidgetUpdate(this, new SDLRect(getX(), getY(), super.getWidth(), super.getHeight())));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void putRegionToUpdate(WidgetUpdate updateInfo)
			throws InterruptedException {
		// TODO Auto-generated method stub
		updateListener.putRegionToUpdate(updateInfo);
	}
	
}
