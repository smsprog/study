package uobject;

import java.util.Vector;

public class Ship implements UObject {
	private static double eps=0.0001;
	private Vector position;
	private double v; // speed
	
	private int directionNumber;
	private int alphaDirection;

	public Ship(double x, double y, double a, int directionNumber) {
		//System.out.println("Ship.Ship()");
		position=new Vector();
		position.add(x);
		position.add(y);
		this.directionNumber=directionNumber;
		this.alphaDirection=(int)(directionNumber*a/360.0);
	}

	public Object getProperty(String key) throws Exception {
		if(key=="position")return(position);
		else if(key=="alphaDirection")return(alphaDirection);
		else if(key=="directionNumber")return(directionNumber);
		else if(key=="v")return(v);
		else throw new Exception("property for GET not found: "+key);
	}
	
	public void setProperty(String key, Object newValue) throws Exception {
		//System.out.println("Ship.setProperty()"+newValue);
		if(key=="position")
			position=(Vector)newValue;
		else if(key=="v")
			v=(double)newValue;
		else if(key=="alphaDirection")
			alphaDirection=(int)newValue;
		else if(key=="directionNumber")
			directionNumber=(int)newValue;
		else
			throw new Exception("property for SET not found: "+key);
	}
	
	public void showProps(String prefix) {
		System.out.println(prefix+"Ship.showProps(): x="+(double)position.elementAt(0)+" y="+(double)position.elementAt(1)+" v="+v+" alphaDirection="+alphaDirection+" directionNumber="+directionNumber);
	}
	
	public double getAlpha() {
		return(360.0*alphaDirection/directionNumber);
	}
	
	public Vector getPosition() throws Exception {
		if(position.elementAt(0)==null || position.elementAt(1)==null || Double.isNaN((double)position.elementAt(0)) || Double.isNaN((double)position.elementAt(1))) {
			//System.out.println("MovableAdapter.getPosition(): EXCEPTION");
			throw new Exception("Unknown position");
		}
		return(position);
	}
}