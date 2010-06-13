package test;

import java.io.File;

import platform.gui.Area;
import platform.gui.Button;
import platform.gui.HiddenMenu;
import platform.gui.Label;
import platform.gui.Panel;
import platform.gui.PlatformDropDown;
import platform.gui.PlatformIcon;
import platform.gui.Screen;
import platform.util.Direction;
import platform.util.PlatformListModel;
import sdljava.SDLException;
import sdljava.SDLMain;
import sdljava.ttf.SDLTTF;
import sdljava.video.SDLColor;
import sdljavax.guichan.GUIException;
import sdljavax.guichan.gfx.Image;
import sdljavax.guichan.gfx.ImageLoader;
import sdljavax.guichan.sdl.SDLImageLoader;
import sdljavax.guichan.widgets.ListModel;

public class GUITest {

	/**
	 * @param args
	 * @throws GUIException 
	 * @throws SDLException 
	 */
	public static void main(String[] args) throws SDLException, GUIException {
		Screen screen = null;
		PlatformDropDown dropDown = null;
		
		try {
			SDLMain.init(SDLMain.SDL_INIT_VIDEO);
			SDLTTF.init();
			
			ImageLoader imageLoader = new SDLImageLoader();
			Image.setImageLoader(imageLoader);
			
			screen=Screen.getScreen();
			
			Area backgroundArea = new Area( new String("resource" + File.separator + "wallpapers"+ File.separator + "tree_small.png" ),5,4);
		    		    			    
			Image image = new Image(new String("resource" + File.separator + "wallpapers" + File.separator + "white.png" ));
		    Area foregroundArea = new Area( image,5,5);
		    foregroundArea.setAlpha(0);
		    
		    Panel panel= new Panel(4,1);
	     	backgroundArea.add(panel, 10);
		    
		    Button playButton = new Button("resource" + File.separator + "PNG" + File.separator);
		    Label label = new Label ("Flip Effect", "Two ellipses flipping", 160, 35);
		    panel.add(playButton,0);
		    panel.add(label, 1);
		    panel.setAlpha(180);
		    HiddenMenu hiddenMenu = new HiddenMenu(new SDLColor(100,100,100), Direction.SOUTH);
			   
			backgroundArea.add(hiddenMenu,15);
			   
			PlatformIcon icon1 = new PlatformIcon(new Image(new String("resource" + File.separator + "images" + File.separator + "gmail.png")));
			PlatformIcon icon2 = new PlatformIcon(new Image(new String("resource" + File.separator + "images" + File.separator + "browser.png")));	
			   
			hiddenMenu.add(icon1,0);
			hiddenMenu.add(icon2,1);
			
			
			ListModel listModel= new PlatformListModel(new String[]{"zero","one","two","three", "four", "five", "six"});
			
		    dropDown = new PlatformDropDown(listModel);
		    
		    backgroundArea.add(dropDown, 0);
		    
			screen.setAreas(backgroundArea, foregroundArea);
					
			while(screen.isRunning()){
											
				Thread.sleep(1000);
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
			
			screen.delete();
			
			SDLMain.quit();
			System.exit(0);					
		}

	}

}
