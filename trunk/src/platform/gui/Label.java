package platform.gui;

import platform.font.CalibriFont;
import platform.gfx.UnifiedGraphics;
import platform.util.WidgetUpdate;
import sdljava.SDLException;
import sdljava.video.SDLColor;
import sdljava.video.SDLRect;
import sdljava.video.SDLSurface;
import sdljava.video.SDLVideo;
import sdljavax.guichan.GUIException;
import sdljavax.guichan.evt.MouseListener;
import sdljavax.guichan.font.Font;

public class Label extends PlatformWidget implements MouseListener {

	protected SDLSurface labelSurface;
	protected String headText;
	protected String descriptiveText;
	protected Font textFont;
	protected int shift;
	protected int shiftIndex=0;
	protected boolean shiftEnded = false;
	
	public Label(String head, int width, int height) throws SDLException {
		this(head, "", width, height);
	}
	
	public Label(String head, String description, int width, int height) throws SDLException {
		super ();
		headText = head;
		descriptiveText = description;
		
		labelSurface = SDLVideo.createRGBSurface(SDLVideo.SDL_HWSURFACE, width, height, 16, 0, 0, 0, 0);
		labelSurface.fillRect(labelSurface.mapRGB(150, 220, 250));
		setWidth(width);
		setHeight(height);
		
		if (descriptiveText.length() != 0) {
			textFont = new CalibriFont((int) (getHeight() * 0.45), new SDLColor(10,10,10), true);
		} else {
			textFont = new CalibriFont((int) (getHeight() * 0.7), new SDLColor(10,10,10), true);
		}
		addMouseListener(this);
	}
	
	public void delete(){
		try {
			labelSurface.freeSurface();
			super.delete();
		} catch (SDLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GUIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void draw(UnifiedGraphics graphics) throws GUIException {
		drawBorder(graphics);
		textFont.drawString(graphics, headText, getX() + 10, getY() + (int) (getHeight() * 0.05) );	
		
		if (descriptiveText.length() != 0) {
			if (textFont instanceof CalibriFont) {
				synchronized (this) {
					try {
						if (shift < ((CalibriFont) textFont).getCharacterWidth(descriptiveText.charAt(shiftIndex)) && shiftIndex==0 ) {
							((CalibriFont) textFont).drawStringBlended(graphics, descriptiveText, 
																	   getX() + 10 - shift, getY() + (int) (getHeight() * 0.5) );
						} else if (shift < ((CalibriFont) textFont).getCharacterWidth(descriptiveText.charAt(shiftIndex))) {
							((CalibriFont) textFont).drawStringBlended(graphics, descriptiveText.substring(shiftIndex),
																	  getX() + 10 - shift, getY() + (int)(getHeight() * 0.5) );
						} else {
							shift -= ((CalibriFont) textFont).getCharacterWidth(descriptiveText.charAt(shiftIndex));
							
							if (shiftIndex + 1 != descriptiveText.length()) { ; //% (descriptiveText.length());
								shiftIndex ++;
								((CalibriFont) textFont).drawStringBlended(graphics, descriptiveText.substring(shiftIndex),
																		   getX() + 10 - shift, getY() + (int)(getHeight() * 0.5) );
							}
						}
					} catch (SDLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} else {
			textFont.drawString(graphics, descriptiveText, getX() + 10, getY() + (int) (getHeight() * 0.6) );
			}
		}
	}
	
	@Override
	public void drawBorder(UnifiedGraphics graphics) throws GUIException {
		try {
			graphics.drawSDLSurface(labelSurface, labelSurface.getRect(),
									graphics.getTarget().getRect(getX(), getY()));
		} catch (SDLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void mouseClick(int x, int y, int button, int count) throws GUIException {
		new Thread(){
			public void run(){
				int textWidth = textFont.getWidth(descriptiveText);
				try{
					for (int i = 0 ; i < textWidth; i+=5) {
						synchronized(Label.this){
							shift+=5 ;
						}
						updateListener.putRegionToUpdate(new WidgetUpdate(Label.this, new SDLRect(getX(), getY(), getWidth(), getHeight())));
						Thread.sleep(100);
					}
					
					Thread.sleep(100);
					synchronized(Label.this){
						shift = 0;
						shiftIndex = 0;
					}
					updateListener.putRegionToUpdate( new WidgetUpdate( Label.this, new SDLRect(Label.this.getX(), Label.this.getY(),
																								Label.this.getWidth(), Label.this.getHeight())));
					Thread.sleep(50);
					
				}  catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
	}

	public void mouseIn() throws GUIException {
		m_bHasMouse =  true;
		requestFocus();	
	}

	public void mouseMotion(int x, int y) throws GUIException {
		// TODO Auto-generated method stub
	}

	public void mouseOut() {
		m_bHasMouse =  false;
		try {
			lostFocus();
		} catch (GUIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void mousePress(int x, int y, int button) throws GUIException {
		// TODO Auto-generated method stub
	}

	public void mouseRelease(int x, int y, int button) throws GUIException {
		// TODO Auto-generated method stub
	}

	public void mouseWheelDown(int x, int y) throws GUIException {
		// TODO Auto-generated method stub
	}

	public void mouseWheelUp(int x, int y) throws GUIException {
		// TODO Auto-generated method stub
	}
}
