package imovable;

import uobject.*;
import java.util.Vector;

public class MovableAdapter implements IMovable {
	private UObject o;
	
	public MovableAdapter(UObject o) {
		this.o=o;
	}
	
	public MovableAdapter(UObject o, double v) throws Exception {
		//System.out.println("Ship.Ship()");
		this(o);
		this.o.setProperty("v",v);
	}
	
	public MovableAdapter(UObject o, double dx, double dy) throws Exception {
		//System.out.println("Ship.Ship()");
		this(o);
		setVelocity(dx, dy);
	}

	public void setPosition(Vector newPosition) throws Exception {
		o.setProperty("position",newPosition);
		//o.showPosition("MovableAdapter: ");
	}
	
	public void move() throws Exception {
		Vector pos;
		Vector posNew=new Vector();
		double v,alpha;
		int alphaDirection,directionNumber;
		
		pos=o.getPosition();
		v=(double)o.getProperty("v");
		alphaDirection=(int)o.getProperty("alphaDirection");
		directionNumber=(int)o.getProperty("directionNumber");
		alpha=360.0*alphaDirection/(double)directionNumber*Math.PI/180.0;
		System.out.println("MovableAdapter.move(): v="+v+" alphaDirection="+alphaDirection+" directionNumber="+directionNumber+" alpha="+alpha);

		posNew.add((double)pos.elementAt(0)+v*Math.cos(alpha));
		posNew.add((double)pos.elementAt(1)+v*Math.sin(alpha));
		setPosition(posNew);
	}
	
	public void setVelocity(double dx, double dy) throws Exception {
		double alpha,v;
		int alphaDirection,directionNumber;
		
		alphaDirection=(int)o.getProperty("alphaDirection");
		directionNumber=(int)o.getProperty("directionNumber");
		//System.out.println("MovableAdapter.setVelocity(): dx="+dx+" dy="+dy+" alphaDirection="+alphaDirection+" directionNumber="+directionNumber);
		
		v=Math.sqrt(dx*dx+dy*dy);
		if(dx>Globals.eps) {
			if(dy>0 || Math.abs(dy)==0)
				alpha=Math.atan(dy/dx);
			else
				alpha=Math.atan(dy/dx)+2*Math.PI;
		} else if(dx<-Globals.eps)
			alpha=Math.atan(dy/dx)+Math.PI;
		else if(dy>Globals.eps)
			alpha=Math.PI/2;
		else if(dy<-Globals.eps)
			alpha=-Math.PI/2;
		else
			alpha=Double.NaN;
		//System.out.println(alpha);
		if(!Double.isNaN(alpha)) {
			alpha*=180.0/Math.PI;  //System.out.println(alpha);
			alphaDirection=(int)Math.ceil(directionNumber*alpha/360.0);
			//System.out.println(alphaDirection);
		}
		
		o.setProperty("alphaDirection",alphaDirection);
		o.setProperty("v",v);
		//System.out.println("MovableAdapter.setVelocity(): alphaDirection="+alphaDirection+" v="+v);
	}
}