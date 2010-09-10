package platform.util;

import platform.gui.PlatformWidget;
import sdljava.video.SDLRect;

public class WidgetUpdate {
	
	protected PlatformWidget widget;
	protected SDLRect widgetRegion;
	
	public WidgetUpdate(PlatformWidget widgetToUpdate, SDLRect updateRegion) {
		widget = widgetToUpdate;
		widgetRegion = updateRegion;
	}

	public PlatformWidget getWidget() {
		return widget;
	}

	public SDLRect getWidgetRegion() {
		return widgetRegion;
	}
}
