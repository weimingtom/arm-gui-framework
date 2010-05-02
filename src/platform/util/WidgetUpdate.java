package platform.util;

import sdljava.video.SDLRect;
import sdljavax.guichan.widgets.Widget;

public class WidgetUpdate {
	
	protected Widget widget;
	protected SDLRect widgetRegion;
	
	public WidgetUpdate(Widget widgetToUpdate, SDLRect updateRegion){
		
		widget = widgetToUpdate;
		widgetRegion = updateRegion;
	}

	public Widget getWidget() {
		return widget;
	}

	public SDLRect getWidgetRegion() {
		return widgetRegion;
	}
	
	
}
