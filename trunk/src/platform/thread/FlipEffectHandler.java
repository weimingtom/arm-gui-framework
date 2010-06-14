package platform.thread;

import platform.gui.Area;
import platform.gui.Screen;
import platform.util.UpdateListener;
import platform.util.WidgetUpdate;
import sdljava.SDLException;
import sdljava.video.SDLRect;
import sdljava.video.SDLSurface;
import sdljavax.gfx.SDLGfx;

public class FlipEffectHandler extends Thread{

	final int TRANSITION_NR=10;
	Area motionArea, anotherArea;
	UpdateListener updateListener;
	SDLSurface destSurface, originalSurface;
	int xCenter, yCenter, maxHorizontalRadius, maxVerticalRadius;
	
	public FlipEffectHandler(Area area) throws SDLException{
		
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
	
	public void run(){
		int r=255,g=0,b=0;
		int i=0;
				
		try{
		
			
			while( i++ < 4){
				
				
				//SDLGfx.filledEllipseRGBA(destSurface, xCenter, yCenter, maxHorizontalRadius , maxVerticalRadius, 10, 10, 10 ,255);
				//updateListener.putRegionToUpdate(new WidgetUpdate(motionArea, new SDLRect(xCenter - maxHorizontalRadius, yCenter - maxVerticalRadius, maxHorizontalRadius *2, maxVerticalRadius * 2 )));
				for(int j=TRANSITION_NR; j > (-1) ; j--){
					
					//parametr alpha 0 to pełna przeżroczystość
					SDLGfx.filledEllipseRGBA(destSurface, xCenter, yCenter, maxHorizontalRadius / TRANSITION_NR * j, maxVerticalRadius, r, g, b ,255);
					
					updateListener.putRegionToUpdate(new WidgetUpdate(motionArea, new SDLRect(xCenter - maxHorizontalRadius, yCenter - maxVerticalRadius, maxHorizontalRadius *2, maxVerticalRadius * 2 )));
					
					Thread.sleep( (int)((double)(j + 1) / TRANSITION_NR * 150));
					//updateListener.putRegionToUpdate(new WidgetUpdate(motionArea, new SDLRect(xCenter - maxHorizontalRadius, yCenter - maxVerticalRadius, maxHorizontalRadius *2, maxVerticalRadius * 2 )));

					SDLGfx.filledEllipseRGBA(destSurface, xCenter, yCenter, maxHorizontalRadius / TRANSITION_NR * j, maxVerticalRadius, 255, 255, 255 ,255);
					updateListener.putRegionToUpdate(new WidgetUpdate(motionArea, new SDLRect(xCenter - maxHorizontalRadius, yCenter - maxVerticalRadius, maxHorizontalRadius *2, maxVerticalRadius * 2 )));	
					
				}
				r^=255;
				g^=255;
				for(int k=0; k < TRANSITION_NR + 1; k++){
					SDLGfx.filledEllipseRGBA(destSurface, xCenter, yCenter, maxHorizontalRadius / TRANSITION_NR * k, maxVerticalRadius, r, g, b, 255);
					updateListener.putRegionToUpdate(new WidgetUpdate(motionArea, new SDLRect(xCenter - maxHorizontalRadius, yCenter - maxVerticalRadius, maxHorizontalRadius *2, maxVerticalRadius * 2 )));
					Thread.sleep( (int)((double) (k + 1) / TRANSITION_NR * 150));
				}
				Thread.sleep(1500);
			}
			
			destSurface = new SDLSurface(originalSurface.getSwigSurface());
			updateListener.putRegionToUpdate(new WidgetUpdate(motionArea, new SDLRect(xCenter - maxHorizontalRadius, yCenter - maxVerticalRadius, maxHorizontalRadius *2, maxVerticalRadius * 2 )));
			Thread.sleep(1000);
		}
		catch(InterruptedException e){
			e.printStackTrace();
		} 
		displayArea(anotherArea);
				
	}
	
	private void displayArea(Area display){
		int x=250;
		
		while( x > 100){
			
			try {
				display.setAlpha(255-x);
				Thread.sleep(300);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			x-=5;
		}
		display.setAlpha(255);
	}
	
}
