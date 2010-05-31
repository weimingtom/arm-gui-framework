package platform.guitest;

import java.io.File;

import platform.gui.Area;
import platform.gui.Button;
import platform.gui.PlatformDropDown;
import platform.gui.Screen;
import platform.util.PlatformListModel;
import sdljava.SDLException;
import sdljava.SDLMain;
import sdljava.ttf.SDLTTF;
import sdljavax.guichan.GUIException;
import sdljavax.guichan.gfx.Image;
import sdljavax.guichan.gfx.ImageLoader;
import sdljavax.guichan.sdl.SDLImageLoader;
import sdljavax.guichan.widgets.ListModel;

public class SimpleDropDownMenuTest {

	/**
	 * @param args
	 * @throws GUIException 
	 * @throws SDLException 
	 */
	public static void main(String[] args) throws SDLException, GUIException {
		
		Screen screen = null;
		Area backgroundArea = null;
		Area foregroundArea = null;
		PlatformDropDown dropDown = null;
		Button playButton = null;
		
		int i=0;
		
		try {
			SDLMain.init(SDLMain.SDL_INIT_VIDEO);
			SDLTTF.init();
			
			ImageLoader imageLoader = new SDLImageLoader();
			Image.setImageLoader(imageLoader);
			
					
			screen=Screen.getScreen();
			
			backgroundArea = new Area( new String("resource" + File.separator + "wallpapers"+ File.separator + "tree_small.png" ),4,4);
		    		    			    
			Image image = new Image(new String("resource" + File.separator + "wallpapers" + File.separator + "islands_small.png" ));
		    foregroundArea = new Area( image,4,4);
		    foregroundArea.setAlpha(i);
		   	   							
		   ListModel listModel= new PlatformListModel(new String[]{"zero","one","two","three", "four"});
		
		    dropDown = new PlatformDropDown(listModel);
		    
		    backgroundArea.add(dropDown, 0);
		
		    playButton = new Button("resource" + File.separator + "PNG" + File.separator);
		    backgroundArea.add(playButton, 4);
		    
		    screen.setAreas(backgroundArea, foregroundArea);
		    
		while(screen.isRunning()){
			//foregroundArea.setAlpha(i%255);
			//i+=5;
			Thread.sleep(300);
			
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
	}
	catch (Exception e){
		e.printStackTrace();
	}
	finally{
	
		
		
		SDLMain.quit();
		System.exit(0);					
	}
	}

}
