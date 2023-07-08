package irotatable;

import uobject.*;
import irotatable.*;

public class RotatableAdapter implements IRotatable {
	private UObject o;
	private double da;
	
	public RotatableAdapter(UObject o, double da) throws Exception {
		int directionNumber,alphaDirection;
		//System.out.println("RotatableAdapter.RotatableAdapter(): "+a);
		this.o=o;
		this.da=da%360;
	}

	public void rotate() throws Exception {
		int alphaDirection,directionNumber;
		double alpha;
		
		directionNumber=(int)o.getProperty("directionNumber");
		alphaDirection=(int)o.getProperty("alphaDirection");
		alpha=360.0*alphaDirection/(double)directionNumber;
		alpha+=da;
		alpha=alpha%360;
		alphaDirection=(int)(directionNumber*alpha/360.0);
		System.out.println("RotatableAdapter.rotate(): alphaDirection="+alphaDirection+" directionNumber="+directionNumber+" alpha="+alpha);
		o.setProperty("alphaDirection",alphaDirection);
	}
}