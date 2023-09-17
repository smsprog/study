package icommand;

import uobject.*;
import imovable.*;
import java.util.Queue;

public class HardStopLoopCommand implements ICommand {
	private EventLoop el;
	
	public HardStopLoopCommand(EventLoop el) throws Exception {
		this.el=el;
	}

	public void exec() throws Exception {
		this.el.setProcessing( (arr) -> { return(true);	} );
	}
}

