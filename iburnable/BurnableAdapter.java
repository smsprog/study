package iburnable;

import uobject.*;

public class BurnableAdapter implements IBurnable {
	private UObject o;
	
	public BurnableAdapter(UObject o, double fuelBurnRate) throws Exception {
		this.o=o;
		o.setProperty("fuelBurnRate", fuelBurnRate);
	}
	
	public double check() throws Exception {
		double fc, fbr, v;

		fc=(double)o.getProperty("fuelCapacity");
		fbr=(double)o.getProperty("fuelBurnRate");
		v=(double)o.getProperty("v");
		//System.out.println("BurnableAdapter: "+fc+" "+fbr+" "+v);
		fc-=fbr*v;
		if(fc<-Globals.eps)throw new Exception("no fuel exception, need additional: "+fc);
		return(fc);
	}
	
	public void burn() throws Exception {
		double fc=check();
		o.setProperty("fuelCapacity", fc);
	}
}