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
import platform.gui.Area;
import platform.gui.Screen;
import platform.util.Active;
import platform.util.UpdateListener;
import platform.util.WidgetUpdate;
import sdljava.SDLException;
import sdljava.video.SDLRect;
import sdljava.video.SDLSurface;
import sdljavax.gfx.SDLGfx;

/**
 * Thread prepared for showing capabilities of library, it rotates an ellipse on the screen
 * what imitates 3D rotation effect
 * @author Bartosz Kędra
 * @author bartosz.kedra@gmail.com
 *
 */
public class FlipEffectHandler extends Thread {

	/**
	 * Number of rotations 
	 */
	final int TRANSITION_NR = 10;
	
	/**
	 * Area where the rotations take place
	 */
	Area motionArea;
	
	/**
	 * Second area, which fist becomes invisible and then visible again after effect finishes
	 */
	Area anotherArea;
	
	/**
	 * Object processing update requests
	 */
	UpdateListener updateListener;
	
	/**
	 * Motion area surface
	 */
	SDLSurface destSurface;
	
	/**
	 * Original motion surface, preserved
	 */
	SDLSurface originalSurface;
	
	/**
	 * Center x coordinate of an ellipse
	 */
	int xCenter;
	
	/**
	 * Center y coordinate of an ellipse
	 */
	int yCenter;
	
	/**
	 * horizontal ellipse radius
	 */
	int maxHorizontalRadius;
	
	/**
	 * vertical ellipse radius
	 */
	int maxVerticalRadius;
	
	
	/**
	 * Constructor, also launches the thread
	 * @param area
	 * 			motion area
	 * @throws SDLException
	 */
	public FlipEffectHandler(Area area) throws SDLException {
		motionArea = area;
		
		anotherArea = (Screen.getScreen().getBackground().equals(motionArea)) ? Screen.getScreen().getForeground() : Screen.getScreen().getBackground();
		updateListener = (UpdateListener) area;
		destSurface = area.getSurface();
		
		originalSurface = new SDLSurface(destSurface.getSwigSurface());
		xCenter = destSurface.getWidth() / 2;
		yCenter = destSurface.getHeight() / 2;
		maxHorizontalRadius = destSurface.getWidth() / 4;
		maxVerticalRadius = destSurface.getHeight() / 4;
		
		displayArea(motionArea);
		start();
	}
	
	/**
	 * Function setting transparency coefficient to surface
	 * @param display
	 * 			Area that should become visible
	 */
	private void displayArea(Area display) {
		int x = 250;
		
		while (x > 100) {
			try {
				display.setAlpha(255 - x);
				Thread.sleep(300);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			x -= 5;
		}
		display.setAlpha(255);
		
		try {
			if(display.equals(Screen.getScreen().getBackground()))
			Screen.getScreen().setActive(Active.BACKGROUND);
			
			else
			Screen.getScreen().setActive(Active.FOREGROUND);	
		} catch (SDLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Main thread function rotating ellipse
	 * 
	 */
	public void run(){
		int r = 255;
		int g = 0;
		int b = 0;
		int i = 0;
				
		try{
			while ( i++ < 4) {
				//SDLGfx.filledEllipseRGBA(destSurface, xCenter, yCenter, maxHorizontalRadius , maxVerticalRadius, 10, 10, 10 ,255);
				//updateListener.putRegionToUpdate(new WidgetUpdate(motionArea, new SDLRect(xCenter - maxHorizontalRadius, yCenter - maxVerticalRadius, maxHorizontalRadius *2, maxVerticalRadius * 2 )));
				for (int j = TRANSITION_NR; j > (-1); j--) {
					//parametr alpha 0 means fully transparent 
					SDLGfx.filledEllipseRGBA(destSurface, xCenter, yCenter, maxHorizontalRadius / TRANSITION_NR * j, maxVerticalRadius, r, g, b ,255);
					
					updateListener.putRegionToUpdate(new WidgetUpdate(motionArea, new SDLRect(xCenter - maxHorizontalRadius, yCenter - maxVerticalRadius,
																							  maxHorizontalRadius *2, maxVerticalRadius * 2 )));
					
					Thread.sleep((int) ((double) (j + 1) / TRANSITION_NR * 150));
					//updateListener.putRegionToUpdate(new WidgetUpdate(motionArea, new SDLRect(xCenter - maxHorizontalRadius, yCenter - maxVerticalRadius, maxHorizontalRadius *2, maxVerticalRadius * 2 )));

					SDLGfx.filledEllipseRGBA(destSurface, xCenter, yCenter, maxHorizontalRadius / TRANSITION_NR * j,
											 maxVerticalRadius, 255, 255, 255 ,255);
					updateListener.putRegionToUpdate(new WidgetUpdate(motionArea, new SDLRect(xCenter - maxHorizontalRadius, yCenter - maxVerticalRadius,
																							  maxHorizontalRadius *2, maxVerticalRadius * 2 )));	
				}
				r ^= 255;
				g ^= 255;
				for (int k = 0; k < TRANSITION_NR + 1; k++) {
					SDLGfx.filledEllipseRGBA(destSurface, xCenter, yCenter, maxHorizontalRadius / TRANSITION_NR * k,
											 maxVerticalRadius, r, g, b, 255);
					updateListener.putRegionToUpdate(new WidgetUpdate(motionArea, new SDLRect(xCenter - maxHorizontalRadius, yCenter - maxVerticalRadius,
																							  maxHorizontalRadius *2, maxVerticalRadius * 2 )));
					Thread.sleep( (int) ((double) (k + 1) / TRANSITION_NR * 150));
				}
				Thread.sleep(1500);
			}
			
			destSurface = new SDLSurface(originalSurface.getSwigSurface());
			updateListener.putRegionToUpdate(new WidgetUpdate(motionArea, new SDLRect(xCenter - maxHorizontalRadius, yCenter - maxVerticalRadius,
																					  maxHorizontalRadius *2, maxVerticalRadius * 2 )));
			Thread.sleep(1000);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		} 
		displayArea(anotherArea);
	}
}
