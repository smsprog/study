import java.util.Vector;
import java.util.LinkedList;
import java.util.Queue;
import java.util.NoSuchElementException;

import uobject.*;
import icommand.*;
import irotatable.*;
import imovable.*;
import iburnable.*;

class m {
	private static double eps=0.0001;
	private static Vector pos;
	private static double alpha, fc, v;
	
    public static void main(String[] args) {
		Exception e;
		
		//e=test(0.0, 0.0, 90.0, 8, 1, 1, 0.0);	 					if(e==null)System.out.println("Test0 passed\n"); else System.out.println("Test0 FAILED, "+e+"\n"); 
		e=test(12.0, 5.0, 10.0, (int)Math.pow(2, 19), -7, 3, 0.0, 7.61577);	if(e==null && Math.abs((double)pos.elementAt(0)-5)<eps && Math.abs((double)pos.elementAt(1)-8)<eps && Math.abs(fc)<eps)System.out.println("===Test1 passed\n"); else System.out.println("===Test1 FAILED, "+e+" "+fc+"\n"); 
		e=test(12.0, 5.0, 10.0, (int)Math.pow(2, 19), -7, 3, 0.0, 1e-7);	if(e!=null && e.getClass().getSimpleName().equals("CommandException"))System.out.println("===Test1.1 passed: "+e+"\n"); else System.out.println("===Test1.1 FAILED, "+e+"\n"); 
		//e=test(Double.NaN, Double.NaN, 90.0, 8, 1, 1, 0.0);		if(e!=null)System.out.println("Test3 passed: "+e+"\n"); else System.out.println("Test3 FAILED, "+e+"\n"); 
		//e=test(0.0, 0.0, 90.0, 8, Double.NaN, Double.NaN, 0.0);	if(e!=null)System.out.println("Test4 passed: "+e+"\n"); else System.out.println("Test4 FAILED, "+e+"\n"); 
		e=test(0.0, 0.0, 0.0, 360, 1, 0, 90.0, 10.0);	if(e==null && Math.abs(alpha-90)<eps && Math.abs(v-1)<eps)System.out.println("===Test8.3 passed\n"); else System.out.println("===Test8.3 FAILED, "+e+"\n");
		e=test(0.0, 0.0, 0.0, 360, 3, 4, 0.0, 10.0);	if(e==null && Math.abs(alpha-0)<eps && Math.abs(v-5)<eps)System.out.println("===Test8.4 passed\n"); else System.out.println("===Test8.4 FAILED, "+e+"\n");
		
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

class ExceptionHandler {
	public static ICommand Handle(Exception e, ICommand cmd, Queue<ICommand> q) throws Exception {
						//q.add(new RepeatCommand(q, cmd));
		String eName=e.getClass().getSimpleName();
		String cmdName=cmd.getClass().getSimpleName();
		
		System.out.println("Handle EXCEPTION: "+eName+" "+cmdName);
		if(cmdName.equals("MoveCommand")) {
			System.out.println("+command in queue: "+cmdName);
			return(new AddCommand(q, new RetryCommand(cmd)));
//		else if(cmdName.equals("RetryCommand"))
//			return(new AddCommand(q, new SecondRetryCommand(cmd)));
		}else if(cmdName.equals("CheckFuelCommand") || cmdName.equals("MacroCommand") || cmdName.equals("MoveAndBurnFuelCommand")) {
			throw e;
		} else {
			System.out.println("+command in queue: LogCommand");
			return(new AddCommand(q, new LogCommand(e)));
		}
	}
}
