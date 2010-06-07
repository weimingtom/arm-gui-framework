package test;

import java.io.File;

import platform.gui.Area;
import platform.gui.PlatformIcon;
import platform.gui.Screen;
import platform.thread.CurveMotionHandler;
import sdljava.SDLException;
import sdljava.SDLMain;
import sdljava.video.SDLColor;
import sdljavax.guichan.GUIException;
import sdljavax.guichan.gfx.Image;
import sdljavax.guichan.gfx.ImageLoader;
import sdljavax.guichan.sdl.SDLImageLoader;

public class SimpleCurveMotionHandlerTest {

	/**
	 * @param args
	 * @throws GUIException 
	 * @throws SDLException 
	 */
	public static void main(String[] args) throws SDLException, GUIException {
		Screen screen = null;
		
		
		try {
			SDLMain.init(SDLMain.SDL_INIT_VIDEO);
						
			ImageLoader imageLoader = new SDLImageLoader();
			Image.setImageLoader(imageLoader);
			
			
			screen=Screen.getScreen();
			
			Image image = new Image(new String("resource" + File.separator + "wallpapers" + File.separator + "islands_small.png" ));
		    Area backgroundArea = new Area( image,5,5);
		    
			Area foregroundArea = new Area( new SDLColor(0,0,0) ,5,5);
		    		    			    
			foregroundArea.setAlpha(0);
		    
		   
		   PlatformIcon icon1 = new PlatformIcon(new Image(new String("resource" + File.separator + "images" + File.separator + "gmail2.png")));
		   PlatformIcon icon2 = new PlatformIcon(new Image(new String("resource" + File.separator + "images" + File.separator + "music.png")));	
		   PlatformIcon icon3 = new PlatformIcon(new Image(new String("resource" + File.separator + "images" + File.separator + "youtube.png")));
		   PlatformIcon icon4 = new PlatformIcon(new Image(new String("resource" + File.separator + "images" + File.separator + "camera.png")));
		   PlatformIcon icon5 = new PlatformIcon(new Image(new String("resource" + File.separator + "images" + File.separator + "dial.png")));
		  /* PlatformIcon icon6 = new PlatformIcon(new Image(new String("resource" + File.separator + "images" + File.separator + "calendar.png")));
		   PlatformIcon icon7 = new PlatformIcon(new Image(new String("resource" + File.separator + "images" + File.separator + "calculator.png")));
		   PlatformIcon icon8 = new PlatformIcon(new Image(new String("resource" + File.separator + "images" + File.separator + "browser.png")));*/
		   
		   backgroundArea.add(icon1,5);
		  backgroundArea.add(icon2,10);
		  backgroundArea.add(icon3,15); 
		  backgroundArea.add(icon4,20);
		  backgroundArea.add(icon5,4);
		  /*backgroundArea.add(icon6,9);
		  backgroundArea.add(icon7,14);
		  backgroundArea.add(icon8,8);*/
		  
		   screen.setAreas(backgroundArea, foregroundArea);
			
		   new CurveMotionHandler(backgroundArea);
			while(screen.isRunning()){
											
				Thread.sleep(1500);
			}
			
			screen.delete();
		} catch (SDLException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		} catch (GUIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			
			screen.delete();
			
			SDLMain.quit();
			System.exit(0);					
		}

	}

}
