package platform.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import platform.gui.Area;
import platform.gui.PlatformIcon;
import platform.gui.PlatformWidget;
import platform.util.UpdateListener;
import platform.util.WidgetUpdate;
import sdljava.SDLException;
import sdljava.video.SDLRect;
import sdljava.video.SDLSurface;
import sdljavax.gfx.SDLGfx;
import sdljavax.guichan.GUIException;

public class CurveMotionHandler extends Thread {

	private final int transitionNr=5;
	private double[] resizeFactor = { 0.6, 0.7, 0.85, 1.0, 0.85, 0.7, 0.6 }; // 0.5 , 0.8, 1.0,
															// 1.0, 0.8, 0.5
	private SDLRect[] curveCells = new SDLRect[resizeFactor.length];
	private List<PlatformIcon> iconList = new ArrayList<PlatformIcon>();
	private Integer[] iconIndex;
	private Area motionArea;
	private UpdateListener updateListener;

	public CurveMotionHandler(Area area) throws GUIException {
		super();

		motionArea = area;
		updateListener = (UpdateListener) area;

		int[] grid = area.getGrid();

		if (grid[0] != 5 || grid[1] != 5) {
			throw new GUIException(
					"CurveMotionHandler can operate only on Area with 5x5 grid!");
		}

		fillCurveCellsArray(area.getxCellDimension(), area.getyCellDimension());

		Map<PlatformWidget, Set<Integer>> tempMap = area.getWidgetMap();

		int value = 0;
		iconIndex = new Integer[tempMap.keySet().size()];
		for (PlatformWidget widget : tempMap.keySet()) {

			if (widget instanceof PlatformIcon) {
				iconList.add((PlatformIcon) widget);

				value++;
			}

		}

		iconIndex = new Integer[value];
		for (int i = 0; i < iconIndex.length; i++) {
			iconIndex[i] = i;
		}

		int cellWidth = area.getxCellDimension();
		int cellHeight = area.getyCellDimension();

		for (PlatformIcon widget : iconList) {

			double widthRelation = (double) cellWidth / widget.getWidth();
			double heightRelation = (double) cellHeight / widget.getHeight();

			int a = (widthRelation < heightRelation) ? adjustSize(widget,widthRelation) : adjustSize(widget, heightRelation);
		}

		start();
	}

	public void run(){
		
		int i=0;
		double a = (double)(curveCells[2].y - curveCells[5].y) / (curveCells[2].x - curveCells[5].x);
		int b = (int) (curveCells[2].y - a * curveCells[2].x);
		
		try {
			
			while( (i++) < 200){
				
				Integer[] newIconIndex = new Integer[iconIndex.length]; 
		
				for(int k=0; k<transitionNr ; k++){
					
					for(PlatformIcon icon: iconList){
						int xPos,yPos;
						updateListener.putRegionToUpdate(new WidgetUpdate(motionArea, new SDLRect(icon.getX(),icon.getY(),icon.getWidth() + 2, icon.getHeight()+2)));
						
						if(iconIndex[iconList.indexOf(icon)]+1 < curveCells.length){
						xPos = curveCells[iconIndex[iconList.indexOf(icon)]].x + k*(curveCells[iconIndex[iconList.indexOf(icon)]+1].x - curveCells[iconIndex[iconList.indexOf(icon)]].x)/transitionNr;
						yPos = (int) (xPos* a + b);
						}
						else{
						xPos = curveCells[iconIndex[iconList.indexOf(icon)]].x + k*(curveCells[iconIndex[iconList.indexOf(icon)]].x - curveCells[iconIndex[iconList.indexOf(icon)]-1].x)/transitionNr;
						yPos = (int) (xPos* a + b);
						}
						icon.setPosition(xPos, yPos);
						//SDLSurface temp = SDLGfx.rotozoomSurface(icon.getIconImage(), 1.0, resizeFactor[iconIndex[j]], true);
						//icon.setIconModifiedImage(temp, temp.getWidth(), temp.getHeight() );
					
						updateListener.putRegionToUpdate(new WidgetUpdate(icon, new SDLRect(xPos, yPos, icon.getWidth() ,icon.getHeight() )));
						
					}
					Thread.sleep(30);
				}
				for(int j=0 ; j< iconIndex.length; j++){
						PlatformIcon icon = iconList.get(j);
						newIconIndex[j] = (iconIndex[j] + 1) % resizeFactor.length;
						
						updateListener.putRegionToUpdate(new WidgetUpdate(motionArea, new SDLRect(icon.getX(),icon.getY(),icon.getWidth(), icon.getHeight())));

						icon.setPosition(curveCells[newIconIndex[j]].x , curveCells[newIconIndex[j]].y );
						SDLSurface temp = SDLGfx.rotozoomSurface(icon.getIconImage(), 1.0, resizeFactor[newIconIndex[j]], true);
						icon.setIconModifiedImage(temp, temp.getWidth(), temp.getHeight() );
						updateListener.putRegionToUpdate(new WidgetUpdate(icon, curveCells[newIconIndex[j]]));																				
				}
				
				iconIndex = newIconIndex.clone();
				Thread.sleep(1000);
			}
			System.out.println("Finished");
		}
		catch (SDLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void fillCurveCellsArray(int width, int height) {

		curveCells[0] = new SDLRect((int) (0.1 * width),
				(int) (0.1 * height), (int) (0.65 * width),
				(int) (0.65 * height));
		
		curveCells[1] = new SDLRect((int) (0.75 * width),
				(int) (0.75 * height), (int) (0.75 * width),
				(int) (0.75 * height));
		
		curveCells[2] = new SDLRect((int) (1.4 * width),
				(int) (1.4 * height), (int) (0.9 * width),
				(int) (0.9 * height));
		
		curveCells[3] = new SDLRect((int)(2.0 * width), (int)(2.0 * height), width,
				(int) (1.0 * height));
		
		curveCells[4] = new SDLRect((int) (2.7 * width),
				(int) (2.7 * height), (int) (0.9 * width),
				(int) (0.9 * height));
		
		
		curveCells[5] = new SDLRect((int) (3.5 * width),
				(int) (3.5 * height), (int) (0.75 * width),
				(int) (0.75 * height));
		curveCells[6] = new SDLRect((int) (4.25 * width),
				(int) (4.25 * height), (int) (0.65 * width),
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
		}
		// refactoring
		return 1;
	}

}

/*
Integer[] newIconIndex = new Integer[iconIndex.length]; 
for(int index=0; index<5; index++){
	int j;
	for( j=0 ; j< iconIndex.length; j++){
		if(iconIndex[j] == index){ 
			
			PlatformIcon icon = iconList.get(j);
			if(iconIndex[j] != 0 && iconIndex[j] !=5 ){
				SDLRect currentRect = curveCells[iconIndex[j]-1];
				SDLRect nextRect = curveCells[iconIndex[j]];
				
				double a = (double)(nextRect.y - currentRect.y) / (nextRect.x - currentRect.x);
				int b = (int) (nextRect.y - a * nextRect.x);
				
				for(int k=0; k<20 ; k++){
					int xPos = curveCells[iconIndex[j] -1].x + k* 3;
					int yPos = (int) (xPos* a + b);
					
					icon.setPosition(xPos, yPos);
					//SDLSurface temp = SDLGfx.rotozoomSurface(icon.getIconImage(), 1.0, resizeFactor[iconIndex[j]], true);
					//icon.setIconModifiedImage(temp, temp.getWidth(), temp.getHeight() );
				
					updateListener.putRegionToUpdate(new WidgetUpdate(icon, new SDLRect(xPos, yPos, curveCells[iconIndex[j]-1].width, curveCells[iconIndex[j]-1].height)));
					updateListener.putRegionToUpdate(new WidgetUpdate(motionArea, new SDLRect(xPos, yPos, curveCells[iconIndex[j]-1].width, curveCells[iconIndex[j]-1].height)));
					Thread.sleep(5);
				}
			}
			
			icon.setPosition(curveCells[iconIndex[j]].x , curveCells[iconIndex[j]].y );
			SDLSurface temp = SDLGfx.rotozoomSurface(icon.getIconImage(), 1.0, resizeFactor[iconIndex[j]], true);
			icon.setIconModifiedImage(temp, temp.getWidth(), temp.getHeight() );
		
			updateListener.putRegionToUpdate(new WidgetUpdate(icon, curveCells[iconIndex[j]]));
			newIconIndex[j] = (iconIndex[j] + 1) % 5;
			break;
		}
		
	}
	if(j == iconIndex.length){
		updateListener.putRegionToUpdate(new WidgetUpdate(motionArea, curveCells[index]));
	}
}
iconIndex = newIconIndex.clone();
Thread.sleep(2000);
*/