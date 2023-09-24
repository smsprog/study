package imovable;

import uobject.*;
import java.util.Vector;

public class MovableAdapter implements IMovable {
	private UObject o;
	double v=0;
	double dx=0,dy=0;
	
	public MovableAdapter(UObject o) {
		this.o=o;
	}
	
	public MovableAdapter(UObject o, double v) throws Exception {
		//System.out.println("Ship.Ship()");
		this(o);
		this.v=v;
	}
	
	public MovableAdapter(UObject o, double dx, double dy) throws Exception {
		//System.out.println("Ship.Ship()");
		this(o);
		this.dx=dx;
		this.dy=dy;
	}

	public void setPosition(Vector newPosition) throws Exception {
		o.setProperty("position",newPosition);
		//o.showPosition("MovableAdapter: ");
	}
	
	public void move() throws Exception {
		Vector pos;
		Vector posNew=new Vector();
		double v,phi;
		int phiDirection,directionNumber;
		
		if(this.v!=0)this.o.setProperty("v",this.v);
		if(this.dx!=0 && this.dy!=0)setVelocity(this.dx, this.dy);
		
		pos=o.getPosition();
		v=(double)o.getProperty("v");
		phiDirection=(int)o.getProperty("phiDirection");
		directionNumber=(int)o.getProperty("directionNumber");
		phi=360.0*phiDirection/(double)directionNumber*Math.PI/180.0;
		System.out.println("MovableAdapter.move(): v="+v+" phiDirection="+phiDirection+" directionNumber="+directionNumber+" phi="+phi+" "+o);

		posNew.add((double)pos.elementAt(0)+v*Math.cos(phi));
		posNew.add((double)pos.elementAt(1)+v*Math.sin(phi));
		setPosition(posNew);
		System.out.println("MovableAdapter.move(): "+o.getPosition());
	}
	
	public void setVelocity(double dx, double dy) throws Exception {
		double phi,v;
		int phiDirection,directionNumber;
		
		phiDirection=(int)o.getProperty("phiDirection");
		directionNumber=(int)o.getProperty("directionNumber");
		//System.out.println("MovableAdapter.setVelocity(): dx="+dx+" dy="+dy+" alphaDirection="+alphaDirection+" directionNumber="+directionNumber);
		
		v=Math.sqrt(dx*dx+dy*dy);
		if(dx>Globals.eps) {
			if(dy>0 || Math.abs(dy)==0)
				phi=Math.atan(dy/dx);
			else
				phi=Math.atan(dy/dx)+2*Math.PI;
		} else if(dx<-Globals.eps)
			phi=Math.atan(dy/dx)+Math.PI;
		else if(dy>Globals.eps)
			phi=Math.PI/2;
		else if(dy<-Globals.eps)
			phi=-Math.PI/2;
		else
			phi=Double.NaN;
		//System.out.println(alpha);
		if(!Double.isNaN(phi)) {
			phi*=180.0/Math.PI;  //System.out.println(alpha);
			phiDirection=(int)Math.ceil(directionNumber*phi/360.0);
			//System.out.println(alphaDirection);
		}
		
		o.setProperty("phiDirection",phiDirection);
		o.setProperty("v",v);
		//System.out.println("MovableAdapter.setVelocity(): alphaDirection="+alphaDirection+" v="+v);
	}
}