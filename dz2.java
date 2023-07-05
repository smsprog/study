import java.util.Vector;
import java.util.LinkedList;
import java.util.Queue;
import java.util.NoSuchElementException;

interface ICommand {
	void exec() throws Exception;
}

interface IRotatable {
	void rotate() throws Exception;
}

interface IMovable {
	void move() throws Exception;
	void setPosition(Vector newPosition) throws Exception;
}

interface UObject {
	Object getProperty(String key) throws Exception;
	void setProperty(String key, Object o) throws Exception;
	Vector getPosition() throws Exception;
	//void showPosition(String prefix);
}

class RotateCommand implements ICommand {
	private IRotatable ad;
	
	public RotateCommand(IRotatable ad) throws Exception {
		//System.out.println("RotatableAdapter.RotatableAdapter(): "+a);
		this.ad=ad;
	}

	public void exec() throws Exception {
		ad.rotate();
	}
}

class RotatableAdapter implements IRotatable {
	private static double eps=0.0001;
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

class MoveCommand implements ICommand {
	private IMovable ad;
	
	public MoveCommand(IMovable ad) throws Exception {
		this.ad=ad;
	}

	public void exec() throws Exception {
		ad.move();
	}
}

class MovableAdapter implements IMovable {
	private static double eps=0.0001;
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
		//System.out.println("Ship.setVelocity(): dx="+dx+" dy="+dy+" alphaDirection="+alphaDirection+" directionNumber="+directionNumber);
		
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
			//System.out.println(alphaDirection);
		}
		
		o.setProperty("alphaDirection",alphaDirection);
		o.setProperty("v",v);
		//System.out.println("Ship.setVelocity(): alphaDirection="+alphaDirection+" v="+v);
	}
}

class Ship implements UObject {
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

class m {
	private static double eps=0.0001;
	private static Vector pos;
	private static double alpha;
	
    public static void main(String[] args) {
		Exception e;
		
		e=test(0.0, 0.0, 90.0, 8, 1, 1, 0.0);	 					if(e==null)System.out.println("Test0 passed\n"); else System.out.println("Test0 FAILED, "+e+"\n"); 
		e=test(12.0, 5.0, 10.0, (int)Math.pow(2, 19), -7, 3, 0.0);	if(e==null && Math.abs((double)pos.elementAt(0)-5)<eps && Math.abs((double)pos.elementAt(1)-8)<eps)System.out.println("Test1 passed\n"); else System.out.println("Test1 FAILED, "+e+"\n"); 
		e=test(Double.NaN, Double.NaN, 90.0, 8, 1, 1, 0.0);		if(e!=null)System.out.println("Test3 passed: "+e+"\n"); else System.out.println("Test3 FAILED, "+e+"\n"); 
		e=test(0.0, 0.0, 90.0, 8, Double.NaN, Double.NaN, 0.0);	if(e!=null)System.out.println("Test4 passed: "+e+"\n"); else System.out.println("Test4 FAILED, "+e+"\n"); 
		
		e=test(0.0, 0.0, 0.0, 360, 1, 0, 90.0);	 				if(e==null && Math.abs(alpha-90)<eps)System.out.println("Test8.3 passed\n"); else System.out.println("Test8.3 FAILED, "+e+"\n");
    }

	public static Exception test(double x, double y, double a, int directionNumber, double dx, double dy, double da) {
		try {
			Queue<ICommand> 	q = new LinkedList<>();
			ICommand			cmd;
			int i=1;
			Ship 				ship1=new Ship(x, y, a, directionNumber);
			RotatableAdapter 	rShip=new RotatableAdapter(ship1, da);
			MovableAdapter 		mShip=new MovableAdapter(ship1, dx, dy);
			
			q.add(new MoveCommand(mShip)); 
			if(da>0.0)q.add(new RotateCommand(rShip)); 
			
			try {
				while(true) {
					cmd=q.remove();
					ship1.showProps(i+"-BEFORE: ");
					cmd.exec();
					ship1.showProps(i+"-AFTER: ");
					i++; System.out.println("");
				}
			} catch (NoSuchElementException qException) {
				System.out.println(".");
			}
			pos=ship1.getPosition();
			alpha=ship1.getAlpha();
		} catch (Exception e) {
			System.out.println("EXCEPTION! ");
			return(e);
		}
		return(null);
	}

}
