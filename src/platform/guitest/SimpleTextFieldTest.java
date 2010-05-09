package platform.guitest;

import java.io.File;

import platform.gui.Area;
import platform.gui.Label;
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
	 * @throws SDLException 
	 * @throws GUIException 
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
		   
		    //TextField googleSearch = new TextField("google");
		    //TextField googleSearch = new TextField();
		    //backgroundArea.add(googleSearch,4);
		    Panel panel= new Panel(4,1);
		     	
		    backgroundArea.add(panel, 4);
		    
		    Label label = new Label ("David Guetta", "One Love", 160, 35);
		    panel.add(label, 1);	    
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
		} finally{
			screen.delete();
			SDLTTF.quit();
			SDLMain.quit();
			System.exit(0);					
		}
		
		

	}

}
