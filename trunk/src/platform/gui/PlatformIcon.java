package platform.gui;

/**
*  arm-gui-framework -Java GUI based on sdljava for omap5912 board
*  Copyright (C) 2010  Bartosz Kędra (bartosz.kedra@gmail.com)
* 
*  This library is free software; you can redistribute it and/or
*  modify it under the terms of the GNU Lesser General Public
*  License as published by the Free Software Foundation; either
*  version 3.0 of the License, or (at your option) any later version.
* 
*  This library is distributed in the hope that it will be useful,
*  but WITHOUT ANY WARRANTY; without even the implied warranty of
*  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
*  Lesser General Public License for more details.
* 
*  You should have received a copy of the GNU Lesser General Public
*  License along with this library; if not, write to the Free Software
*  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
*  USA
*
*/
import platform.gfx.UnifiedGraphics;
import platform.util.Active;
import platform.util.WidgetUpdate;
import sdljava.SDLException;
import sdljava.video.SDLRect;
import sdljava.video.SDLSurface;
import sdljavax.guichan.GUIException;
import sdljavax.guichan.evt.ActionListener;
import sdljavax.guichan.evt.MouseListener;
import sdljavax.guichan.gfx.Image;

/**
 * Class representing simple icon
 * @author Bartosz Kędra
 * @author bartosz.kedra@gmail.com
 *
 */
public class PlatformIcon extends PlatformWidget implements MouseListener {
	
	/**
	 * Icon image
	 */
	private Image iconImage;
	
	/**
	 * Modified icon image
	 */
	private Image modified = null;
	
	/**
	 * Flag indicating whether draw or not modified icon image
	 */
	private boolean drawModified=false;
	
	protected boolean hasAppeared = false;
	/**
	 * Constructor
	 * @param image
	 * 			icon image
	 * @throws GUIException
	 */
	public PlatformIcon(Image image, String actionName) throws GUIException{
		super();
		iconImage = image;
		setHeight(image.getHeight());
		setWidth(image.getWidth());

		this.m_strEventId = actionName;
		addMouseListener(this);
	}
	
	public void addIconDemoActionListener(){
		addActionListener(new ActionListener(){
			public void action(String strEventId) throws GUIException{
				int x = 225;
				Area display = null;
				try {
					display = Screen.getScreen().getForeground();
					while (x > 125) {
							//300 ms is good to switch areas smoothly
							display.setAlpha(255-x);
							Thread.sleep(250);
							x -= 25;
					}
					display.setAlpha(255);
					Screen.getScreen().setActive(Active.FOREGROUND);
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
	
	public void addIconDemoActionListener2(){
		addActionListener(new ActionListener(){
			public void action(String strEventId) throws GUIException{
				
				if( !PlatformIcon.this.hasAppeared ) {
					for( int alpha = 55 ; alpha < 200 ; alpha += 40 ){
					try {
						for( PlatformWidget widget : Screen.getScreen().getBackground().getWidgetMap().keySet() ){
							widget.setAlpha(alpha);
						}
						Thread.sleep(300);
					} catch (SDLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					} 
				}	else {
					for( int alpha = 201 ; alpha > 0 ; alpha -= 40 ){
						try {
							for( PlatformWidget widget : Screen.getScreen().getBackground().getWidgetMap().keySet() ){
								widget.setAlpha(alpha);
							}
							Thread.sleep(300);
						} catch (SDLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	
					}
				}	
				PlatformIcon.this.hasAppeared = !PlatformIcon.this.hasAppeared;
			}					
		});
	}
	/**
	 * Cleans any dynamically reserved memory areas in C style 
	 * @throws GUIException
	 */
	@Override
	public void delete() throws GUIException {
		// TODO Auto-generated method stub
		iconImage.delete();
		if (modified != null) {
			modified.delete();
		}
		/*try {
			iconImage.freeSurface();
		} catch (SDLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		super.delete();
	}

	/**
	 * Cleans dynamically reserved memory area in C style for modified icon image 
	 * @throws GUIException
	 * @throws InterruptedException
	 */
	public void deleteIconModifiedImage() throws GUIException, InterruptedException {
		modified.delete();
		drawModified = false;
		//updateListener.putRegionToUpdate(new WidgetUpdate(this, new SDLRect(getX(), getY(), getWidth(), getHeight())));
	}
	
	/**
	 * Draws icon on the surface 
	 * @param graphics
	 * 			used for drawing menu on target surface
	 * @throws GUIException
	 */
	@Override
	public void draw(UnifiedGraphics graphics) throws GUIException {
		/*try {
			graphics.drawSDLSurface(iconImage, iconImage.getRect(), graphics.getTarget().getRect(getX(), getY()));
		} catch (SDLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		if (! drawModified) {
			graphics.drawImage(iconImage, getX(), getY());
		} else {
			graphics.drawImage(modified, getX(), getY());	
		}
	}
	
	/**
	 * Draws border - background on the target surface - not used here
	 * @param graphics
	 * 			used for drawing background on target surface
	 * @throws GUIException
	 */
	@Override
	public void drawBorder(UnifiedGraphics graphics) throws GUIException {
		// TODO Auto-generated method stub
	}
	
	/**
	 * Get icon image as SDLSurface
	 * @return	SDLSurface representation of icon image
	 */
	public SDLSurface getIconImage() {
		return ((SDLSurface) iconImage.getData());
	}
	
	/**
	 * MouseListener implementation - icon gets transparent when clicked
	 */
	public void mouseClick(int arg0, int y, int button, int count) throws GUIException {
			generateAction();
	}
	
	/**
	 * Not used
	 */
	public void mouseIn() throws GUIException {
		
	}
	
	/**
	 * Not used
	 */
	public void mouseMotion(int x, int y) throws GUIException {
		// TODO Auto-generated method stub
	}
	
	/**
	 * Not used
	 */
	public void mouseOut() {
		// TODO Auto-generated method stub
	}
	
	/**
	 * Not used
	 */
	public void mousePress(int x, int y, int button) throws GUIException {
		// TODO Auto-generated method stub
	}
	
	/**
	 * Not used
	 */
	public void mouseRelease(int x, int y, int button) throws GUIException {
		// TODO Auto-generated method stub
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
	 * Set transparency coefficient for working surface
	 * @parem alphaIndex
	 * 			transparency index to be set - 0 is totally transparent, 255 means no transparency 
	 */
	@Override
	public void setAlpha(int alphaIndex) {
		try {
			((SDLSurface) iconImage.getData()).setAlpha(Screen._alphaFlags, alphaIndex);
			updateListener.putRegionToUpdate(new WidgetUpdate(this, new SDLRect(getX(), getY(), getWidth(), getHeight() )));
		} catch (SDLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Set icon image
	 * @param icon
	 * 			representation of icon image
	 * @param width
	 * 			width of icon image
	 * @param height
	 * 			height of icon image
	 * @throws GUIException
	 */
	public void setIconImage(Object icon, int width, int height) throws GUIException {
		iconImage.delete();
		iconImage = new Image(icon, width, height);
		setWidth(width);
		setHeight(height);
	}
	
	/**
	 * Set modfied icon image
	 * @param icon
	 * 			representation of icon image
	 * @param width
	 * 			width of icon image
	 * @param height
	 * 			height of icon image
	 * @throws GUIException
	 */
	public void setIconModifiedImage(Object modifiedIcon, int width, int height) throws GUIException {
		drawModified = true;
		if (modified == null) {
			modified = new Image(modifiedIcon, width, height);
		} else {
			modified.delete();
			modified = new Image(modifiedIcon, width, height);
		}
	}
}
