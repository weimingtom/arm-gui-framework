package platform.font;

import java.io.File;

import sdljava.SDLException;
import sdljava.ttf.GlyphMetrics;
import sdljava.ttf.SDLTTF;
import sdljava.ttf.SDLTrueTypeFont;
import sdljava.video.SDLColor;
import sdljava.video.SDLSurface;
import sdljavax.guichan.GUIException;
import sdljavax.guichan.font.Font;
import sdljavax.guichan.gfx.Graphics;
import sdljavax.guichan.sdl.SDLGraphics;

public class CalibriFont implements Font{

	protected SDLTrueTypeFont calibriFont;
	protected String text;
	protected SDLColor fontColor;
	
	public CalibriFont(int size , SDLColor color) throws SDLException{
		calibriFont = SDLTTF.openFont( "fonts"+ File.separator + "calibri.ttf", size); 
		text = new String("");
		fontColor = color; 
	}
	
	public void drawString(Graphics graphics, String string, int x, int y)throws GUIException {
		try {
			SDLSurface textSurface = calibriFont.renderTextSolid(string, fontColor);
			SDLGraphics sdlGraphics = (SDLGraphics) graphics;
			sdlGraphics.drawSDLSurface(textSurface, textSurface.getRect(), sdlGraphics.getTarget().getRect(x,y));
		
		} catch (SDLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getHeight() {
		return calibriFont.fontHeight();
	}

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

	public int getCharacterWidth(char character) throws SDLException{		
		GlyphMetrics glyphMetrics = calibriFont.glyphMetrics(character);
		
		return glyphMetrics.getMaxX() - glyphMetrics.getMinX() + 2;
	}
	
	public int getWidth(String string) {
		int width=0;
		char [] stringChars = string.toCharArray();
			
		for( char character: stringChars ){
			try {
				width+= getCharacterWidth(character);
			}catch (SDLException e) {
				e.printStackTrace();
			}
		}
		return width;
	}

	public void setColor(SDLColor color){
		fontColor = color;
		return;
	}
	
	public SDLColor getColor(){	
		return fontColor;
	}
	
	public void delete() throws GUIException {
		try {
			calibriFont.closeFont();
		} catch (SDLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
