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

public class PlatformDropDown extends DropDown implements UpdateListener, Maintainable {

	ListBox listBox; 
	ScrollArea scrollArea; 
	
	public PlatformDropDown(ListModel list) throws GUIException, SDLException {
		super(list);
		int maxWidth = 0;
		
		this.setFont(new CalibriFont(25, new SDLColor(0,0,0,0), true));
		for(int i = 0; i < list.getNumberOfElements(); i++){
			int stringWidth;
			
			stringWidth = getFont().getWidth(list.getElementAt(i));
			
			maxWidth = ( maxWidth > stringWidth) ? maxWidth : stringWidth;
			
		}
		maxWidth = ( (310 - getHeight()) > maxWidth) ? maxWidth : (310 - getHeight());
		setWidth(maxWidth + 10 +  getHeight());
				
		listBox = new ListBox(list);
		listBox.setSelected(2);
		listBox.setBorderSize(1);
		listBox.setFont(new CalibriFont(25, new SDLColor(0,0,0,0), true));
		listBox.setBackgroundColor(new Color(255,255,255,128));
		listBox.setWidth(getWidth());
		
		
		scrollArea = new ScrollArea();
		scrollArea.setBorderSize(1);
		scrollArea.setContent(listBox);
		scrollArea.setBackgroundColor(new Color(255,255,255,128));
		scrollArea.setWidth(getWidth());
		scrollArea.setScrollbarWidth(20);
		
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
	public void mouseClick(int x, int y, int button, int count)
			throws GUIException {
		/*System.out.println("Mouse click");
		try {
			m_strEventId = new String("ListBox selection");
			
			if (!m_bDroppedDown) {
				logic();
				m_bPushed = true;
				dropDown();
				putRegionToUpdate(new WidgetUpdate(this, new SDLRect(getX(), getY(), super.getWidth(), super.getHeight())));
				Thread.sleep(100);
			} else if (m_bDroppedDown && y < m_nOldH) { // Fold up the listbox if the upper part is clicked after fold down
				int width=super.getWidth();
				int height= super.getHeight();
				m_bPushed = false;
				logic();
				foldUp();
				putRegionToUpdate(new WidgetUpdate(this, new SDLRect(getX(), getY(), width, height)));
			} else if((button == MouseInput.LEFT  && m_bDroppedDown && y > m_nOldH)) {
				int width=super.getWidth();
				int height= super.getHeight();
				logic();
				putRegionToUpdate(new WidgetUpdate(this, new SDLRect(getX(), getY(), super.getWidth(), super.getHeight())));
				//Thread.sleep(200);
				//putRegionToUpdate(new WidgetUpdate(this, new SDLRect(getX(), getY(), width, height )));
			} else {
				System.out.println("Else");
				logic();
				putRegionToUpdate(new WidgetUpdate(this, new SDLRect(getX(), getY(), super.getWidth(), super.getHeight())));
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	

	@Override
	public void mouseIn() {
			try {
				System.out.println("Mouse in");
				logic();
				m_bPushed = true;
				dropDown();
				putRegionToUpdate(new WidgetUpdate(this, new SDLRect(getX(), getY(), super.getWidth(), super.getHeight())));
				
			} catch (GUIException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}

	@Override
	public void mousePress(int x, int y, int button) throws GUIException {
		
		try {
			/*if (!m_bDroppedDown) {
				logic();
				m_bPushed = true;
				dropDown();
				putRegionToUpdate(new WidgetUpdate(this, new SDLRect(getX(), getY(), super.getWidth(), super.getHeight())));
				
			} else*/ if (m_bDroppedDown && y < m_nOldH) { // Fold up the listbox if the upper part is clicked after fold down
				int width=super.getWidth();
				int height= super.getHeight();
				m_bPushed = false;
				logic();
				foldUp();
				putRegionToUpdate(new WidgetUpdate(this, new SDLRect(getX(), getY(), width, height)));
			} else if((button == MouseInput.LEFT  && m_bDroppedDown && y > m_nOldH)) {
				m_strEventId = new String("ListBox selection");
				logic();
				generateAction();
				putRegionToUpdate(new WidgetUpdate(this, new SDLRect(getX(), getY(), super.getWidth(), super.getHeight())));
			} else {
				logic();
				putRegionToUpdate(new WidgetUpdate(this, new SDLRect(getX(), getY(), super.getWidth(), super.getHeight())));
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void mouseRelease(int x, int y, int button) {
		if (button == MouseInput.LEFT) {
			try {
				m_bPushed = false;
				putRegionToUpdate(new WidgetUpdate(this, new SDLRect(getX(), getY(), super.getWidth(), super.getHeight())));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void putRegionToUpdate(WidgetUpdate updateInfo) throws InterruptedException {
		// TODO Auto-generated method stub
		updateListener.putRegionToUpdate(updateInfo);
	}
}
