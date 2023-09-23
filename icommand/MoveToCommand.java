package icommand;

import uobject.*;
import imovable.*;
import java.util.Queue;

public class MoveToCommand implements ICommand {
	EventLoop el;
	IState from;
	IState to;
	Queue<ICommand> qTarget;
	
	public MoveToCommand(EventLoop el, Queue<ICommand> q, IState from, IState to) throws Exception {
		this.el=el;
		this.qTarget=q;
		this.from=from;
		this.to=to;
	}

	public void exec() throws Exception {
		//this.el.setStateTo();
		Object[] objs={ (Object)this.qTarget };
		this.el.args=objs;
		this.el.setProcessing( (arr) -> {
			ICommand cmd;
			try {
				cmd=((Queue<ICommand>)arr[0]).remove();
				System.out.println("\n"+(Long)arr[1]+": MoveTo new queue CMD="+cmd.getClass().getSimpleName());
				if(cmd.getClass().getSimpleName().equals("RunCommand"))
					cmd.exec();
				else
					qTarget.add(cmd);
			} catch (Exception e) {
				if(e.getClass().getSimpleName().equals("NoSuchElementException")) {
					System.out.println((Long)arr[1]+": No CMD in queue. Sleeping for awhile..."); 
					try { Thread.sleep(1000); } catch(Exception e1) {}
				}
				else
					System.out.println((Long)arr[1]+": Exception from this CMD: "+e);
			}
			return(false);
		} );
		this.from.setStateTo(this.to);
		//.setProcessing( (arr) -> { return(true);	} );
	}
}

