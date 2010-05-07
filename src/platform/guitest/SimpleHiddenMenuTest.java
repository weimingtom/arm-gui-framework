package platform.guitest;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

import platform.gui.Area;
import platform.gui.HiddenMenu;
import platform.gui.PlatformIcon;
import platform.gui.Screen;
import platform.util.Direction;
import sdljava.SDLException;
import sdljava.SDLMain;
import sdljava.video.SDLColor;
import sdljavax.guichan.GUIException;
import sdljavax.guichan.evt.Input;
import sdljavax.guichan.gfx.Image;
import sdljavax.guichan.gfx.ImageLoader;
import sdljavax.guichan.sdl.SDLImageLoader;
import sdljavax.guichan.sdl.SDLInput;

public class SimpleHiddenMenuTest {

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
			
			Input input = new SDLInput();
			
			screen=Screen.getScreen();
			screen.setInputSource(input);
			
			Area backgroundArea = new Area( new String("resource" + File.separator + "wallpapers"+ File.separator + "tree_small.png" ),4,4);
		    		    			    
			Image image = new Image(new String("resource" + File.separator + "wallpapers" + File.separator + "islands_small.png" ));
		    Area foregroundArea = new Area( image,4,4);
		    foregroundArea.setAlpha(0);
		    
		   HiddenMenu hiddenMenu = new HiddenMenu(new SDLColor(0,0,0), Direction.NORTH);
		   
		   backgroundArea.add(hiddenMenu,0);
		   
		   PlatformIcon icon1 = new PlatformIcon(new Image(new String("resource" + File.separator + "images" + File.separator + "gmail_small.png")));
		   PlatformIcon icon2 = new PlatformIcon(new Image(new String("resource" + File.separator + "images" + File.separator + "browser_small.png")));	
		   
		   hiddenMenu.addIcon(icon1);
		   hiddenMenu.addIcon(icon2);
		   
		   screen.setAreas(backgroundArea, foregroundArea);
			
			while(screen.isRunning()){
											
				Thread.sleep(200);
			}
			
			
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
