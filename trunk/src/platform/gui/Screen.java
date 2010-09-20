package platform.gui;
/**
*  arm-gui-framework -Java GUI based on sdljava for omap5912 board
*  Copyright (C) 2010  Bartosz Kędra (bartosz.kedra@gmail.com)
* 
*  This library is free software; you can redistribute it and/or
*  modify it under the terms of the GNU Lesser General Public
*  License as published by the Free Software Foundation; either
*  version 3.0 of the License, or (at your option) any later version.
* 
*  This library is distributed in the hope that it will be useful,
*  but WITHOUT ANY WARRANTY; without even the implied warranty of
*  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
*  Lesser General Public License for more details.
* 
*  You should have received a copy of the GNU Lesser General Public
*  License along with this library; if not, write to the Free Software
*  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
*  USA
*
*/

import platform.evt.ExtendedInput;
import platform.gfx.UnifiedGraphics;
import platform.sdl.SDLGraphics;
import platform.sdl.SDLInput;
import platform.thread.EventCapturer;
import platform.thread.EventDispatcher;
import platform.util.Active;
import sdljava.SDLException;
import sdljava.video.SDLSurface;
import sdljava.video.SDLVideo;
import sdljavax.guichan.GUIException;

/**
 * Class representing display - it may contain numerous layers - areas to display widgets on many surfaces
 * @author Bartosz Kędra
 * @author bartosz.kedra@gmail.com
 *
 */
public class Screen {

	/**
	 * screen width in pixels
	 */
	public static final int _screenWidth = 320;
	
	/**
	 * screen height in pixels
	 */
	public static final int _screenHeight = 240;
	
	/**
	 * flags for surface creation - they are double-buffered for reasons of efficiency
	 */
	public static final long _screenflags = SDLVideo.SDL_HWSURFACE | SDLVideo.SDL_DOUBLEBUF | SDLVideo.SDL_HWACCEL | SDLVideo.SDL_ANYFORMAT ;
	
	/**
	 * flags for transparency effect
	 */
	public static final long _alphaFlags = SDLVideo.SDL_SRCALPHA | SDLVideo.SDL_RLEACCEL ; 
	
	/**
	 * single instance of screen, it is singleton
	 */
	private static Screen _soleInstance = null;
 	
	/**
	 * Get an instance of screen
	 * @return Screen instance
	 * @throws SDLException
	 */
	public static Screen getScreen() throws SDLException {	
		if (_soleInstance == null) {
			synchronized (Screen.class) {
				if (_soleInstance == null) {
					_soleInstance = new Screen();
				}
			}
		}
			return _soleInstance;
	}
	
	/**
	 * Area object for handling one surface
	 */
	private Area foreground;
	
	/**
	 * Area object for handling second surface
	 */
	private Area background;
	
	/**
	 * Enum indicating which surface - background of foreground is active
	 */
	private Active active;
	
	/**
	 * Input source - this where the events come from
	 */
	private ExtendedInput inputSource;
	
	/**
	 * Screen surface - main surface where everything is drawn 
	 */
	private SDLSurface target;
	
	/**
	 * Object capable of drawing to screen surface
	 */
	private UnifiedGraphics graphics;
	
	/**
	 * flag indicating whether application is running or not
	 */
	private volatile boolean running;
	
	/**
	 * Private constructor
	 * @throws SDLException
	 */
	private Screen() throws SDLException {	
		//TODO change that for normal variables ?
		target = SDLVideo.setVideoMode(Screen._screenWidth, Screen._screenHeight, 0, Screen._screenflags);
		graphics = new SDLGraphics();
		graphics.setTarget(target);
		
		inputSource = new SDLInput();
		running = true;
		
	}

	/**
	 * Cleans any dynamically reserved memory areas in C style 
	 * @throws GUIException
	 */
	public void delete() throws SDLException, GUIException {
		target.freeSurface();
		foreground.delete();
		background.delete();
	}
		
	/**
	 * Get name of active area -layer
	 * @return enum of an active area
	 */
	public Active getActive() {
		return active;
	}

	/**
	 * Get background area
	 * @return backgroudn area
	 */
	public Area getBackground() {
		return background;
	}

	/**
	 * Get foreground area
	 * @return foreground area
	 */
	public Area getForeground() {
		return foreground;
	}

	/**
	 * Get surface drawing object
	 * @return surface drawing object
	 */
	public UnifiedGraphics getGraphics() {
		return graphics;
	}

	/**
	 * Get running flag
	 * @return true if running, false if stoped
	 */
	public boolean isRunning() {
		return running;
	}

	/**
	 * Refresh the screen, it flips the buffers
	 * @throws SDLException
	 */
	public synchronized void refresh() throws SDLException {
		target.flip();
	}

	/**
	 * Set which surface should be active
	 * @param active surface name
	 */
	public void setActive(Active active) {
		this.active = active;
		
		if (active == Active.BACKGROUND) {
			background.setActive(true);
			foreground.setActive(false);
		} else {
			background.setActive(false);
			foreground.setActive(true);
		}
	}

	/**
	 * Set screen areas - background and foreground, from now on the events are handled
	 * must be called to receive events
	 * @param background
	 * 			background area
	 * @param foreground
	 * 			foreground area
	 * @throws SDLException
	 */
	public void setAreas(Area background, Area foreground) throws SDLException {		
		this.background = background;
		this.foreground = foreground;
		
		setActive(Active.BACKGROUND);
		startEventHandling();
	}

	/**
	 * Set background area
	 * @param background 
	 * 			background area
	 */
	public void setBackground(Area background) {
		this.background = background;
	}
	
	/**
	 * Set foreground area
	 * @param foreground
	 * 			foreground area
	 */
	public void setForeground(Area foreground) {
		this.foreground = foreground;
	}
	
	/**
	 * Set object capable of drawing surfaces
	 * @param graphics
	 */
	public void setGraphics(UnifiedGraphics graphics) {
		this.graphics = graphics;
	}
	
	/**
	 * Set running status
	 * @param running
	 * 			running status
	 */
	public void setRunning(boolean running) {
		this.running = running;
	}
	
	/**
	 * Start event handling, called from setAreas method
	 * @throws SDLException
	 */
	private void startEventHandling() throws SDLException {
		EventDispatcher eventDispatcher = new EventDispatcher(inputSource );
		new EventCapturer(inputSource, eventDispatcher);
	}
}
