package icommand;

import java.util.Queue;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import uobject.*;

public class Code implements Runnable {
	static ThreadLocal< Queue<ICommand> > sc=new ThreadLocal<>();
	
	public Code () {
		System.out.println("Code created");
	}
	
	public void run() {
		try {
			Queue<ICommand> q=sc.get();
			
			q=new LinkedList<>();
			Ship ship1=new Ship(0.0, 0.0, 0.0, 360, 3.0);
			
			q.add(new MoveAndBurnFuelCommand(ship1, 2.0, 2.0, 1.0));
			q.add(new MoveAndBurnFuelCommand(ship1, 2.0, 2.0, 1.0));
			q.add(new ChangeVelocityCommand(ship1, 3.0, 3.0));
			
			try {
				while(true) {
					System.out.println("Queue size="+q.size());
					ICommand cmd=q.remove();
					System.out.println("\nCMD="+cmd.getClass().getSimpleName());
					try {
						cmd.exec();
					} catch (Exception e) {
						System.out.println("Exception from this CMD: "+e);
					}
				}
			} catch (NoSuchElementException qException) {
				Thread.sleep(3000); System.out.println("\nSleep");		
				System.out.println(".");
			}
		} catch (Exception e) {
			System.out.println("EXCEPTION in the thread: "+e);
		}
	}
}