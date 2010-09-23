package test;

import java.io.File;

import platform.gui.Area;
import platform.gui.HiddenMenu;
import platform.gui.Label;
import platform.gui.Panel;
import platform.gui.PlatformDropDown;
import platform.gui.PlatformIcon;
import platform.gui.Screen;
import platform.gui.SimplerButton;
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

public class DelphiDemo {

	/**
	 * @param args
	 * @throws SDLException 
	 */
	public static void main(String[] args) throws SDLException {
		Screen screen = null;
		PlatformDropDown dropDown = null;
		
		try {
			SDLMain.init(SDLMain.SDL_INIT_VIDEO);
			SDLTTF.init();
			
			ImageLoader imageLoader = new SDLImageLoader();
			Image.setImageLoader(imageLoader);
				
			screen=Screen.getScreen();
			
			//Image backgroundImage = new Image(new String("resource" + File.separator + "wallpapers"+ File.separator + "tree_small.png" ));
			Area backgroundArea = new Area( new String("resource" + File.separator + "wallpapers"+ File.separator + "camaro_front.png" ), 4, 4);
			
			Area foregroundArea = new Area( new String("resource" + File.separator + "wallpapers" + File.separator + "camaro_rear.png" ),4,4);
		    foregroundArea.setAlpha(0);
		    
		    Panel lowPanel= new Panel(4,1);
		    backgroundArea.add(lowPanel, 12);
		    lowPanel.setAlpha(0);
		    
		    SimplerButton nextButton = new SimplerButton("resource" + File.separator + "PNG" + File.separator, new String[]{"ic_media_next.png", "ic_media_next_selected.png", "ic_media_next_pressed.png"}, "next");
			SimplerButton previousButton = new SimplerButton("resource" + File.separator + "PNG" + File.separator, new String[]{"ic_media_previous.png", "ic_media_previous_selected.png", "ic_media_previous_pressed.png"}, "previous");		    
			SimplerButton playButton = new SimplerButton("resource" + File.separator + "PNG" + File.separator, new String[]{"ic_media_play.png", "ic_media_play_selected.png", "ic_media_play_pressed.png" }, "play");
			playButton.addDemoActionListener2();
			 
			lowPanel.add(playButton, 1.5f);	    
		    lowPanel.add(nextButton, 2.5f);
		    lowPanel.add(previousButton, 0.5f);
		  
			
		    Panel highPanel = new Panel(2,2);
			backgroundArea.add(highPanel, 5);
			highPanel.setAlpha(0);
			
			HiddenMenu hiddenMenu = new HiddenMenu(new SDLColor(100,100,100), Direction.NORTH);
			   
			backgroundArea.add(hiddenMenu,0);
			
			PlatformIcon icon1 = new PlatformIcon(new Image(new String("resource" + File.separator + "images" + File.separator + "music.png")), "music");
		    icon1.addIconDemoActionListener2();
			
		    PlatformIcon icon2 = new PlatformIcon(new Image(new String("resource" + File.separator + "images" + File.separator + "settings.png")), "");
			icon2.addIconDemoActionListener();
			
			hiddenMenu.add(icon1,1);
			hiddenMenu.add(icon2,2);
			
			SimplerButton closeButton = new SimplerButton("resource" + File.separator + "PNG" + File.separator, new String[]{"btn_close_normal.png", "btn_close_selected.png", "btn_close_pressed.png" }, "");
			closeButton.addButtonDemoActionListener();
			foregroundArea.add(closeButton, 12);
				
			ListModel listModel= new PlatformListModel(new String[]{"Sting - Fragile", "Sting - Fields of gold", "Elthon John - Sacrifice", "David Guetta - Memories ","Daft Punk - Crescendolls", "Daft Punk - Technologic", "Basia - Time and Tide", "Basia - New day for You", "Ania Dąbrowska - Bang Bang", "Ania Dąbrowska - Charlie Charlie"});
			dropDown = new PlatformDropDown(listModel);
		    foregroundArea.add(dropDown, 0);
		    
		    Label nameLabel = new Label ("Artist", "Song title", 137, 40, dropDown);
			Label timeLabel = new Label ("Time", "00:00" , 137, 45);
			
			
			highPanel.add(nameLabel, 0);
			highPanel.add(timeLabel, 2);
			
			timeLabel.startCounting();
			dropDown.addActionListener(nameLabel);
			nextButton.addActionListener(nameLabel);
			nextButton.addActionListener(timeLabel);
			playButton.addActionListener(nameLabel);
			icon1.addActionListener(nameLabel);
			
			dropDown.addActionListener(timeLabel);
			previousButton.addActionListener(timeLabel);
			previousButton.addActionListener(nameLabel);
			
			playButton.addActionListener(timeLabel);
			
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
