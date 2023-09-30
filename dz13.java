import java.util.Vector;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.HashSet;
import java.util.NoSuchElementException;

import uobject.*;
import icommand.*;
import irotatable.*;
import imovable.*;
import iburnable.*;

class Interpret {
	String str;
	Set<String> numbers = new HashSet<String>();
	
	public Interpret(String str) {
		this.str=str;
		
		this.numbers.add("0");
		this.numbers.add("1");
		this.numbers.add("2");
		this.numbers.add("3");
		this.numbers.add("4");
		this.numbers.add("5");
		this.numbers.add("6");
		this.numbers.add("7");
		this.numbers.add("8");
		this.numbers.add("9");
	}
	
	public int number(int offset) throws Exception {
		int len=str.length();
		int i;
		
		for(i=0;i<len;i++) {
			try {
				if(!numbers.contains(str.charAt(offset+i)))break;
			} catch (Exception e) {	}
		}
		if(i==0)throw new Exception("Not a number");
		return(i);
	}
	
}

class m {
	static ICommand cmd;
	
    public static void main(String[] args) throws Exception {
		Queue<ICommand> q1=new LinkedList<>();
		int tst;
		EventLoop el1,el2;
		Neighborhoods[] allNghs={null, null};
		Ship[] allShips={null, null, null, null, null};
		String order="{ 'id': '2',  'action': 'Move', 'initialVelocity': 2  	}";
		int offset;
		
		Interpret intr=new Interpret(order);
		
		//offset=intr.number(0); System.out.println("Offset: "+offset);
		//offset=intr.number(51); System.out.println("Offset: "+offset);
		offset=intr.number(50); System.out.println("Offset: "+offset);
		
		return;
/*		
		allNghs[0]=new Neighborhoods(2, 0); 	// neighborhoods {0, 0 <-> 5, 5}, {5, 0 <-> 10, 5}, {0, 5 <-> 5, 10}, {5, 5 <-> 10, 10}
		
		allShips[0]=new Ship(0.0, 0.0, 0.0, 360, 3.0);
		allShips[1]=new Ship(9.0, 9.0, 0.0, 360, 3.0);
		allShips[2]=new Ship(4.0, 4.0, 0.0, 360, 3.0);
		allShips[3]=new Ship(8.0, 8.0, 0.0, 360, 3.0);
		allShips[4]=new Ship(7.0, 7.0, 0.0, 360, 3.0);
		
		
		
		//MovableAdapter 	mShip=new MovableAdapter(allShips[0], 2.0, 2.0);
		MovableAdapter 	mShip=new MovableAdapter(allShips[0], 2.0);
		q1.add(new MoveCommand(mShip));
		
		q1.add(new OrderCommand(order, allShips));
		
		//q1.add(new ChangeVelocityCommand(allShips[0], 3.0, 3.0));

		el1=new EventLoop(q1);
		
		System.out.println("1st loop...");  tst=0;
		cmd=new StartLoopCommand(el1); cmd.exec(); for(Thread t: Thread.getAllStackTraces().keySet())if(t.getId()==el1.getThreadId())tst=1; if(tst==1)System.out.println("Test27 passed\n"); else System.out.println("Test27 FAILED\n");

		//q1.add(new ChangeVelocityCommand(allShips[0], 3.0, 3.0));
//		System.out.println("Adding Hard stop..."); q1.add(new HardStopLoopCommand(el1));
		System.out.println("Adding Soft stop..."); q1.add(new SoftStopLoopCommand(el1));
		//q1.add(new ChangeVelocityCommand(allShips[0], 3.0, 3.0));
		
		while(true) {
			tst=1;
			for(Thread t: Thread.getAllStackTraces().keySet())if(t.getId()==el1.getThreadId())tst=0;
			if(tst>0)break;
			Thread.sleep(1000);
		}
		System.out.println("");
		for(Ship sh: allShips)sh.showProps("");	*/
    }
}


