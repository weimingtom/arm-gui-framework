package platform.thread;

import platform.util.Direction;
import platform.util.UpdateListener;
import platform.util.WidgetUpdate;
import sdljava.video.SDLRect;
import sdljava.video.SDLSurface;
import sdljavax.guichan.GUIException;
import sdljavax.guichan.widgets.Widget;

public class TransitionEffectHandler extends Thread{

	Widget widget;
	SDLSurface transitionedSurface;
	Direction direction;
	boolean show;
	public TransitionEffectHandler(Widget widg ,SDLSurface surface, Direction dir, boolean shw){
		widget = widg;
		transitionedSurface = surface;
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
						((UpdateListener)widget).putRegionToUpdate( new WidgetUpdate (widget,new SDLRect(widget.getX(),widget.getY(),transitionedSurface.getWidth(), (int)(transitionedSurface.getHeight() * i) ) ) );
						Thread.sleep(50);
					}
					break;
					
				case EAST:
					for(double i= 0.8 ; i>=0.0 ; i-=0.2){
						((UpdateListener)widget).putRegionToUpdate( new WidgetUpdate (widget,new SDLRect(widget.getX() + (int)(transitionedSurface.getWidth() * i) ,widget.getY() ,(int)(transitionedSurface.getWidth() * i) , transitionedSurface.getHeight() ) ) );
						Thread.sleep(50);
					}
					break;
						
				case SOUTH:
					for(double i= 0.8 ; i>=0.0 ; i-=0.2){
						((UpdateListener)widget).putRegionToUpdate( new WidgetUpdate (widget,new SDLRect(widget.getX(),widget.getY()+ (int)(transitionedSurface.getHeight() * i),transitionedSurface.getWidth(), (int)(transitionedSurface.getHeight()*i) ) ) );
						Thread.sleep(50);
					}
					break;
					
				case WEST:	
					for(double i= 0.2 ; i<1.0 ; i+=0.2){
						((UpdateListener)widget).putRegionToUpdate(new WidgetUpdate(widget,new SDLRect(widget.getX(),widget.getY(),(int)(transitionedSurface.getWidth() * i), transitionedSurface.getHeight())));
						Thread.sleep(50);
					}
					break;
						
				default:
					break;
				}
					
				((UpdateListener)widget).putRegionToUpdate( new WidgetUpdate (widget,new SDLRect(widget.getX(),widget.getY(),transitionedSurface.getWidth(), transitionedSurface.getHeight() ) ) );
			}
			
			else{
				switch(direction){
					
				case NORTH:
					for(double i= 0.2 ; i<=1.0 ; i+=0.2){
						((UpdateListener)widget).putRegionToUpdate( new WidgetUpdate (widget,new SDLRect(widget.getX(),widget.getY(),transitionedSurface.getWidth(), (int)(transitionedSurface.getHeight() *(1-i) ) ) ) );
						Thread.sleep(50);
					}
					break;
					
				case EAST:
					for(double i= 0.8 ; i>=0.0 ; i-=0.2){
						((UpdateListener)widget).putRegionToUpdate( new WidgetUpdate (widget,new SDLRect(widget.getX() + (int)(transitionedSurface.getWidth() *(1-i) ) ,widget.getY() ,(int)(transitionedSurface.getWidth() *(1-i) ) , transitionedSurface.getHeight() ) ) );
						Thread.sleep(50);
					}
					break;
						
				case SOUTH:
					for(double i= 0.8 ; i>=0.0 ; i-=0.2){
						((UpdateListener)widget).putRegionToUpdate( new WidgetUpdate (widget,new SDLRect(widget.getX(),widget.getY()+ (int)(transitionedSurface.getHeight() *(1-i) ),transitionedSurface.getWidth(), (int)(transitionedSurface.getHeight()*(1-i) ) ) ) );
						Thread.sleep(50);
					}
					break;
					
				case WEST:	
					for(double i= 0.2 ; i<1.0 ; i+=0.2){
						((UpdateListener)widget).putRegionToUpdate(new WidgetUpdate(widget,new SDLRect(widget.getX(),widget.getY(),(int)(transitionedSurface.getWidth() *(1-i) ), transitionedSurface.getHeight())));
						Thread.sleep(50);
					}
					break;
				}
				Thread.sleep(50);
				
				widget.setVisible(false);
				((UpdateListener)widget).putRegionToUpdate( new WidgetUpdate (widget,new SDLRect(widget.getX(),widget.getY(),transitionedSurface.getWidth(), transitionedSurface.getHeight() ) ) );
	
			}
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (GUIException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
}
