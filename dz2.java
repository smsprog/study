import java.util.Vector;

interface IRotatable {
	void rotate(double alpha) throws Exception;
}

class RotatableAdapter implements IRotatable {
	private static double eps=0.0001;
	private UObject o;
	
	public RotatableAdapter(UObject o) {
		this.o=o;
	}

	public void rotate(double alpha) throws Exception {
		int alphaDirection,directionNumber;
		
		alphaDirection=(int)o.getProperty("alphaDirection");
		directionNumber=(int)o.getProperty("directionNumber");
		alpha+=360.0*alphaDirection/(double)directionNumber;
		alpha=alpha%360;
		alphaDirection=(int)(directionNumber*alpha/360.0);
		System.out.println("RotatableAdapter.rotate(): alphaDirection="+alphaDirection+" directionNumber="+directionNumber+" alpha="+alpha);
		o.setProperty("alphaDirection",alphaDirection);
	}
}

interface IMovable {
	Vector getPosition() throws Exception;
	void setPosition(Vector newPosition) throws Exception;
	void move() throws Exception;
}

class MovableAdapter implements IMovable {
	private static double eps=0.0001;
	private UObject o;
	
	public MovableAdapter(UObject o) {
		this.o=o;
	}

	public void setPosition(Vector newPosition) throws Exception {
		o.setProperty("position",newPosition);
		//o.showPosition("MovableAdapter: ");
	}
	
	public Vector getPosition() throws Exception {
		Vector v;
		v=(Vector)o.getProperty("position");
		//o.showPosition("MovableAdapter.getPosition(): "+v.elementAt(0)+" "+v.elementAt(1));
		if(v.elementAt(0)==null || v.elementAt(1)==null || Double.isNaN((double)v.elementAt(0)) || Double.isNaN((double)v.elementAt(1))) {
			//System.out.println("MovableAdapter.getPosition(): EXCEPTION");
			throw new Exception("Unknown position");
		}
		return(v);
	}
	
	public void move() throws Exception {
		Vector pos;
		Vector posNew=new Vector();
		double v,alpha;
		int alphaDirection,directionNumber;
		
		pos=getPosition();
		v=(double)o.getProperty("v");
		alphaDirection=(int)o.getProperty("alphaDirection");
		directionNumber=(int)o.getProperty("directionNumber");
		alpha=360.0*alphaDirection/(double)directionNumber*Math.PI/180.0;
		System.out.println("MovableAdapter.move(): v="+v+" alphaDirection="+alphaDirection+" directionNumber="+directionNumber+" alpha="+alpha);

		posNew.add((double)pos.elementAt(0)+v*Math.cos(alpha));
		posNew.add((double)pos.elementAt(1)+v*Math.sin(alpha));
		setPosition(posNew);
	}
}

interface UObject {
	Object getProperty(String key);
	void setProperty(String key, Object o) throws Exception;
	void showPosition(String prefix);
}

class Ship implements UObject {
	private static double eps=0.0001;
	private Vector position;
	private double v; // speed
	
	private int directionNumber;
	private int alphaDirection;


	public Ship(double x, double y, int alphaDirection, int directionNumber) {
		//System.out.println("Ship.Ship()");
		position=new Vector();
		position.add(x);
		position.add(y);
		this.alphaDirection=alphaDirection;
		this.directionNumber=directionNumber;
	}

	public Ship(double x, double y, int alphaDirection, int directionNumber, double v) {
		//System.out.println("Ship.Ship()");
		this(x, y, alphaDirection, directionNumber);
		this.v=v;
	}
	
	public Ship(double x, double y, int alphaDirection, int directionNumber, double dx, double dy) {
		//System.out.println("Ship.Ship()");
		this(x, y, alphaDirection, directionNumber);
		setVelocity(dx, dy);
	}

	public Object getProperty(String key) {
		if(key=="position")return(position);
		else if(key=="alphaDirection")return(alphaDirection);
		else if(key=="directionNumber")return(directionNumber);
		else if(key=="v")return(v);
		else return(null);
	}
	
	public void setProperty(String key, Object newValue) throws Exception {
		//System.out.println("Ship.setProperty()");
		if(key=="position")
			position=(Vector)newValue;
		else if(key=="v")
			v=(double)newValue;
		else if(key=="alphaDirection")
			alphaDirection=(int)newValue;
		else
			throw new Exception("property not found");
	}
	
	public void showPosition(String prefix) {
		System.out.println(prefix+"Ship.showPosition(): x="+(double)position.elementAt(0)+", y="+(double)position.elementAt(1));
	}
	
	public void setVelocity(double dx, double dy) {
		double alpha;
		
		v=Math.sqrt(dx*dx+dy*dy);
		if(dx>eps) {
			if(dy>0 || Math.abs(dy)==0)
				alpha=Math.atan(dy/dx);
			else
				alpha=Math.atan(dy/dx)+2*Math.PI;
		} else if(dx<-eps)
			alpha=Math.atan(dy/dx)+Math.PI;
		else if(dy>eps)
			alpha=Math.PI/2;
		else if(dy<-eps)
			alpha=-Math.PI/2;
		else
			alpha=Double.NaN;
		//System.out.println(alpha);
		if(!Double.isNaN(alpha)) {
			alpha*=180.0/Math.PI;  //System.out.println(alpha);
			alphaDirection=(int)Math.ceil(directionNumber*alpha/360.0);
		}
		System.out.println("Ship.setVelocity(): alphaDirection="+alphaDirection+" v="+v);
	}
	
	public double getAlpha() {
		return(360.0*alphaDirection/directionNumber);
	}
}

class m {
	private static double eps=0.0001;
	private static Vector pos;
	private static double alpha;
	
    public static void main(String[] args) {
		Exception e;
		
		e=test(0.0, 0.0, 2, 8, 1, 1, 0.0);	 					if(e==null)System.out.println("Test0 passed\n"); else System.out.println("Test0 FAILED, "+e+"\n"); 
		e=test(12.0, 5.0, 1, (int)Math.pow(2, 19), -7, 3, 0.0);	if(e==null && Math.abs((double)pos.elementAt(0)-5)<eps && Math.abs((double)pos.elementAt(1)-8)<eps)System.out.println("Test1 passed\n"); else System.out.println("Test1 FAILED, "+e+"\n"); 
		e=test(Double.NaN, Double.NaN, 2, 8, 1, 1, 0.0);		if(e!=null)System.out.println("Test3 passed: "+e+"\n"); else System.out.println("Test3 FAILED, "+e+"\n"); 
		e=test(0.0, 0.0, 2, 8, Double.NaN, Double.NaN, 0.0);	if(e!=null)System.out.println("Test4 passed: "+e+"\n"); else System.out.println("Test4 FAILED, "+e+"\n"); 
		
		e=test(0.0, 0.0, 2, 360, 0, 0, 90.0);	 				if(e==null && Math.abs(alpha-92)<eps)System.out.println("Test8.3 passed\n"); else System.out.println("Test8.3 FAILED, "+e+"\n");
    }

	public static Exception test(double x, double y, int alphaDirection, int directionNumber, double dx, double dy, double a) {
		try {
			Ship ship1=new Ship(x, y, alphaDirection, directionNumber, dx, dy);
			MovableAdapter 		aShip1=new MovableAdapter(ship1);
			RotatableAdapter 	rShip1=new RotatableAdapter(ship1);
			
			//aShip1.setPosition(new Vector());
			ship1.showPosition("BEFORE: ");
			aShip1.move();
			ship1.showPosition("AFTER MOVE: ");
			if(a>0.0) {
				rShip1.rotate(a); 
				ship1.showPosition("AFTER ROTATE: ");
			}
			pos=aShip1.getPosition();
			alpha=ship1.getAlpha();
			//System.out.println(pos);
		} catch (Exception e) {
			System.out.println("Some exception");
			return(e);
		}
		return(null);
	}

}
