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

class m {
	static ICommand cmd;
	
    public static void main(String[] args) throws Exception {
		Queue<ICommand> q1=new LinkedList<>();
		Queue<ICommand> q2=new LinkedList<>();
		int tst;
		EventLoop el1,el2;
		
		Ship ship1=new Ship(0.0, 0.0, 0.0, 360, 3.0);
		MovableAdapter 		mShip=new MovableAdapter(ship1, 2.0, 2.0);
			
		q1.add(new MoveCommand(mShip));
		q1.add(new ChangeVelocityCommand(ship1, 3.0, 3.0));
		
		q2.add(new MoveCommand(mShip));
		q2.add(new ChangeVelocityCommand(ship1, 3.0, 3.0));
		
		el1=new EventLoop(q1);
		el2=new EventLoop(q2);
		
		System.out.println("1st loop...");  tst=0;
		cmd=new StartLoopCommand(el1); cmd.exec(); for(Thread t: Thread.getAllStackTraces().keySet())if(t.getId()==el1.getThreadId())tst=1; if(tst==1)System.out.println("Test27 passed\n"); else System.out.println("Test27 FAILED\n");

		System.out.println("2nd loop..."); tst=0;
		cmd=new StartLoopCommand(el2); cmd.exec(); for(Thread t: Thread.getAllStackTraces().keySet())if(t.getId()==el1.getThreadId())tst=1; if(tst==1)System.out.println("Test28 passed\n"); else System.out.println("Test28 FAILED\n");

		q1.add(new ChangeVelocityCommand(ship1, 3.0, 3.0));
		q2.add(new ChangeVelocityCommand(ship1, 3.0, 3.0));
		System.out.println("Adding Hard stop..."); q1.add(new HardStopLoopCommand(el1));
		System.out.println("Adding Soft stop..."); q2.add(new SoftStopLoopCommand(el2));
		q1.add(new ChangeVelocityCommand(ship1, 3.0, 3.0));
		q2.add(new ChangeVelocityCommand(ship1, 3.0, 3.0));
    }
}


