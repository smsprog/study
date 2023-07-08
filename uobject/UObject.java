package uobject;

import java.util.Vector;

public interface UObject {
	Object getProperty(String key) throws Exception;
	void setProperty(String key, Object o) throws Exception;
	Vector getPosition() throws Exception;
	//void showPosition(String prefix);
}
