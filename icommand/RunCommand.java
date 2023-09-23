package icommand;

import uobject.*;
import imovable.*;
import java.util.Queue;

public class RunCommand implements ICommand {
	EventLoop el;
	IState from;
	IState to;
	
	public RunCommand(EventLoop el, IState from, IState to) throws Exception {
		this.el=el;
		this.from=from;
		this.to=to;
	}

	public void exec() throws Exception {
		//this.el.setStateTo();
		Object[] objs={ null };
		this.el.args=objs;
		this.el.setDefaultStrategy();
		this.from.setStateTo(this.to);
	}
}

