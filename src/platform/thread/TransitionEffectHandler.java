package platform.thread;

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
import platform.gui.PlatformWidget;
import platform.util.Direction;
import platform.util.UpdateListener;
import platform.util.WidgetUpdate;
import sdljava.video.SDLRect;

/**
 * Thread used for handling appearing and disappearing of 'hidden' menu
 * @author Bartosz Kędra
 * @author bartosz.kedra@gmail.com
 *
 */
public class TransitionEffectHandler extends Thread {

	/**
	 * Widget that will be transitioned 
	 */
	PlatformWidget widget;
	
	/**
	 * Object processing update requests
	 */
	UpdateListener updateListener;
	
	/**
	 * Direction of move
	 */
	Direction direction;
	
	/**
	 * Flag indicating whether show or not the widget
	 */
	boolean show;
	
	/**
	 * Constructor also starts the thread
	 * @param widg
	 * 			widget to operate on
	 * @param listener
	 * 			object processing update requests
	 * @param dir
	 * 			direction of move	
	 * @param shw
	 * 			flag whether to show or not
	 */
	public TransitionEffectHandler(PlatformWidget widg , UpdateListener listener, Direction dir, boolean shw) {
		widget = widg;
		updateListener = listener;
		direction = dir;
		show=shw;
		start();
	}
	
	/**
	 * Main task function , shows or hides partially the widget
	 */
	public void run(){
		try {
			if (show == true) {	
				switch (direction) {
					case NORTH:
					for(double i = 0.2; i <= 1.0; i+=0.2) {
						updateListener.putRegionToUpdate( new WidgetUpdate (widget, new SDLRect(widget.getX(), widget.getY(),
																							    widget.getWidth(), (int) (widget.getHeight() * i) )));
						Thread.sleep(50);
					}
					break;
					
					case EAST:
					for (double i = 0.8; i >= 0.0; i-=0.2) {
						updateListener.putRegionToUpdate( new WidgetUpdate (widget, new SDLRect(widget.getX() + (int) (widget.getWidth() * i), widget.getY(),
																							   (int) (widget.getWidth() * i), widget.getHeight() )));
						Thread.sleep(50);
					}
					break;
						
					case SOUTH:
					for (double i = 0.8; i >= 0.0; i-=0.2) {
						updateListener.putRegionToUpdate( new WidgetUpdate (widget, new SDLRect(widget.getX(), widget.getY()+ (int)(widget.getHeight() * i),
																							    widget.getWidth(), (int) (widget.getHeight() * i) + 1  )));
						Thread.sleep(50);
					}
					break;
					
					case WEST:	
					for (double i = 0.2; i < 1.0; i+=0.2) {
						updateListener.putRegionToUpdate(new WidgetUpdate(widget, new SDLRect(widget.getX(), widget.getY(),
																							 (int) (widget.getWidth() * i), widget.getHeight() )));
						Thread.sleep(50);
					}
					break;
						
					default:
					break;
				}
					
				updateListener.putRegionToUpdate(new WidgetUpdate(widget, new SDLRect(widget.getX(), widget.getY(),
																					  widget.getWidth(), widget.getHeight() )));
			} else {
				switch(direction){
					
					case NORTH:
					for (double i = 0.2; i <= 1.0; i+=0.2) {
						updateListener.putRegionToUpdate(new WidgetUpdate(widget, new SDLRect(widget.getX(), widget.getY() + (int) (widget.getHeight() * (1 - i)),
																							  widget.getWidth(), (int) (widget.getHeight() * i) + 1) ));
						Thread.sleep(50);
					}
					break;
					
					case EAST:
					for (double i = 0.8; i >= 0.0; i-=0.2) {
						//updateListener.putRegionToUpdate( new WidgetUpdate (widget,new SDLRect(widget.getX() + (int)(widget.getWidth() *(1-i) ) ,widget.getY() ,(int)(widget.getWidth() *(1-i) ) , widget.getHeight() ) ) );
						updateListener.putRegionToUpdate(new WidgetUpdate(widget, new SDLRect(widget.getX(), widget.getY(),
																							 (int) (widget.getWidth() * (1 - i)), widget.getHeight() )));
						Thread.sleep(50);
					}
					break;
						
					case SOUTH:
					for (double i = 0.8; i>=0.0; i-=0.2) {
						updateListener.putRegionToUpdate(new WidgetUpdate(widget, new SDLRect(widget.getX(), widget.getY(),
																							  widget.getWidth(), (int) (widget.getHeight() * (1-i)) ) ) ) ;
						Thread.sleep(50);
					}
					break;
					
					case WEST:	
					for (double i = 0.2; i <= 1.0; i+=0.2) {
						updateListener.putRegionToUpdate(new WidgetUpdate(widget, new SDLRect(widget.getX() + (int) (widget.getWidth() * (1 - i)), widget.getY(),
																							 (int) (widget.getWidth() * i), widget.getHeight())));
						Thread.sleep(50);
					}
					break;
				}
				Thread.sleep(50);
			}
				
		} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		} 
	}
}
