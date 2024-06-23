import java.util.Vector;
import java.util.LinkedList;
import java.util.Queue;
import java.util.NoSuchElementException;

import uobject.*;
import icommand.*;
import irotatable.*;
import imovable.*;

class m {
	private static double eps=0.0001;
	private static Vector pos;
	private static double alpha;
	
    public static void main(String[] args) {
		Exception e;
		
		//e=test(0.0, 0.0, 90.0, 8, 1, 1, 0.0);	 					if(e==null)System.out.println("Test0 passed\n"); else System.out.println("Test0 FAILED, "+e+"\n"); 
		//e=test(12.0, 5.0, 10.0, (int)Math.pow(2, 19), -7, 3, 0.0);	if(e==null && Math.abs((double)pos.elementAt(0)-5)<eps && Math.abs((double)pos.elementAt(1)-8)<eps)System.out.println("Test1 passed\n"); else System.out.println("Test1 FAILED, "+e+"\n"); 
		e=test(Double.NaN, Double.NaN, 90.0, 8, 1, 1, 0.0);		if(e!=null)System.out.println("===Test3 passed: "+e+"\n"); else System.out.println("===Test3 FAILED, "+e+"\n"); 
		//e=test(0.0, 0.0, 90.0, 8, Double.NaN, Double.NaN, 0.0);	if(e!=null)System.out.println("Test4 passed: "+e+"\n"); else System.out.println("Test4 FAILED, "+e+"\n"); 
		
		//e=test(0.0, 0.0, 0.0, 360, 1, 0, 90.0);	 				if(e==null && Math.abs(alpha-90)<eps)System.out.println("Test8.3 passed\n"); else System.out.println("Test8.3 FAILED, "+e+"\n");
    }

	public static Exception test(double x, double y, double a, int directionNumber, double dx, double dy, double da) {
		try {
			Queue<ICommand> 	q = new LinkedList<>();
			int i=1;
			Ship 				ship1=new Ship(x, y, a, directionNumber, 0.0);
			RotatableAdapter 	rShip=new RotatableAdapter(ship1, da);
			MovableAdapter 		mShip=new MovableAdapter(ship1, dx, dy);
			
			q.add(new MoveCommand(mShip)); 
			if(da>0.0)q.add(new RotateCommand(rShip)); 
			
			try {
				while(true) {
					ICommand cmd=q.remove();
					//ship1.showProps(i+"-BEFORE: ");
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
		} catch (Exception e) {
			System.out.println("EXCEPTION! "+e);
			return(e);
		}
		return(null);
	}

}

