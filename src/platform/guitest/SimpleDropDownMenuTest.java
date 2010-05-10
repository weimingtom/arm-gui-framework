package platform.guitest;

import java.io.File;

import platform.gui.Area;
import platform.gui.PlatformDropDown;
import platform.gui.PlayButton;
import platform.gui.Screen;
import sdljava.SDLException;
import sdljava.SDLMain;
import sdljava.ttf.SDLTTF;
import sdljavax.guichan.GUIException;
import sdljavax.guichan.evt.Input;
import sdljavax.guichan.gfx.Image;
import sdljavax.guichan.gfx.ImageLoader;
import sdljavax.guichan.sdl.SDLImageLoader;
import sdljavax.guichan.sdl.SDLInput;
import sdljavax.guichan.test.sdlwidgets.DemoListModel;
import sdljavax.guichan.widgets.DropDown;
import sdljavax.guichan.widgets.ListBox;

public class SimpleDropDownMenuTest {

	/**
	 * @param args
	 * @throws GUIException 
	 * @throws SDLException 
	 */
	public static void main(String[] args) throws SDLException, GUIException {
		
		Screen screen = null;
		int i=0;
		
		try {
			SDLMain.init(SDLMain.SDL_INIT_VIDEO);
			SDLTTF.init();
			
			ImageLoader imageLoader = new SDLImageLoader();
			Image.setImageLoader(imageLoader);
			
			Input input = new SDLInput();
			
			screen=Screen.getScreen();
			screen.setInputSource(input);
			
			Area backgroundArea = new Area( new String("resource" + File.separator + "wallpapers"+ File.separator + "tree_small.png" ),4,4);
		    		    			    
			Image image = new Image(new String("resource" + File.separator + "wallpapers" + File.separator + "islands_small.png" ));
		    Area foregroundArea = new Area( image,4,4);
		    foregroundArea.setAlpha(i);
		   	   							
		    PlatformDropDown		dropDown;
		    DemoListModel	demoListModel	= new DemoListModel();
		
		    dropDown = new PlatformDropDown(demoListModel);
		    
		    backgroundArea.add(dropDown, 0);
		
		    PlayButton playButton = new PlayButton();
		    backgroundArea.add(playButton, 6);
		    
		    screen.setAreas(backgroundArea, foregroundArea);
		    
		while(screen.isRunning()){
			//foregroundArea.setAlpha(i%255);
			//i+=5;
			Thread.sleep(300);
			
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
	}
	catch (Exception e){
		e.printStackTrace();
	}
	finally{
		System.out.println("delete");
		screen.delete();
		
		SDLMain.quit();
		System.exit(0);					
	}
	}

}
