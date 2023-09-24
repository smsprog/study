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

class m {
	static ICommand cmd;
	
	public static void printNeighborhoods(Object[] nghs) {
		for(int i=0;i<nghs.length;i++) {
			System.out.println("Neigh"+i+": "+((Set<Ship>)nghs[i]) );
		}
	}
	
    public static void main(String[] args) throws Exception {
		Queue<ICommand> q1=new LinkedList<>();
		int tst;
		EventLoop el1;
		Ship[] allShips={null, null, null, null, null};
		MovableAdapter mShip;
		Object[] allNeighborhoods={null, null, null, null}; // neighborhoods {0, 0 <-> 5, 5}, {5, 0 <-> 10, 5}, {0, 5 <-> 5, 10}, {5, 5 <-> 10, 10}
		Set<Ship> nghbrhd;
		
		allShips[0]=new Ship(0.0, 0.0, 0.0, 360, 3.0);
		allShips[1]=new Ship(9.0, 9.0, 0.0, 360, 3.0);
		allShips[2]=new Ship(4.0, 4.0, 0.0, 360, 3.0);
		allShips[3]=new Ship(8.0, 8.0, 0.0, 360, 3.0);
		allShips[4]=new Ship(7.0, 7.0, 0.0, 360, 3.0);
		
		for(int i=0;i<allNeighborhoods.length;i++)allNeighborhoods[i]=new HashSet<Ship>();
		
		/*
		((Set<Ship>)allNeighborhoods[0]).add(allShips[0]);
		((Set<Ship>)allNeighborhoods[3]).add(allShips[1]);
		((Set<Ship>)allNeighborhoods[0]).add(allShips[2]);
		((Set<Ship>)allNeighborhoods[3]).add(allShips[3]);
		((Set<Ship>)allNeighborhoods[3]).add(allShips[4]);
		*/
		for(Ship sh: allShips)q1.add(new UpdateNeighborhoodCommand(sh, allNeighborhoods, q1));

/*		
		for(Ship sh: allShips) {
			q1.add(new MoveCommand(new MovableAdapter(sh, 2.0, 2.0)));
			q1.add(new UpdateNeighborhoodCommand(sh, allNeighborhoods, q1));
		}
*/		
		q1.add(new MoveCommand(new MovableAdapter(allShips[2], 2.0, 2.0)));
		q1.add(new UpdateNeighborhoodCommand(allShips[2], allNeighborhoods, q1));
		
		printNeighborhoods(allNeighborhoods);
		
		el1=new EventLoop(q1);
		
		System.out.println("1st loop..."); tst=0;
		cmd=new StartLoopCommand(el1); cmd.exec(); for(Thread t: Thread.getAllStackTraces().keySet())if(t.getId()==el1.getThreadId())tst=1; if(tst==1)System.out.println("Test27 passed\n"); else System.out.println("Test27 FAILED\n");

		System.out.println("Adding Soft stop..."); q1.add(new SoftStopLoopCommand(el1));
	
		while(true) {
			tst=1;
			for(Thread t: Thread.getAllStackTraces().keySet())if(t.getId()==el1.getThreadId())tst=0;
			if(tst>0)break;
			Thread.sleep(1000);
		}
		printNeighborhoods(allNeighborhoods);
		for(Ship sh: allShips)sh.showProps("");
    }
}


