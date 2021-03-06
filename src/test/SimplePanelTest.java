package test;

import java.io.File;

import platform.gui.Area;
import platform.gui.Panel;
import platform.gui.PlatformIcon;
import platform.gui.Screen;
import sdljava.SDLException;
import sdljava.SDLMain;
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

				
		try {
			SDLMain.init(SDLMain.SDL_INIT_VIDEO);
													
			Screen screen = Screen.getScreen();
			ImageLoader imageLoader = new SDLImageLoader();
			
			Image.setImageLoader(imageLoader);
			
			
			Area backgroundArea = new Area( new String("resource" + File.separator + "wallpapers"+ File.separator + "tree_small.png" ),5,4);
		    		    			    
			Image image = new Image(new String("resource" + File.separator + "wallpapers" + File.separator + "islands_small.png" ));
		    Area foregroundArea = new Area( image,5,4);
		    foregroundArea.setAlpha(0);
		    
		    
		    Panel panel = new Panel(4,1);
		    backgroundArea.add(panel, 5);
		    panel.setAlpha(210);
		    
		    PlatformIcon icon = new PlatformIcon(new Image(new String("resource" + File.separator + "images" + File.separator + "youtube_small.png")),"");
		    panel.add(icon, 3);
		    
		    PlatformIcon icon2 = new PlatformIcon(new Image(new String("resource" + File.separator + "images" + File.separator + "youtube.png")),"");
		    backgroundArea.add(icon2,0);
		    
			screen.setAreas(backgroundArea, foregroundArea);
						
			int i=0;
			while(Screen.getScreen().isRunning()){
				
				Thread.sleep(2000);
				if(i == 5){
					
					//panel.remove(icon);
					i++;
				}
				else{
					
					i++;
				}

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
		}
		finally{
						
			SDLMain.quit();
			System.exit(0);	
		}
		

	}

}
