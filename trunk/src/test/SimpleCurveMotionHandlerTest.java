package test;

import java.io.File;

import platform.gui.Area;
import platform.gui.PlatformIcon;
import platform.gui.Screen;
import platform.thread.CurveMotionHandler;
import sdljava.SDLException;
import sdljava.SDLMain;
import sdljavax.guichan.GUIException;
import sdljavax.guichan.gfx.Image;
import sdljavax.guichan.gfx.ImageLoader;
import sdljavax.guichan.sdl.SDLImageLoader;

public class SimpleCurveMotionHandlerTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
Screen screen = null;
		
		
		try {
			SDLMain.init(SDLMain.SDL_INIT_VIDEO);
						
			ImageLoader imageLoader = new SDLImageLoader();
			Image.setImageLoader(imageLoader);
			
			
			screen=Screen.getScreen();
			
			Area backgroundArea = new Area( new String("resource" + File.separator + "wallpapers"+ File.separator + "tree_small.png" ),5,5);
		    		    			    
			Image image = new Image(new String("resource" + File.separator + "wallpapers" + File.separator + "islands_small.png" ));
		    Area foregroundArea = new Area( image,4,4);
		    foregroundArea.setAlpha(0);
		    
		   
		   PlatformIcon icon1 = new PlatformIcon(new Image(new String("resource" + File.separator + "images" + File.separator + "gmail.png")));
		   PlatformIcon icon2 = new PlatformIcon(new Image(new String("resource" + File.separator + "images" + File.separator + "music.png")));	
		   
		   backgroundArea.add(icon1,5);
		  backgroundArea.add(icon2,10);
		   
		   screen.setAreas(backgroundArea, foregroundArea);
			
		   new CurveMotionHandler(backgroundArea);
			while(screen.isRunning()){
											
				Thread.sleep(200);
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
			
			//screen.delete();
			
			SDLMain.quit();
			System.exit(0);					
		}

	}

}
