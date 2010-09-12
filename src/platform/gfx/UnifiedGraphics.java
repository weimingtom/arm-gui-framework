package platform.gfx;

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

import sdljava.SDLException;
import sdljava.video.SDLRect;
import sdljava.video.SDLSurface;
import sdljavax.guichan.gfx.Graphics;

/**
 * An abstract class for merging interface of <code>SDLGraphics</code> and <code> OpenGLGraphics</code> into one class
 * @author Bartosz Kędra
 * @author bartosz.kedra@gmail.com
 */
public abstract class UnifiedGraphics extends Graphics {

	/**
	 * Draw horizontal line
	 * @param x1 
	 * 		  start coordinate of a line
	 * @param y
	 * 		  y coordinate at which line must be drawn
	 * @param x2
	 * 		  end coordinate of a line
	 */
	protected void	drawHLine(int x1, int y, int x2) {
		throw new UnsupportedOperationException();
	}
	
	
	/**
	 * Draws SDL surface 
	 * @param surface 
	 * 			a <code>SDLSurface</code> from which to draw
	 * @param source
	 * 			a rectangular representation of a part of surface that must be drawn
	 * @param destination
	 * 			a rectangular representation of a part of target surface where to draw
	 * @throws SDLException
	 */
	public void	drawSDLSurface(SDLSurface surface, SDLRect source, SDLRect destination) throws SDLException {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Draw vertical line
	 * @param x 
	 * 		  x coordinate at which line must be drawn
	 * @param y1
	 * 
	 * @param y2
	 */
	protected void	drawVLine(int x, int y1, int y2) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Get target surface
	 * @return target <code>SDLSurface</code>
	 */
	public SDLSurface getTarget() {	
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Set target surface
	 * @param target
	 * 			a <code> SDLSurface </code> target surface
	 */
	public void	setTarget(SDLSurface target) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Set target plane 
	 * @param width
	 * 			width
	 * @param height
	 * 			height
	 */
	public void setTargetPlane(int width, int height) {
		throw new UnsupportedOperationException();
	}
}
