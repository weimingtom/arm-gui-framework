package platform.evt;

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



import sdljava.event.SDLEvent;
import sdljavax.guichan.evt.Input;

/**
 * Interface extending interface from SDLJava for purpose of ease of coding 
 * @author Bartosz Kędra
 * @author bartosz.kedra@gmail.com
 */

public interface ExtendedInput extends Input {

	/**
	 * Method inserting event in the queue
	 * @param event a <code>SDLEvent</code>
	 */
	public void	pushInput(SDLEvent event);
}
