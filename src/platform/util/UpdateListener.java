package platform.util;

public interface UpdateListener {
	
	boolean putRegionToUpdate(WidgetUpdate updateInfo) throws InterruptedException;

}
