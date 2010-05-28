package platform.gui;

import platform.buttons.ClickedPlayButton;
import platform.buttons.DefaultPlayButton;
import platform.buttons.SelectedPlayButton;
import platform.gfx.UnifiedGraphics;
import platform.util.UpdateListener;
import platform.util.WidgetUpdate;
import sdljava.video.SDLRect;
import sdljavax.guichan.GUIException;
import sdljavax.guichan.evt.MouseListener;

public class Button extends PlatformWidget implements MouseListener, UpdateListener{

	ButtonState defaultButton;
	ButtonState clickedButton;
	ButtonState selectedButton;
	
	ButtonState currentState;
		
	public Button(String resourceDir) throws GUIException{
		super();
		
		defaultButton = new DefaultPlayButton(resourceDir + "music_button_default.png", this );
		clickedButton = new ClickedPlayButton(resourceDir + "music_button_pressed.png", this);
		selectedButton = new SelectedPlayButton(resourceDir + "music_button_selected.png", this);
		
		currentState = defaultButton;
		
		setWidth(defaultButton.getWidth());
		setHeight(defaultButton.getHeight());
		
		addMouseListener(this);
	}
	
	@Override
	public void draw(UnifiedGraphics graphics) throws GUIException {

		graphics.drawImage(currentState.getImage(), getX(), getY());
	}

	@Override
	public void drawBorder(UnifiedGraphics graphics) throws GUIException {
		// TODO Auto-generated method stub
		
	}

	public void mouseClick(int x, int y, int button, int count)
			throws GUIException {
		
		//buttonState=ButtonStates.DEFAULT;
	}

	public void mouseIn() throws GUIException {
	
		
		m_bHasMouse =  true;
		requestFocus();
		buttonState = ButtonStates.SELECTED;
		try {
			updateListener.putRegionToUpdate(new WidgetUpdate(this,new SDLRect(getX(),getY(),selectedButton.getWidth(), selectedButton.getHeight())));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void mouseMotion(int x, int y) throws GUIException {
		// TODO Auto-generated method stub
		
	}


	public void mouseOut() {
		m_bHasMouse =  false;
		buttonState=ButtonStates.DEFAULT;
		try {
			updateListener.putRegionToUpdate(new WidgetUpdate(this,new SDLRect(getX(),getY(),defaultButton.getWidth(), defaultButton.getHeight())));
			lostFocus();
		} catch (GUIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void mousePress(int x, int y, int button) throws GUIException {
		buttonState=ButtonStates.PRESSED;
		try {
			updateListener.putRegionToUpdate(new WidgetUpdate(this,new SDLRect(getX(),getY(),clickedButton.getWidth(), clickedButton.getHeight())));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void mouseRelease(int x, int y, int button) throws GUIException {
	
		buttonState=ButtonStates.DEFAULT;
		try {
			updateListener.putRegionToUpdate(new WidgetUpdate(this,new SDLRect(getX(),getY(),defaultButton.getWidth(), defaultButton.getHeight())));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		defaultButton.delete();
		clickedButton.delete();
		selectedButton.delete();
		super.delete();
	}

	public void putRegionToUpdate(WidgetUpdate updateInfo) throws InterruptedException {
		updateListener.putRegionToUpdate(updateInfo);

		
	}
	
}
