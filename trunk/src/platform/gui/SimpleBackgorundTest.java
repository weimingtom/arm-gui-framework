package platform.gui;

import java.io.File;

import sdljava.SDLException;
import sdljava.SDLMain;
import sdljava.SDLTimer;
import sdljavax.guichan.GUIException;
import sdljavax.guichan.gfx.Image;
import sdljavax.guichan.gfx.ImageLoader;
import sdljavax.guichan.sdl.SDLGraphics;
import sdljavax.guichan.sdl.SDLImageLoader;

public class SimpleBackgorundTest {

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			Area backgroundArea = null;
			Area foregroundArea = null;
			
			
			try {
				SDLMain.init(SDLMain.SDL_INIT_VIDEO);
				
				Screen screen = Screen.getScreen();
				ImageLoader imageLoader = new SDLImageLoader();
				
				Image.setImageLoader(imageLoader);
				
								
			    backgroundArea = new Area( new String("resource" + File.separator + "tree_small.png" ), new SDLGraphics() );

			    foregroundArea = new Area( new String("resource" + File.separator + "islands_small.png" ), new SDLGraphics() );

			    //TODO implement new ImageLoader based on SDLImageLoader
			    //Image image = new Image(new String("resource" + File.separator + "tree_small.png" ));
			    //backgroundArea = new Area( image , new SDLGraphics() );

				screen.setBackground(backgroundArea);
				screen.getBackground().refreshArea();
				
				screen.setForeground(foregroundArea);
				screen.setAreaAlpha(foregroundArea, 0);
				screen.getForeground().refreshArea();
				
				int i =0;
				while(i<255){
					
					i+=10;
					screen.setAreaAlpha(foregroundArea, i);
					screen.getForeground().refreshArea();
					SDLTimer.delay(500);
				}
				screen.getBackground().refreshArea();
				SDLTimer.delay(2000);
				
				
				
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
