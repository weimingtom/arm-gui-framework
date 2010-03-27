package platform.guitest;

import java.io.File;

import platform.gui.Area;
import platform.gui.Screen;
import sdljava.SDLException;
import sdljava.SDLMain;
import sdljava.SDLTimer;
import sdljavax.guichan.GUIException;
import sdljavax.guichan.gfx.Image;
import sdljavax.guichan.gfx.ImageLoader;
import sdljavax.guichan.sdl.SDLImageLoader;

public class SimpleBackgroundTest {

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
				
								
			    backgroundArea = new Area( new String("resource" + File.separator + "tree_small.png" ));
			    		    			    
			   // foregroundArea = new Area( new String("resource" + File.separator + "islands_small.png" ), screen.getGraphics() );
			    //TODO implement new ImageLoader based on SDLImageLoader
			    Image image = new Image(new String("resource" + File.separator + "islands_small.png" ));
			    foregroundArea = new Area( image);

			    //foregroundArea = new Area(new SDLColor(100,100,100) , screen.getGraphics());
				screen.setBackground(backgroundArea);
				screen.setForeground(foregroundArea);
				
								
				int i = 0;
				while(i<255){
					
					i+=5;
					screen.setAreaAlpha(foregroundArea, i);
					screen.refresh();
					
					
					SDLTimer.delay(100);
				}
				
				screen.refresh();						
				SDLTimer.delay(100);
				
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
