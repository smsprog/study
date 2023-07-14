import java.util.Vector;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.Queue;
import java.util.NoSuchElementException;

import uobject.*;
import icommand.*;
import irotatable.*;
import imovable.*;
import iburnable.*;


interface IFunction {
  Object run(Object obj[]);
}


class RegisterCommand implements ICommand {
	Dictionary<String, IFunction> scope;
	String cname;
	IFunction f;
	
	public RegisterCommand(Dictionary<String, IFunction> sc, String cname, IFunction f) throws Exception {
		this.scope=sc;
		this.cname=cname;
		this.f=f;
	}

	public void exec() throws Exception {
		scope.put((String)cname, (IFunction)f);
	}
}


class IoC {
	static Dictionary<String, Class> dict=new Hashtable<>() {{	put("java.lang.Double", Double.TYPE); 
																put("java.lang.Integer", Integer.TYPE); }};

	static Dictionary<String, IFunction> scope1=new Hashtable<>();

	static void ShowScopes() {
		//for (Object p: objects)
		System.out.println("IoC.ShowScopes()");
		Enumeration<String> k = scope1.keys();
		
        while (k.hasMoreElements()) {
            String key = k.nextElement();
            System.out.println("Scope: key="+key+", value="+scope1.get(key));
        }
	}

/*	static <T> T Resolve2(String scope, String depName, IFunction f) throws Exception {
			scope1.put(depName, f);
			Object[] arr={123, 456};
			System.out.println(f.run(arr));
			return(null);
	} */
	
	static <T> T Resolve(String cname, Object... objects) throws Exception {
		Class[] cArg=new Class[objects.length];
		Class cl;
		int k=0;
		IFunction f;
		ICommand cmd=null;
		
		if (cname=="IoC.Register") {
			for (Object p: objects) {
				System.out.println("IoC.Register: "+p+" "+p.getClass());
			}
			return((T)new RegisterCommand(scope1, (String)objects[0], (IFunction)objects[1]));
			//f=(IFunction)objects[1];
			//Object[] arr={12, 5.0, 10.0, 360, 7.61577};
			//System.out.println(f.run(arr));
		} else if(cname.substring(0, 5)=="Scope.") {
			System.out.println("Scope: "+cname);
		} else { // call Function
			/* 
			for (Object p: objects) {
				//System.out.println(p.getClass()+" "+dict.get(p.getClass().getName()));
				cl=dict.get(p.getClass().getName());
				if(cl!=null)
					cArg[k]=dict.get(p.getClass().getName());
				else
					cArg[k]=p.getClass();
				k++;
			} 
			return((T)Class.forName(cname).getDeclaredConstructor(cArg).newInstance(objects));
			*/
			f=scope1.get(cname);
			if(f!=null)
				return((T)f.run(objects));
			else
				System.out.println("IoC.Resolve: no such function "+cname);
		}
			
		return((T)cmd);
	}
}

class m {
	private static double eps=0.0001;
	private static Vector pos;
	private static double alpha, fc, v;
	
    public static void main(String[] args) throws Exception {
		Exception e=null;
		Ship ship1=null;//=new Ship(12.0, 5.0, 10.0, 360, 7.61577);
		ICommand cmd;
		
		ship1=IoC.Resolve("uobject.Ship", 12.0, 5.0, 10.0, 360, 7.61577); if(ship1==null)System.out.println("Test10 passed\n"); else System.out.println("Test10 FAILED, "+ship1+"\n"); 
		cmd=IoC.Resolve("IoC.Register", "X1", (IFunction)((x) -> (int)x[0]+1) ); cmd.exec(); if(cmd!=null)System.out.println("Test11 passed\n"); else System.out.println("Test11 FAILED\n");
		cmd=IoC.Resolve("IoC.Register", "Str2", (IFunction)((str) -> {System.out.println("string: "+str[0]+" "+str[1]); return(null);}) ); cmd.exec(); if(cmd!=null)System.out.println("Test12 passed\n"); else System.out.println("Test12 FAILED\n");
		cmd=IoC.Resolve("IoC.Register", "uobject.Ship", (IFunction)((arr) -> new Ship((double)arr[0], (double)arr[1], (double)arr[2], (int)arr[3], (double)arr[4])) ); cmd.exec(); System.out.println("Test13 passed\n");
		
		IoC.ShowScopes();
		
		ship1=IoC.Resolve("uobject.Ship", 12.0, 5.0, 10.0, 360, 7.61577); if(ship1!=null)System.out.println("Test14 passed\n"); else System.out.println("Test14 FAILED, "+e+"\n");
		
		//System.out.println(Integer.TYPE.getClass());
		
		
		/*
		//e=test(0.0, 0.0, 90.0, 8, 1, 1, 0.0);	 					if(e==null)System.out.println("Test0 passed\n"); else System.out.println("Test0 FAILED, "+e+"\n"); 
		e=test(12.0, 5.0, 10.0, (int)Math.pow(2, 19), -7, 3, 0.0, 7.61577);	if(e==null && Math.abs((double)pos.elementAt(0)-5)<eps && Math.abs((double)pos.elementAt(1)-8)<eps && Math.abs(fc)<eps)System.out.println("===Test1 passed\n"); else System.out.println("===Test1 FAILED, "+e+" "+fc+"\n"); 
		e=test(12.0, 5.0, 10.0, (int)Math.pow(2, 19), -7, 3, 0.0, 1e-7);	if(e!=null && e.getClass().getSimpleName().equals("CommandException"))System.out.println("===Test1.1 passed: "+e+"\n"); else System.out.println("===Test1.1 FAILED, "+e+"\n"); 
		//e=test(Double.NaN, Double.NaN, 90.0, 8, 1, 1, 0.0);		if(e!=null)System.out.println("Test3 passed: "+e+"\n"); else System.out.println("Test3 FAILED, "+e+"\n"); 
		//e=test(0.0, 0.0, 90.0, 8, Double.NaN, Double.NaN, 0.0);	if(e!=null)System.out.println("Test4 passed: "+e+"\n"); else System.out.println("Test4 FAILED, "+e+"\n"); 
		e=test(0.0, 0.0, 0.0, 360, 1, 0, 90.0, 10.0);	if(e==null && Math.abs(alpha-90)<eps && Math.abs(v-1)<eps)System.out.println("===Test8.3 passed\n"); else System.out.println("===Test8.3 FAILED, "+e+"\n");
		e=test(0.0, 0.0, 0.0, 360, 3, 4, 0.0, 10.0);	if(e==null && Math.abs(alpha-0)<eps && Math.abs(v-5)<eps)System.out.println("===Test8.4 passed\n"); else System.out.println("===Test8.4 FAILED, "+e+"\n");
		*/
    }

	public static Exception test(double x, double y, double a, int directionNumber, double dx, double dy, double da, double fuelCapacity) {
		try {
			Queue<ICommand> 	q = new LinkedList<>();
			int i=1;
			Ship 				ship1=new Ship(x, y, a, directionNumber, fuelCapacity);
			RotatableAdapter 	rShip=new RotatableAdapter(ship1, da);
			//MovableAdapter 		mShip=new MovableAdapter(ship1, dx, dy);
			//BurnableAdapter 	bShip=new BurnableAdapter(ship1, 1);
			
			
			//q.add(new CheckFuelCommand(bShip));
			//q.add(new MoveCommand(mShip));
			//q.add(new BurnFuelCommand(bShip));
			//if(da>0.0)q.add(new RotateCommand(rShip)); 

			//q.add(new MacroCommand(new CheckFuelCommand(bShip), new MoveCommand(mShip), new BurnFuelCommand(bShip)));
			q.add(new MoveAndBurnFuelCommand(ship1, dx, dy, 1.0));
			if(da>0.0)q.add(new RotateAndChangeVelocityCommand(ship1, da, dx, dy));
			q.add(new ChangeVelocityCommand(ship1, dx, dy));
			//q.add(new MacroCommand(new MoveCommand(mShip), new MoveCommand(mShip)));
			
			try {
				while(true) {
					System.out.println("Queue size="+q.size());
					ICommand cmd=q.remove();
					System.out.println("CMD="+cmd.getClass().getSimpleName());
					ship1.showProps(i+"-BEFORE: ");
					try {
						cmd.exec();
					} catch (Exception e) {
						ExceptionHandler.Handle(e, cmd, q).exec();
					}
					ship1.showProps(i+"-AFTER: ");
					i++; System.out.println("");
				}
			} catch (NoSuchElementException qException) {
				System.out.println(".");
			}
			pos=ship1.getPosition();
			alpha=ship1.getAlpha();
			fc=ship1.getFuelCapacity();
			v=ship1.getVelocity();
		} catch (Exception e) {
			System.out.println("EXCEPTION! "+e);
			return(e);
		}
		return(null);
	}

}


