package platform.util;

public interface UpdateListener {
	
	void putRegionToUpdate(WidgetUpdate updateInfo) throws InterruptedException;

}
