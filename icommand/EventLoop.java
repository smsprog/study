package icommand;

import java.util.Queue;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import uobject.*;

public class EventLoop implements Runnable {
	//static ThreadLocal< Queue<ICommand> > sc=new ThreadLocal<>();
	Queue<ICommand> q;
	Long threadId;
	Boolean stop;
	IFunction f;
	
	public EventLoop(Queue<ICommand> q) {
		//System.out.println("EventLoop constructor");
		this.q=q;
		this.stop=false;
		this.f=(arr) -> {
			ICommand cmd;
			try {
				cmd=((Queue<ICommand>)arr[0]).remove();
				System.out.println("\n"+this.threadId+": CMD="+cmd.getClass().getSimpleName());
				cmd.exec();
			} catch (Exception e) {
				if(e.getClass().getSimpleName().equals("NoSuchElementException")) {
					System.out.println(this.threadId+": No CMD in queue. Sleeping for awhile..."); 
					try { Thread.sleep(100); } catch(Exception e1) {}
				}
				else
					System.out.println(this.threadId+": Exception from this CMD: "+e);
			}
			return(false);
		};
	}
	
	public void setProcessing(IFunction f) {
		this.f=f;
	}
	
	public void setThreadId(Long threadId) {
		this.threadId=threadId;
	}
	
	public Long getThreadId() {
		return(this.threadId);
	}
	
	public void run() {
		ICommand cmd;
		Object[] obj={ null, null };

		try {
			while(!stop) {
				System.out.println(this.threadId+": Queue size="+q.size());
				obj[0]=this.q;
				obj[1]=this.threadId;
				System.out.println(this.threadId+": Loop function: "+this.f);
				stop=(Boolean)this.f.run(obj);

				Thread.sleep(1000);
			}
		} catch (Exception e) {
			System.out.println(this.threadId+": EXCEPTION in the thread: "+e);
		}
	}
}