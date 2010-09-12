package platform.font;

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
import java.io.File;

import platform.gfx.UnifiedGraphics;
import platform.sdl.SDLGraphics;
import sdljava.SDLException;
import sdljava.ttf.GlyphMetrics;
import sdljava.ttf.SDLTTF;
import sdljava.ttf.SDLTrueTypeFont;
import sdljava.video.SDLColor;
import sdljava.video.SDLSurface;
import sdljavax.guichan.GUIException;
import sdljavax.guichan.font.Font;
import sdljavax.guichan.gfx.Graphics;

/**
 * Class used for drawing font "Calibri" on the screen.
 * @author Bartosz Kędra
 * @author bartosz.kedra@gmail.com
 */
public class CalibriFont implements Font {

	/**
	 * Instance of TTF_Font object
	 */
	protected SDLTrueTypeFont calibriFont;
	
	/**
	 * Text to be displayed
	 */
	protected String text;
	
	/**
	 * Text color 
	 */
	protected SDLColor fontColor;
	
	/**
	 * Constructor
	 * @param size 
	 * 			font size
	 * @param color 
	 * 			<code>SDLColor</code> font color
	 * @param bold 
	 * 			set true for bold font, false for normal
	 * @throws SDLException
	 */
	public CalibriFont(int size, SDLColor color, boolean bold) throws SDLException {
		if (bold) {
			calibriFont = SDLTTF.openFont( "fonts"+ File.separator + "Calibri Bold.ttf", size); 
		} else {
			calibriFont = SDLTTF.openFont( "fonts"+ File.separator + "calibri.ttf", size); 
		}
		text = new String("");
		fontColor = color; 
	}
	
	/**
	 * Cleans any dynamically reserved memory areas in C style 
	 * @throws GUIException
	 */
	public void delete() throws GUIException {
		try {
			calibriFont.closeFont();
		} catch (SDLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Draws string on Graphics' target surface
	 * @param graphics 
	 * 			<code>Graphics</code> object 
	 * @param string 
	 * 			a String value
	 * @param x 
	 * 		a x coordinate where to draw a string on the screen
	 * @param y 
	 * 		a Y coordinate where to draw a string on the screen
	 * @throws GUIException
	 */
	
	public void drawString(Graphics graphics, String string, int x, int y) throws GUIException {
		if(graphics instanceof SDLGraphics){
			try {
				SDLSurface textSurface = calibriFont.renderTextSolid(string, fontColor);
				((SDLGraphics) graphics).drawSDLSurface(textSurface, textSurface.getRect(),
														((SDLGraphics) graphics).getTarget().getRect(x,y));
			} catch (SDLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Draws string on UnifiedGraphics' target surface
	 * @param graphics 
	 * 			<code>UnifiedGraphics</code> object that doesn't necessitate cast to SDLGraphics
	 * @param string 
	 * 			a String value
	 * @param x 
	 * 		a X coordinate where to draw a string on the screen
	 * @param y 
	 * 		a Y coordinate where to draw a string on the screen
	 * @throws GUIException
	 */
	public void drawString(UnifiedGraphics graphics, String string, int x, int y) throws GUIException {
		try {
			SDLSurface textSurface = calibriFont.renderTextSolid(string, fontColor);
			graphics.drawSDLSurface(textSurface, textSurface.getRect(),
									graphics.getTarget().getRect(x,y));
		} catch (SDLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Draws blended string on UnifiedGraphics' target surface
	 * @param graphics 
	 * 			<code>UnifiedGraphics</code> object that doesn't necessitate cast to SDLGraphics
	 * @param string 
	 * 			a String value
	 * @param x 
	 * 		a X coordinate where to draw a string on the screen
	 * @param y 
	 * 		a Y coordinate where to draw a string on the screen
	 * @throws GUIException
	 */
	public void drawStringBlended(UnifiedGraphics graphics, String string, int x, int y) throws GUIException {
		try {
			SDLSurface textSurface = calibriFont.renderTextBlended(string, fontColor);
			graphics.drawSDLSurface(textSurface, textSurface.getRect(),
									graphics.getTarget().getRect(x,y));
		} catch (SDLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Get character width
	 * @param character 
	 * 			character we want to measure
	 * @return width of passed character
	 * @throws SDLException
	 */
	public int getCharacterWidth(char character) throws SDLException {		
		GlyphMetrics glyphMetrics = calibriFont.glyphMetrics(character);
		
		return glyphMetrics.getMaxX() - glyphMetrics.getMinX() + 2;
	}
	
	/**
	 * Get font color
	 * @return <code>SDLColor</code>
	 */
	public SDLColor getColor(){	
		return fontColor;
	}
	
	/**
	 * Get font height
	 * @return font height
	 */
	public int getHeight() {
		return calibriFont.fontHeight();
	}
	
	/**
	 * Get a string index at a certain position
	 * @param strText
	 * 			a String for measuring index
	 * @param x
	 * 		  position we want to search
	 * @return string index 
	 */
	public int getStringIndexAt(String strText, int x) {
		int size = 0;
		
		for (int i = 0; i < strText.length(); ++i) {
			try {
				size += getCharacterWidth(strText.charAt(i));
			} catch (SDLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (size > x) {
				return i;
			}
		}
		return strText.length();
	}
	
	/**
	 * Get width of string
	 * @param string a String value
	 * @return width of string
	 */
	public int getWidth(String string) {
		int width = 0;
		char [] stringChars = string.toCharArray();
			
		for( char character: stringChars ){
			try {
				width += getCharacterWidth(character);
			} catch (SDLException e) {
				e.printStackTrace();
			}
		}
		return width;
	}

	/**
	 * Set font color
	 * @param color a <code>SDLColor</code> value
	 */
	public void setColor(SDLColor color){
		fontColor = color;
		return;
	}
}
