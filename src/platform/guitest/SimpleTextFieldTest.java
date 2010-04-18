package platform.guitest;

import java.io.File;

import platform.gui.Area;
import platform.gui.Panel;
import platform.gui.PlatformIcon;
import platform.gui.Screen;
import platform.gui.TextField;
import sdljava.SDLException;
import sdljava.SDLMain;
import sdljava.SDLTimer;
import sdljava.ttf.SDLTTF;
import sdljavax.guichan.GUIException;
import sdljavax.guichan.evt.Input;
import sdljavax.guichan.gfx.Image;
import sdljavax.guichan.gfx.ImageLoader;
import sdljavax.guichan.sdl.SDLImageLoader;
import sdljavax.guichan.sdl.SDLInput;

public class SimpleTextFieldTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			SDLMain.init(SDLMain.SDL_INIT_VIDEO);
			SDLTTF.init();
			
			Screen screen = Screen.getScreen();
			ImageLoader imageLoader = new SDLImageLoader();
			
			Image.setImageLoader(imageLoader);
			Input input = new SDLInput();
			
			screen.setInputSource(input);
			
			Area backgroundArea = new Area( new String("resource" + File.separator + "wallpapers"+ File.separator + "tree_small.png" ),5,4);
		    		    			    
			Image image = new Image(new String("resource" + File.separator + "wallpapers" + File.separator + "islands_small.png" ));
		    Area foregroundArea = new Area( image,5,4);
		
		    TextField googleSearch = new TextField("google");
		    
		    
		    Panel panel = new Panel(2,2);
		    panel.add(googleSearch, 0);
		    backgroundArea.add(panel, 6);
		    
		    
		    
		    		    
			screen.setBackground(backgroundArea);
			screen.setForeground(foregroundArea);
			foregroundArea.setAlpha(0);
			
		
			while(screen.isRunning()){
				
				screen.refresh();
				System.out.println("Screen refresh");
				
				SDLTimer.delay(1000);
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

	}

}
