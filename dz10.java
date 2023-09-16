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

class EventLoopStateUsual implements IState {
	IState to;
	EventLoop el;
	
	public EventLoopStateUsual(EventLoop el) {
		this.el=el;
	}
	
	public void setStateTo(IState to) {
		this.to=to;
	}
	
	public IState handle() throws Exception {
		System.out.println("EventLoopStateUsual.next()");
		if(el.isStopped())this.to=null;
		//return(IoC.Resolve("EventLoopState", , this));
		return(to);
	}
}

class EventLoopStateMoveTo implements IState {
	IState to;
	EventLoop el;
	
	public EventLoopStateMoveTo(EventLoop el) {
		this.el=el;
	}
	
	public void setStateTo(IState to) {
		this.to=to;
	}
	
	public IState handle() throws Exception {
		System.out.println("EventLoopStateMoveTo.next()");
		if(el.isStopped())this.to=null;
		return(to);
	}
}

class EventLoopStateStopped implements IState {
	IState to;
	EventLoop el;
	
	public EventLoopStateStopped(EventLoop el) {
		this.el=el;
	}
	
	public void setStateTo(IState to) {
		this.to=to;
	}
	
	public IState handle() throws Exception {
		int tst=0;
		ICommand cmd;
		
		System.out.println("EventLoopStateStopped.next()");
		cmd=new StartLoopCommand(el); cmd.exec(); for(Thread t: Thread.getAllStackTraces().keySet())if(t.getId()==el.getThreadId())tst=1; if(tst==1)System.out.println("Test27 passed\n"); else System.out.println("Test27 FAILED\n");
		return(to);
	}
}


class m {
	static ICommand cmd;
	
    public static void main(String[] args) throws Exception {
		Queue<ICommand> q1=new LinkedList<>();
		Queue<ICommand> q2=new LinkedList<>();
		int tst;
		EventLoop el1,el2;
		IState nextState;
		
		Ship ship1=new Ship(0.0, 0.0, 0.0, 360, 3.0);
		MovableAdapter 		mShip=new MovableAdapter(ship1, 2.0, 2.0);

		el1=new EventLoop(q1);

		IState	su=new EventLoopStateUsual(el1);
		IState	sm=new EventLoopStateMoveTo(el1);
		IState	ss=new EventLoopStateStopped(el1);
		
		q1.add(new MoveCommand(mShip));
		q1.add(new ChangeVelocityCommand(ship1, 3.0, 3.0));
		q1.add(new MoveToCommand(el1, q2, su, sm));
		q1.add(new ChangeVelocityCommand(ship1, 2.0, 2.0));
		q1.add(new ChangeVelocityCommand(ship1, 1.0, 1.0));
		q1.add(new RunCommand(el1, sm, su));
		q1.add(new HardStopLoopCommand(el1));
		
		//q2.add(new MoveCommand(mShip));
		//q2.add(new ChangeVelocityCommand(ship1, 3.0, 3.0));

		//el2=new EventLoop(q2);
		
		su.setStateTo(su);
		sm.setStateTo(sm);
		ss.setStateTo(su);
		
		/*
		cmd=IoC.Resolve("Scopes.New", "scope1"); cmd.exec(); if(cmd!=null)System.out.println("Test15 passed\n"); else System.out.println("Test15 FAILED\n");
		cmd=IoC.Resolve("Scopes.Current", "scope1"); cmd.exec(); if(cmd!=null)System.out.println("Test17 passed\n"); else System.out.println("Test17 FAILED\n");
		cmd=IoC.Resolve("IoC.Register", "EventLoopState", (IFunction)((arr) -> { return(null); } ) ); cmd.exec(); System.out.println("Test13 passed\n");
		*/
		
		nextState=ss;
		while((nextState=nextState.handle())!=null)Thread.sleep(1000);
		
		System.out.println("Queue2:"+q2);

		//System.out.println("2nd loop..."); tst=0;
		//cmd=new StartLoopCommand(el2); cmd.exec(); for(Thread t: Thread.getAllStackTraces().keySet())if(t.getId()==el1.getThreadId())tst=1; if(tst==1)System.out.println("Test28 passed\n"); else System.out.println("Test28 FAILED\n");

		q1.add(new ChangeVelocityCommand(ship1, 3.0, 3.0));
		//q2.add(new ChangeVelocityCommand(ship1, 3.0, 3.0));
		//System.out.println("Adding Hard stop..."); q1.add(new HardStopLoopCommand(el1));
		//System.out.println("Adding Soft stop..."); q2.add(new SoftStopLoopCommand(el2));
		q1.add(new ChangeVelocityCommand(ship1, 3.0, 3.0));
		//q2.add(new ChangeVelocityCommand(ship1, 3.0, 3.0));
    }
}


