package platform.util;

import java.util.Vector;

import sdljavax.guichan.widgets.ListModel;

public class PlatformListModel implements ListModel {

	protected Vector<String> vector= new Vector<String>();
	
	public PlatformListModel(String [] array){
		for (String string : array) {
			vector.add(string);
		}
	}
	
	public void add(String string) {
		vector.add(string);
	}

	public void add(String string, int index){
		vector.add(index, string);
	}

	public void delete(){}
	
	public String getElementAt(int index) {
		return vector.elementAt(index);
	}
	
	public int getNumberOfElements() {
		return vector.size();
	}
}
