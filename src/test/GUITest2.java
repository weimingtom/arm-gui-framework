package test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import platform.gui.Area;
import platform.gui.Button;
import platform.gui.Label;
import platform.gui.Panel;
import platform.gui.PlatformIcon;
import platform.gui.Screen;
import platform.gui.TextField;
import platform.thread.CurveMotionHandler;
import sdljava.SDLException;
import sdljava.SDLMain;
import sdljava.ttf.SDLTTF;
import sdljavax.guichan.GUIException;
import sdljavax.guichan.gfx.Image;
import sdljavax.guichan.gfx.ImageLoader;
import sdljavax.guichan.sdl.SDLImageLoader;

public class GUITest2 {

	/**
	 * @param args
	 * @throws SDLException 
	 */
	public static void main(String[] args) throws SDLException {
		Screen screen = null;
				
		try {
			SDLMain.init(SDLMain.SDL_INIT_VIDEO);
			SDLTTF.init();
			
			ImageLoader imageLoader = new SDLImageLoader();
			Image.setImageLoader(imageLoader);
				
			screen=Screen.getScreen();
				
			Area backgroundArea = new Area( new String("resource" + File.separator + "wallpapers"+ File.separator + "android-wallpapers-320-240-dailymobile008.png" ),5,4);
		    		    			    
			Area foregroundArea = new Area( new String("resource" + File.separator + "wallpapers" + File.separator + "black.png" ),5,5);
		    foregroundArea.setAlpha(0);
		    
		    Panel panel= new Panel(4,1);
		    backgroundArea.add(panel, 15);
		    
		    Button playButton = new Button("resource" + File.separator + "PNG" + File.separator,2);
		    Label label = new Label ("Curve motion effect", "Imaginary curve", 160, 35);
		    panel.add(playButton,0);
		    panel.add(label, 1);	    
		    panel.setAlpha(210);
			
		    
		    TextField googleSearch = new TextField("google");
		    backgroundArea.add(googleSearch,0);
		    
		    PlatformIcon icon1 = new PlatformIcon(new Image(new String("resource" + File.separator + "images" + File.separator + "settings.png")));
			PlatformIcon icon2 = new PlatformIcon(new Image(new String("resource" + File.separator + "images" + File.separator + "messaging.png")));	
			
			backgroundArea.add(icon1, 10);
			backgroundArea.add(icon2, 11);
			
		    screen.setAreas(backgroundArea, foregroundArea);		
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
			
			SDLTTF.quit();
			SDLMain.quit();
			System.exit(0);					
		}
	}

}
