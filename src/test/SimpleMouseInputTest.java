package test;

import java.io.File;

import platform.gui.Area;
import platform.gui.Button;
import platform.gui.Screen;
import sdljava.SDLException;
import sdljava.SDLMain;
import sdljavax.guichan.GUIException;
import sdljavax.guichan.gfx.Image;
import sdljavax.guichan.gfx.ImageLoader;
import sdljavax.guichan.sdl.SDLImageLoader;

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
			
			screen=Screen.getScreen();
			
			Area backgroundArea = new Area( new String("resource" + File.separator + "wallpapers"+ File.separator + "tree_small.png" ),5,4);
		    		    			    
			Image image = new Image(new String("resource" + File.separator + "wallpapers" + File.separator + "islands_small.png" ));
		    Area foregroundArea = new Area( image,5,4);
		    foregroundArea.setAlpha(0);
		    
		    Button playButton = new Button("resource" + File.separator + "PNG" + File.separator, 0);
		    backgroundArea.add(playButton, 6);
		    		    
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
