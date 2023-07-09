package uobject;

import java.util.Vector;

public class Ship implements UObject {
	private Vector position;
	private double v; // speed
	private int directionNumber;
	private int phiDirection;
	
	private int alphaDirection;

	private double fuelCapacity;
	private double fuelBurnRate;

	public Ship(double x, double y, double a, int directionNumber, double fuelCapacity) {
		//System.out.println("Ship.Ship()");
		position=new Vector();
		position.add(x);
		position.add(y);
		this.directionNumber=directionNumber;
		this.alphaDirection=(int)(directionNumber*a/360.0);
		this.fuelCapacity=fuelCapacity;
	}

	public Object getProperty(String key) throws Exception {
		if(key=="position")return(position);
		else if(key=="alphaDirection")return(alphaDirection);
		else if(key=="directionNumber")return(directionNumber);
		else if(key=="fuelCapacity")return(fuelCapacity);
		else if(key=="fuelBurnRate")return(fuelBurnRate);
		else if(key=="phiDirection")return(phiDirection);
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
		else if(key=="fuelCapacity")
			fuelCapacity=(double)newValue;
		else if(key=="fuelBurnRate")
			fuelBurnRate=(double)newValue;
		else if(key=="phiDirection")
			phiDirection=(int)newValue;
		else
			throw new Exception("property for SET not found: "+key);
	}
	
	public void showProps(String prefix) {
		System.out.println(prefix+"Ship.showProps(): x="+(double)position.elementAt(0)+" y="+(double)position.elementAt(1)+" v="+v+" alphaDirection="+alphaDirection+" directionNumber="+directionNumber+" fuelCapacity="+fuelCapacity);
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