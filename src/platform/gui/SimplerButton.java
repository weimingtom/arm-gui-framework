package platform.gui;

import java.io.File;

import platform.gfx.UnifiedGraphics;
import platform.util.Active;
import platform.util.UpdateListener;
import platform.util.WidgetUpdate;
import sdljava.SDLException;
import sdljava.video.SDLRect;
import sdljava.video.SDLSurface;
import sdljavax.guichan.GUIException;
import sdljavax.guichan.evt.ActionListener;
import sdljavax.guichan.evt.MouseListener;
import sdljavax.guichan.gfx.Image;

/**
 * Class representing button, simpler than <code> Button </code> class
 * @author Bartosz Kędra
 * @author bartosz.kedra@gmail.com 
 */

public class SimplerButton extends PlatformWidget implements MouseListener, UpdateListener {

	/**
	 * Three button images - default, clicked and selected
	 */
	protected Image defaultButton;
	protected Image clickedButton;
	protected Image selectedButton;

	/**
	 * Current button state, the class behaves differently according to that state
	 */
	protected Image currentImage;
		
	protected boolean changed = false;
	
	protected boolean play = true;
	/**
	 * Constructor
	 * @param resourceDir
	 * 			path to directory where the button images are stored
	 * @param String[]
	 * 			button exact names 
	 * @throws GUIException
	 */
	public SimplerButton(String resourceDir, String []names, String actionName) throws GUIException {
		super ();
		
		defaultButton = new Image(resourceDir + names[0]);
		selectedButton = new Image(resourceDir + names[1]);
		clickedButton = new Image(resourceDir + names[2]);
		currentImage = defaultButton;
		
		setWidth(defaultButton.getWidth());
		setHeight(defaultButton.getHeight());
		
		this.m_strEventId = actionName;
		addMouseListener(this);
	}
	
	public void addButtonDemoActionListener(){
		addActionListener(new ActionListener(){
			public void action(String strEventId) throws GUIException{
				int x = 225;
				Area display = null;
				try {
					
					display = Screen.getScreen().getBackground();
					while (x > 125) {
							//300 ms is good to switch areas smoothly
							display.setAlpha(255-x);
							Thread.sleep(250);
							x -= 25;
					}
					display.setAlpha(255);
					Screen.getScreen().setActive(Active.BACKGROUND);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SDLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	
	public void addDemoActionListener2(){
		addActionListener(new ActionListener(){
			public void action(String strEventId) throws GUIException{
				defaultButton.delete();
				clickedButton.delete();
				selectedButton.delete();
				
				
				if(!play){
					SimplerButton.this.m_strEventId = "pause";
					defaultButton = new Image("resource" + File.separator + "PNG" + File.separator + "ic_media_play.png");
					selectedButton = new Image("resource" + File.separator + "PNG" + File.separator + "ic_media_play_selected.png");
					clickedButton = new Image("resource" + File.separator + "PNG" + File.separator + "ic_media_play_pressed.png");
				} else {
					SimplerButton.this.m_strEventId = "play";
					defaultButton = new Image("resource" + File.separator + "PNG" + File.separator + "ic_media_pause.png");
					selectedButton = new Image("resource" + File.separator + "PNG" + File.separator + "ic_media_pause_selected.png");
					clickedButton = new Image("resource" + File.separator + "PNG" + File.separator + "ic_media_pause_pressed.png");
				}
				
				play = !play;
			}
		});
	}
	/**
	 * Cleans any dynamically reserved memory areas in C style 
	 * @throws GUIException
	 */
	@Override
	public void delete() throws GUIException {
		defaultButton.delete();
		clickedButton.delete();
		selectedButton.delete();
		super.delete();
	}

	/**
	 * Draws button on the surface  
	 * @param graphics
	 * 			used for drawing button on target surface
	 * @throws GUIException
	 */
	@Override
	public void draw(UnifiedGraphics graphics) throws GUIException {
		
		graphics.drawImage(currentImage, getX(), getY());
	}

	/**
	 * Not used
	 * @throws GUIException
	 */
	@Override
	public void drawBorder(UnifiedGraphics graphics) throws GUIException {
		// TODO Auto-generated method stub
	}
	
	
	/**
	 * Not used
	 */
	public void mouseClick(int x, int y, int button, int count)	throws GUIException {
		
	
	}

	/**
	 * MouseListener interface implementation, sets the selectedButton image
	 */
	public void mouseIn() throws GUIException {
		
		currentImage = selectedButton;
		try {
			updateListener.putRegionToUpdate(new WidgetUpdate(this, new SDLRect(getX(),getY(),getWidth(), getHeight())));
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Not used
	 */
	public void mouseMotion(int x, int y) throws GUIException {
		// TODO Auto-generated method stub
	}

	/**
	 * MouseListener interface implementation, sets the defaultButton image
	 */
	public void mouseOut() {
		currentImage = defaultButton;
		try {
			updateListener.putRegionToUpdate(new WidgetUpdate(this, new SDLRect(getX(),getY(),getWidth(), getHeight())));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	
	/**
	 * MouseListener interface implementation, sets the clickedButton image
	 */
	public void mousePress(int x, int y, int button) throws GUIException {
		currentImage = clickedButton;
		try {
			updateListener.putRegionToUpdate(new WidgetUpdate(this, new SDLRect(getX(),getY(),getWidth(), getHeight())));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * MouseListener interface implementation, calls the same method on currentState object
	 */
	public void mouseRelease(int x, int y, int button) throws GUIException {
		
		try {
			generateAction();
			currentImage = defaultButton;
			updateListener.putRegionToUpdate(new WidgetUpdate((PlatformWidget)updateListener, new SDLRect(getX(),getY(),getWidth(), getHeight())));
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Not used
	 */
	public void mouseWheelDown(int x, int y) throws GUIException {
		// TODO Auto-generated method stub
	}

	/**
	 * Not used
	 */
	public void mouseWheelUp(int x, int y) throws GUIException {
		// TODO Auto-generated method stub
	}

	/**
	 * Passes request for an update to the enclosing container
	 * @param updateInfo
	 * 			<code> WidgetUpdate </code> update information
	 * @throws InterruptedException
	 */
	public void putRegionToUpdate(WidgetUpdate updateInfo) throws InterruptedException {
		updateListener.putRegionToUpdate(updateInfo);
	}

}
