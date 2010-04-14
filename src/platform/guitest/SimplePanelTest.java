package platform.guitest;

import java.io.File;

import platform.gui.Area;
import platform.gui.Panel;
import platform.gui.PlatformIcon;
import platform.gui.Screen;
import sdljava.SDLException;
import sdljava.SDLMain;
import sdljava.SDLTimer;
import sdljavax.guichan.GUIException;
import sdljavax.guichan.gfx.Image;
import sdljavax.guichan.gfx.ImageLoader;
import sdljavax.guichan.sdl.SDLImageLoader;

public class SimplePanelTest {

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
			
			
			backgroundArea = new Area( new String("resource" + File.separator + "wallpapers"+ File.separator + "tree_small.png" ),5,4);
		    		    			    
			Image image = new Image(new String("resource" + File.separator + "wallpapers" + File.separator + "islands_small.png" ));
		    foregroundArea = new Area( image,5,4);
		    
			//PlatformIcon icon = new PlatformIcon(new Image(new String("resource" + File.separator + "images" + File.separator + "youtube_small.png")));
		    PlatformIcon icon = new PlatformIcon(new Image(new String("resource" + File.separator + "PNG" + File.separator + "2x2_Widget_Frame_Portrait.png")));

		    icon.setAlpha(80);
		    
		    backgroundArea.add(icon, 0);
		    		    
			screen.setBackground(backgroundArea);
			screen.setForeground(foregroundArea);
			foregroundArea.setAlpha(0);
			
			int i = 0;
			while(i<255){
				
				i+=50;
				//foregroundArea.setAlpha(i);
				screen.refresh();
				
				
				SDLTimer.delay(1000);
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
