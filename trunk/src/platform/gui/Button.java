package platform.gui;

import platform.buttons.ClickedPlayButton;
import platform.buttons.DefaultPlayButton;
import platform.buttons.SelectedPlayButton;
import platform.gfx.UnifiedGraphics;
import platform.util.UpdateListener;
import platform.util.WidgetUpdate;
import sdljavax.guichan.GUIException;
import sdljavax.guichan.evt.MouseListener;

public class Button extends PlatformWidget implements MouseListener, UpdateListener{

	ButtonState defaultButton;
	ButtonState clickedButton;
	ButtonState selectedButton;
	
	ButtonState currentState;
		
	public Button(String resourceDir) throws GUIException{
		super();
		
		defaultButton = new DefaultPlayButton(resourceDir + "music_button_default.png", this , this);
		clickedButton = new ClickedPlayButton(resourceDir + "music_button_pressed.png", this, this);
		selectedButton = new SelectedPlayButton(resourceDir + "music_button_selected.png", this, this);
		
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
	
		currentState.mouseIn();
		
	}

	public void mouseMotion(int x, int y) throws GUIException {
		// TODO Auto-generated method stub
		
	}


	public void mouseOut() {
		currentState.mouseOut();
	}

	public void mousePress(int x, int y, int button) throws GUIException {
		currentState.mousePress();
	}

	public void mouseRelease(int x, int y, int button) throws GUIException {
		currentState.mouseRelease();
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

	public ButtonState getDefaultButton() {
		return defaultButton;
	}

	public ButtonState getClickedButton() {
		return clickedButton;
	}

	public ButtonState getSelectedButton() {
		return selectedButton;
	}

	public void setCurrentState(ButtonState currentState) {
		this.currentState = currentState;
	}
	
	
}
