package platform.guitest;

import java.io.File;

import platform.gui.Area;
import platform.gui.Screen;
import sdljava.SDLException;
import sdljava.SDLMain;
import sdljava.SDLTimer;
import sdljava.ttf.SDLTTF;
import sdljava.video.SDLColor;
import sdljavax.guichan.GUIException;
import sdljavax.guichan.gfx.Image;
import sdljavax.guichan.gfx.ImageLoader;
import sdljavax.guichan.sdl.SDLImageLoader;

public class SimpleBackgroundTest {

	/**
	 * @param args
	 * @throws GUIException 
	 * @throws SDLException 
	 */
	public static void main(String[] args) throws SDLException, GUIException {
		// TODO Auto-generated method stub
			Area backgroundArea = null;
			Area foregroundArea = null;
			Screen screen = null;
			
			try {
				SDLMain.init(SDLMain.SDL_INIT_VIDEO);
				
								
				screen = Screen.getScreen();
				ImageLoader imageLoader = new SDLImageLoader();
				
				Image.setImageLoader(imageLoader);
				
								
			    backgroundArea = new Area( new String("resource" + File.separator + "wallpapers"+ File.separator + "tree_small.png" ));
			    		    			    
			    
			   // Image image = new Image(new String("resource" + File.separator + "wallpapers" + File.separator + "islands_small.png" ));
			   // foregroundArea = new Area( image);

			   foregroundArea = new Area(new SDLColor(200,100,50) ,4, 4);
				
			  screen.setAreas(backgroundArea, foregroundArea);
			   								
				int i = 0;
				while(i<255){
					
					i+=5;
					foregroundArea.setAlpha(i);
				
					screen.refresh();
										
					Thread.sleep(100);
				}
				
				screen.refresh();						
				Thread.sleep(200);
				
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
			finally{
				screen.delete();
				SDLMain.quit();
				System.exit(0);
			}
			
			
	}

}
