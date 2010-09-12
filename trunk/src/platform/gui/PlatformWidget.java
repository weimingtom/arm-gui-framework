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

import platform.gfx.UnifiedGraphics;
import platform.util.UpdateListener;
import sdljavax.guichan.GUIException;
import sdljavax.guichan.gfx.Graphics;
import sdljavax.guichan.widgets.Widget;
/**
 * Class representing widget used in the project, extending functionality of sdljavax.guichan Widget class
 * @author Bartosz Kędra
 * @author bartosz.kedra@gmail.com
 *
 */
public abstract class PlatformWidget extends Widget {

	protected UpdateListener updateListener = null;
	protected PlatformWidget parentWidget = null;
	
	/**
	 * Adds widget on the position specified by offset from the (0,0) cell
	 * @param widget
	 * 			widget to add
	 * @param offset
	 * 			offset from the beginning
	 * @throws GUIException
	 */
	public void add(PlatformWidget pltaformWidget, int offset) throws GUIException{
		
		throw new UnsupportedOperationException();
	}

	/**
	 *This method is not called anywhere, method with UnifiedGraphics is used instead
	 */
	@Override
	public void draw(Graphics graphics) throws GUIException {
		// Nothing in here
	}

	/**
	 * Draws widget on the surface 
	 * @param graphics
	 * 			used for drawing menu on target surface
	 * @throws GUIException
	 */
	public abstract void draw(UnifiedGraphics graphics) throws GUIException;
	
	/**
	 * This method is not called anywhere, method with UnifiedGraphics is used instead
	 */
	@Override
	public void drawBorder(Graphics graphics) throws GUIException {
		// Nothing in here
	}
	
	/**
	 * Draws border
	 * @param graphics
	 * 			used for drawing background on target surface
	 * @throws GUIException
	 */
	public abstract void drawBorder(UnifiedGraphics graphics) throws GUIException;
	
	/**
	 * Get parent widget
	 * @return
	 * 		<code> PlatformWidget </code>
	 */
	public PlatformWidget getParentWidget() {
		return parentWidget;
	}
	
	/**
	 * Get update listener
	 * @return
	 */
	public UpdateListener getUpdateListener() {
		return updateListener;
	}
	
	public void remove(PlatformWidget platformWidget) throws GUIException {
		throw new UnsupportedOperationException();
	}
	
	public void setAlpha(int alphaIndex) {
		throw new UnsupportedOperationException();
	}
	
	public void setMouseEnabled(boolean value) {
		m_bHasMouse = value;
	}

	public void setParentWidget(PlatformWidget parentWidget) {
		this.parentWidget = parentWidget;
	}
	
	public void setUpdateListener(UpdateListener listener) {
		updateListener = listener;
	}
}
