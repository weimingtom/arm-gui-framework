package platform.gui;

import java.io.File;

import sdljava.SDLException;
import sdljava.SDLMain;
import sdljava.SDLTimer;
import sdljava.video.SDLColor;
import sdljava.video.SDLSurface;
import sdljava.video.SDLVideo;
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
			SDLSurface surface;
			SDLSurface surface2;
			SDLGraphics graphics;
			
			try {
				SDLMain.init(SDLMain.SDL_INIT_VIDEO);
				
								
				Screen screen = Screen.getScreen();
				ImageLoader imageLoader = new SDLImageLoader();
				
				Image.setImageLoader(imageLoader);
				
								
			    backgroundArea = new Area( new String("resource" + File.separator + "tree_small.png" ), screen.getGraphics() );
			    		    			    
			   // foregroundArea = new Area( new String("resource" + File.separator + "islands_small.png" ), screen.getGraphics() );
			    //TODO implement new ImageLoader based on SDLImageLoader
			    Image image = new Image(new String("resource" + File.separator + "islands_small.png" ));
			    foregroundArea = new Area( image , screen.getGraphics() );

			    foregroundArea = new Area(new SDLColor(100,100,100) , screen.getGraphics());
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
/*
surface = SDLVideo.setVideoMode(Screen._screenWidth, Screen._screenHeight,0 ,Screen._screenflags	);
graphics = new SDLGraphics();
graphics.setTarget(surface);
surface2 = SDLVideo.createRGBSurface(SDLVideo.SDL_HWSURFACE,Screen._screenWidth, Screen._screenHeight, 16 , 0, 0, 0 ,0 );
surface2.fillRect(surface.mapRGB(0 , 250 , 100));

graphics.beginDraw();
graphics.drawSDLSurface(surface2, surface.getRect(),surface.getRect());
graphics.endDraw();
surface.flip();
*/