package platform.font;

import java.io.File;

import platform.gfx.UnifiedGraphics;
import platform.gui.Screen;
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

public class CalibriFont implements Font{

	protected SDLTrueTypeFont calibriFont;
	protected String text;
	protected SDLColor fontColor;
	
	public CalibriFont(int size , SDLColor color, boolean bold) throws SDLException{
		
		if(bold){
		calibriFont = SDLTTF.openFont( "fonts"+ File.separator + "Calibri Bold.ttf", size); 
		}
		else{
			calibriFont = SDLTTF.openFont( "fonts"+ File.separator + "calibri.ttf", size); 
		}
		text = new String("");
		fontColor = color; 
	}
	
	public void drawString(UnifiedGraphics graphics, String string, int x, int y)throws GUIException {
		
		try {
			SDLSurface textSurface = calibriFont.renderTextSolid(string, fontColor);
			graphics.drawSDLSurface(textSurface, textSurface.getRect(), graphics.getTarget().getRect(x,y));
		
		} catch (SDLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void drawStringBlended(UnifiedGraphics graphics, String string, int x, int y)throws GUIException {
		try {
			SDLSurface textSurface = calibriFont.renderTextBlended(string, fontColor);
			graphics.drawSDLSurface(textSurface, textSurface.getRect(), graphics.getTarget().getRect(x,y));
		
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

	public void drawString(Graphics graphics, String string, int x, int y)
			throws GUIException {
		if(graphics instanceof SDLGraphics)
		
		try {
			SDLSurface textSurface = calibriFont.renderTextSolid(string, fontColor);
			((SDLGraphics)graphics).drawSDLSurface(textSurface, textSurface.getRect(), ((SDLGraphics)graphics).getTarget().getRect(x,y));
		
		} catch (SDLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
