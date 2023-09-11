package icommand;

import uobject.*;
import imovable.*;
import java.util.Queue;

public class StartLoopCommand implements ICommand {
	private EventLoop el;
	
	public StartLoopCommand(EventLoop el) throws Exception {
		this.el=el;
	}

	public void exec() throws Exception {
		Thread t;
		t=new Thread(el);
		el.setThreadId(t.getId());
		t.start();
		System.out.println(t.getName()+": id: "+t.getId()+" state: "+t.getState());
	}
}

