package platform.guitest;

import java.io.File;

import platform.gui.Area;
import platform.gui.PlayButton;
import platform.gui.Screen;
import sdljava.SDLException;
import sdljava.SDLMain;
import sdljavax.guichan.GUIException;
import sdljavax.guichan.evt.Input;
import sdljavax.guichan.gfx.Image;
import sdljavax.guichan.gfx.ImageLoader;
import sdljavax.guichan.sdl.SDLImageLoader;
import sdljavax.guichan.sdl.SDLInput;

public class SimpleMouseInputTest {

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
			
			Area backgroundArea = new Area( new String("resource" + File.separator + "wallpapers"+ File.separator + "tree_small.png" ),5,4);
		    		    			    
			Image image = new Image(new String("resource" + File.separator + "wallpapers" + File.separator + "islands_small.png" ));
		    Area foregroundArea = new Area( image,5,4);
		
		    
		    PlayButton playButton = new PlayButton();
		    backgroundArea.add(playButton, 6);
		    		    
			screen.setAreas(backgroundArea, foregroundArea);
			foregroundArea.setAlpha(0);
			
		
			while(screen.isRunning()){
				
				screen.refresh();
				
				Thread.sleep(200);
			}
			screen.delete();
			SDLMain.quit();
			
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
