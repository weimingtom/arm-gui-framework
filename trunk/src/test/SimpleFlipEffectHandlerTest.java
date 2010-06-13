package test;

import java.io.File;

import platform.gui.Area;
import platform.gui.Screen;
import platform.thread.FlipEffectHandler;
import sdljava.SDLException;
import sdljava.SDLMain;
import sdljava.video.SDLColor;
import sdljavax.guichan.GUIException;
import sdljavax.guichan.gfx.Image;
import sdljavax.guichan.gfx.ImageLoader;
import sdljavax.guichan.sdl.SDLImageLoader;

public class SimpleFlipEffectHandlerTest {

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
			
			Image white = new Image(new String("resource" + File.separator + "wallpapers" + File.separator + "white.png" ));
			Image image = new Image(new String("resource" + File.separator + "wallpapers" + File.separator + "tree_small.png" ));
			
			Area backgroundArea = new Area( white, 5,5);
			backgroundArea.setAlpha(0);
			
			Area foregroundArea = new Area( image ,5,5);
			  
		   screen.setAreas(backgroundArea, foregroundArea);
			
		   new FlipEffectHandler(backgroundArea);
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
					
			SDLMain.quit();
			System.exit(0);					
		}

	}

}
