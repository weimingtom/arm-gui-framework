package platform.thread;

import java.util.List;

import platform.gui.Area;
import platform.gui.PlatformIcon;
import platform.gui.Screen;
import platform.util.UpdateListener;
import platform.util.WidgetUpdate;
import sdljava.SDLException;
import sdljava.video.SDLRect;
import sdljava.video.SDLSurface;
import sdljavax.gfx.SDLGfx;
import sdljavax.guichan.GUIException;

public class CurveMotionHandler extends Thread {

	private final int transitionNr=4;
	private double[] resizeFactor = { 0.48, 0.48, 0.61, 0.74, 0.87, 1.0, 0.87, 0.74, 0.61, 0.48 }; // 0.5 , 0.8, 1.0,
															// 1.0, 0.8, 0.5
	private SDLRect[] curveCells = new SDLRect[resizeFactor.length];
	private List<PlatformIcon> iconList ;//= new ArrayList<PlatformIcon>();
	private Integer[] iconIndex;
	private Area motionArea;
	private UpdateListener updateListener;
	private SDLSurface temp;
	
	public CurveMotionHandler(Area area, List<PlatformIcon> list) throws GUIException, SDLException {
		super();
		motionArea = area;
		
		updateListener = (UpdateListener) motionArea;

		int[] grid = area.getGrid();

		if (grid[0] != 5 || grid[1] != 5) {
			throw new GUIException(
					"CurveMotionHandler can operate only on Area with 5x5 grid!");
		}
		fillCurveCellsArray(motionArea.getxCellDimension(), motionArea.getyCellDimension());

		iconList = list;
		
		iconIndex = new Integer[list.size()];
		for (int i = 0; i < iconIndex.length; i++) {
			iconIndex[i] = i;
		}

		int cellWidth = motionArea.getxCellDimension();
		int cellHeight = motionArea.getyCellDimension();

		for (PlatformIcon widget : iconList) {

			double widthRelation = (double) cellWidth / widget.getWidth();
			double heightRelation = (double) cellHeight / widget.getHeight();

			int a = (widthRelation < heightRelation) ? adjustSize(widget,widthRelation) : adjustSize(widget, heightRelation);
		}
		displayArea(area);
		start();
	}

	public void run(){
		int b, i=0;
		double a,sizeGrowth;
				
		try {
			
			while( (i++) < 25){
				
				Integer[] newIconIndex = new Integer[iconIndex.length]; 
		
				for(int k=1; k<transitionNr ; k++){
					
					for(PlatformIcon icon: iconList){
						int xPos,yPos;
						int curveIndex = iconIndex[iconList.indexOf(icon)];
						
						updateListener.putRegionToUpdate(new WidgetUpdate(motionArea, new SDLRect(icon.getX(),icon.getY(),icon.getWidth() + 2, icon.getHeight()+2)));
						
						if(iconIndex[iconList.indexOf(icon)]+1 < curveCells.length){
							a = (double)(curveCells[curveIndex+1].y - curveCells[curveIndex].y) / (curveCells[curveIndex+1].x - curveCells[curveIndex].x);
							b = (int) (curveCells[curveIndex+1].y - a * curveCells[curveIndex+1].x);
							xPos = curveCells[curveIndex].x + k*(curveCells[curveIndex+1].x - curveCells[curveIndex].x)/transitionNr;
							yPos = (int) (xPos* a + b);
						
							sizeGrowth = resizeFactor[curveIndex+1] - resizeFactor[curveIndex]; 
							temp = SDLGfx.rotozoomSurface(icon.getIconImage(), 1.0, resizeFactor[curveIndex]+ sizeGrowth/ transitionNr*k, true);
							icon.setIconModifiedImage(temp, temp.getWidth(), temp.getHeight() );
						}
						
						else{
							
							a = (double)(curveCells[curveIndex].y - curveCells[curveIndex-1].y) / (curveCells[curveIndex].x - curveCells[curveIndex-1].x);
							b = (int) (curveCells[curveIndex].y - a * curveCells[curveIndex].x);
							xPos = curveCells[curveIndex].x + k*(curveCells[curveIndex].x - curveCells[curveIndex-1].x)/transitionNr;
							yPos = (int) (xPos* a + b);
						}
						icon.setPosition(xPos, yPos);
											
						updateListener.putRegionToUpdate(new WidgetUpdate(icon, new SDLRect(xPos, yPos, icon.getWidth() ,icon.getHeight() )));
						
					}
				
					//Thread.sleep(50); sufficient for an ARm with 2 icons
					//Thread.sleep(100); sufficient for ARM to make it smoothly with 4, 50 -> too short
					Thread.sleep(iconList.size() * 25);
				}
				for(int j=0 ; j< iconIndex.length; j++){
						PlatformIcon icon = iconList.get(j);
						newIconIndex[j] = (iconIndex[j] + 1) % resizeFactor.length;
						
						updateListener.putRegionToUpdate(new WidgetUpdate(motionArea, new SDLRect(icon.getX(),icon.getY(),icon.getWidth(), icon.getHeight())));

						icon.setPosition(curveCells[newIconIndex[j]].x , curveCells[newIconIndex[j]].y );
						temp = SDLGfx.rotozoomSurface(icon.getIconImage(), 1.0, resizeFactor[newIconIndex[j]], true);
						icon.setIconModifiedImage(temp, temp.getWidth(), temp.getHeight() );
						updateListener.putRegionToUpdate(new WidgetUpdate(icon, curveCells[newIconIndex[j]]));																				
						
				}
				
				iconIndex = newIconIndex.clone();
				Thread.sleep(1000);
			}
			clean();
		}
		catch (SDLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GUIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void fillCurveCellsArray(int width, int height) {

		curveCells[0] = new SDLRect((int) (0.4 * width),
				0, (int) (0.65 * width),
				(int) (0.65 * height));
		
		curveCells[1] = new SDLRect(0, 0,
				(int) (0.65 * width),
				(int) (0.65 * height));
			
		curveCells[2] = new SDLRect((int) (0.4 * width),
		(int) (0.4 * height), (int) (0.65 * width),
		(int) (0.65 * height));

		
		curveCells[3] = new SDLRect((int) (0.85 * width),
				(int) (0.85 * height), (int) (0.75 * width),
				(int) (0.75 * height));
		
		curveCells[4] = new SDLRect((int) (1.4 * width),
				(int) (1.4 * height), (int) (0.9 * width),
				(int) (0.9 * height));
		
		curveCells[5] = new SDLRect((int)(2.1 * width), (int)(2.1 * height), width,
				(int) (1.0 * height));
		
		curveCells[6] = new SDLRect((int) (2.9 * width),
				(int) (2.9 * height), (int) (0.9 * width),
				(int) (0.9 * height));
		
		
		curveCells[7] = new SDLRect((int) (3.6 * width),
				(int) (3.6 * height), (int) (0.75 * width),
				(int) (0.75 * height));
		
		curveCells[8] = new SDLRect((int) (4.2 * width),
				(int) (4.2 * height), (int) (0.65 * width),
				(int) (0.65 * height));
		
		curveCells[9] = new SDLRect((int) (4.7 * width),
				(int) (4.3 * height), (int) (0.65 * width),
				(int) (0.65 * height));

	}

	private int adjustSize(PlatformIcon icon, double factor) {

		try {

			SDLSurface temp = SDLGfx.rotozoomSurface(icon.getIconImage(), 1.0,
					factor, true);
			icon.setIconImage(temp, temp.getWidth(), temp.getHeight());
			
		} catch (SDLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GUIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// refactoring
		return 1;
	}

	private void clean() throws SDLException, GUIException, InterruptedException{
		
		temp.freeSurface();
		for(PlatformIcon icon: iconList){
			icon.delete();
		}
		iconList.clear();

		Area displayArea = ( Screen.getScreen().getBackground().equals(motionArea) ) ? Screen.getScreen().getForeground() : Screen.getScreen().getBackground() ;

		displayArea(displayArea);
				
	}
	
	private void displayArea(Area display){
		int x=241;
		
		while( x > 0){
			
			try {
				display.setAlpha(255-x);
				Thread.sleep(100);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			x-=12;
		}
	}
}

