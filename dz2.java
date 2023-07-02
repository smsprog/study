import java.util.Vector;

interface IMovable {
	Vector getPosition();
	void setPosition(Vector newPosition);
	void move();
	void setVelocity(double dx,double dy);
}

interface UObject {
	Object getProperty(String key);
	void setProperty(String key, Object o);
	void showPosition(String prefix);
}


class MovableAdapter implements IMovable {
	private UObject o;
	
	public MovableAdapter(UObject o) {
		this.o=o;
	}

	public void setPosition(Vector newPosition) {
		o.setProperty("position",newPosition);
		//o.showPosition("MovableAdapter: ");
	}
	
	public Vector getPosition() {
		Vector v;
		v=(Vector)o.getProperty("position");
		//o.showPosition("MovableAdapter: ");
		return(v);
	}
	
	public void move() {
		Vector pos;
		Vector posNew=new Vector();
		double v,alpha;
		int alphaDirection,directionNumber;
		
		pos=getPosition();
		v=(double)o.getProperty("v");
		alphaDirection=(int)o.getProperty("alphaDirection");
		directionNumber=(int)o.getProperty("directionNumber");
		alpha=360*alphaDirection/(double)directionNumber*Math.PI/180;
		System.out.println("MovableAdapter.move(): "+v+" "+alphaDirection+" "+directionNumber+" "+alpha);

		posNew.add((double)pos.elementAt(0)+v*Math.cos(alpha));
		posNew.add((double)pos.elementAt(1)+v*Math.sin(alpha));
		setPosition(posNew);
	}
	
	public void setVelocity(double dx, double dy) {
		double alpha,v;
		
		v=Math.sqrt(dx*dx+dy*dy);
		alpha=Math.asin(dy/v)*180/Math.PI;
		o.setProperty("v",v);
		o.setProperty("alphaDirection",(int)o.getProperty("directionNumber")*alpha/360);
		System.out.println("MovableAdapter.setVelocity(): "+alpha+" "+v);
	}
}

class Ship implements UObject {
	private Vector position;
	private double v; // speed
	
	private int directionNumber;
	private int alphaDirection;

	public Ship(double x, double y, double v, int alphaDirection, int directionNumber) {
		//System.out.println("Ship.Ship()");
		position=new Vector();
		position.add(x);
		position.add(y);
		this.v=v;
		this.alphaDirection=alphaDirection;
		this.directionNumber=directionNumber;
	}

	public Object getProperty(String key) {
		if(key=="position")return(position);
		else if(key=="alphaDirection")return(alphaDirection);
		else if(key=="directionNumber")return(directionNumber);
		else if(key=="v")return(v);
		else return(null);
	}
	
	public void setProperty(String key, Object newValue) {
		//System.out.println("Ship.setProperty()");
		if(key=="position") {
			position=(Vector)newValue;
		}
	}
	
	public void showPosition(String prefix) {
		System.out.println(prefix+"Ship.showPosition(): x="+(double)position.elementAt(0)+", y="+(double)position.elementAt(1));
	}
}

class m {
	private static double eps=0.0001;
	private static Vector pos;
	private static double[] v=new double[3];
	
    public static void main(String[] args) {
		Exception e;
		
		v[0]=1.4142; 		e=test(0.0, 0.0, v, 1, 8);  if(e==null)System.out.println("Test0 passed"); else System.out.println("Test0 FAILED, "+e); System.out.println("");
		v[0]=0; v[1]=-7; v[2]=3;	e=test(12.0,5.0, v, 1, 8);  if(e==null && Math.abs((double)pos.elementAt(0)-5)<eps && Math.abs((double)pos.elementAt(1)-8)<eps)System.out.println("Test1 passed"); else System.out.println("Test1 FAILED, "+e); System.out.println("");
    }

	public static Exception test(double x, double y, double[] v, int alphaDirection, int directionNumber) {
		try {
			Ship ship1=new Ship(x, y, v[0], alphaDirection, directionNumber);
			MovableAdapter aShip1=new MovableAdapter(ship1);
			
			if(v[1] !=0 && v[2] !=0)aShip1.setVelocity(v[1], v[2]);

			//aShip1.setPosition(new Vector());
			ship1.showPosition("BEFORE: ");
			aShip1.move();
			ship1.showPosition("AFTER: ");
			pos=aShip1.getPosition();
			//System.out.println(pos);
		} catch (Exception e) {
			System.out.println("Some exception");
			return(e);
		}
		return(null);
	}

}
