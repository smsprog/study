package uobject;

import java.util.Vector;
import java.lang.reflect.Field;

public class Ship implements UObject {
	private Vector position;
	private double v; // speed
	private int directionNumber;
	private int phiDirection;
	
	private int alphaDirection;

	private double fuelCapacity;
	private double fuelBurnRate;

	//public Ship() {}
	
	public Ship(double x, double y, double a, int directionNumber, double fuelCapacity) {
		System.out.println("Ship.Ship(): "+x+" "+y+" "+this);
		position=new Vector();
		position.add(x);
		position.add(y);
		this.directionNumber=directionNumber;
		this.alphaDirection=(int)(directionNumber*a/360.0);
		this.fuelCapacity=fuelCapacity;
	}

	public Object getProperty(String key) throws Exception {
		//Class myClass=this.getClass();
		//Field field=this.getClass().getDeclaredField(key);
		return(this.getClass().getDeclaredField(key).get(this));
	}
	
	public void setProperty(String key, Object newValue) throws Exception {
		//Field f=this.getClass().getDeclaredField(key);
		this.getClass().getDeclaredField(key).set(this, newValue);
	}
	
	public void showProps(String prefix) {
		System.out.println(prefix+"Ship.showProps(): x="+(double)position.elementAt(0)+" y="+(double)position.elementAt(1)+" v="+v+" alphaDirection="+alphaDirection+" directionNumber="+directionNumber+" fuelCapacity="+fuelCapacity+" "+this);
	}
	
	public double getAlpha() {
		return(360.0*alphaDirection/directionNumber);
	}

	public double getFuelCapacity() {
		return(fuelCapacity);
	}
	
	public double getVelocity() {
		return(v);
	}
	
	public Vector getPosition() throws Exception {
		if(position.elementAt(0)==null || position.elementAt(1)==null || Double.isNaN((double)position.elementAt(0)) || Double.isNaN((double)position.elementAt(1))) {
			//System.out.println("MovableAdapter.getPosition(): EXCEPTION");
			throw new Exception("Unknown position");
		}
		return(position);
	}
}