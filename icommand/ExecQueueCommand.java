package icommand;

import uobject.*;
import imovable.*;
import java.util.Queue;

public class ExecQueueCommand implements ICommand {
	private Code c;
	
	public ExecQueueCommand(Code c) throws Exception {
		this.c=c;
	}

	public void exec() throws Exception {
		Thread t;
		//t=new Thread(c, "My thread");
		t=new Thread(c);
		t.start();
		System.out.println("ThreadName: "+t.getName()+" id: "+t.getId()+" state: "+t.getState());
	}
}

