package platform.thread;

import platform.gui.PlatformWidget;
import platform.util.Direction;
import platform.util.UpdateListener;
import platform.util.WidgetUpdate;
import sdljava.video.SDLRect;

public class TransitionEffectHandler extends Thread{

	PlatformWidget widget;
	UpdateListener updateListener;
	Direction direction;
	boolean show;
	
	public TransitionEffectHandler(PlatformWidget widg , UpdateListener listener, Direction dir, boolean shw){
		widget = widg;
		updateListener = listener;
		direction = dir;
		show=shw;
		start();
	}
	
	public void run(){
		
		
		try {
			if(show == true){	
				switch(direction){
					
				case NORTH:
					for(double i= 0.2 ; i<=1.0 ; i+=0.2){
						updateListener.putRegionToUpdate( new WidgetUpdate (widget,new SDLRect(widget.getX(),widget.getY(),widget.getWidth(), (int)(widget.getHeight() * i) ) ) );
						Thread.sleep(50);
					}
					break;
					
				case EAST:
					for(double i= 0.8 ; i>=0.0 ; i-=0.2){
						updateListener.putRegionToUpdate( new WidgetUpdate (widget,new SDLRect(widget.getX() + (int)(widget.getWidth() * i) ,widget.getY() ,(int)(widget.getWidth() * i) , widget.getHeight() ) ) );
						Thread.sleep(50);
					}
					break;
						
				case SOUTH:
					for(double i= 0.8 ; i>=0.0 ; i-=0.2){
						updateListener.putRegionToUpdate( new WidgetUpdate (widget,new SDLRect(widget.getX(),widget.getY()+ (int)(widget.getHeight() * i) ,widget.getWidth(), (int)(widget.getHeight()*i) + 1  ) ) );
						Thread.sleep(50);
					}
					break;
					
				case WEST:	
					for(double i= 0.2 ; i<1.0 ; i+=0.2){
						updateListener.putRegionToUpdate(new WidgetUpdate(widget,new SDLRect(widget.getX(),widget.getY(),(int)(widget.getWidth() * i), widget.getHeight())));
						Thread.sleep(50);
					}
					break;
						
				default:
					break;
				}
					
				updateListener.putRegionToUpdate( new WidgetUpdate (widget,new SDLRect(widget.getX(),widget.getY(),widget.getWidth(), widget.getHeight() ) ) );
			}
			
			else{
				switch(direction){
					
				case NORTH:
					for(double i= 0.2 ; i<=1.0 ; i+=0.2){
						updateListener.putRegionToUpdate( new WidgetUpdate (widget,new SDLRect(widget.getX(),widget.getY() + (int)(widget.getHeight() *(1-i)) ,widget.getWidth(), (int)(widget.getHeight() * i) + 1) ) ) ;
						Thread.sleep(50);
					}
					break;
					
				case EAST:
					for(double i= 0.8 ; i>=0.0 ; i-=0.2){
						//updateListener.putRegionToUpdate( new WidgetUpdate (widget,new SDLRect(widget.getX() + (int)(widget.getWidth() *(1-i) ) ,widget.getY() ,(int)(widget.getWidth() *(1-i) ) , widget.getHeight() ) ) );
						
						updateListener.putRegionToUpdate( new WidgetUpdate (widget,new SDLRect(widget.getX(),widget.getY() , (int)(widget.getWidth() *(1-i) ) , widget.getHeight() ) ) );
						Thread.sleep(50);
					}
					break;
						
				case SOUTH:
					for(double i= 0.8 ; i>=0.0 ; i-=0.2){
						updateListener.putRegionToUpdate( new WidgetUpdate (widget,new SDLRect(widget.getX(),widget.getY() ,widget.getWidth(), (int)(widget.getHeight() * (1-i)) ) ) ) ;
						Thread.sleep(50);
					}
					break;
					
				case WEST:	
					for(double i= 0.2 ; i<=1.0 ; i+=0.2){
						updateListener.putRegionToUpdate(new WidgetUpdate(widget,new SDLRect(widget.getX() + (int)(widget.getWidth() *(1- i) ),widget.getY(),(int)(widget.getWidth() * i  ), widget.getHeight())));
						Thread.sleep(50);
					}
					break;
				}
				Thread.sleep(50);
				
				
	
			}
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		
	}
}
