package icommand;

import uobject.*;
import imovable.*;
import java.util.Queue;

public class SoftStopLoopCommand implements ICommand {
	private EventLoop el;
	
	public SoftStopLoopCommand(EventLoop el) throws Exception {
		this.el=el;
	}

	public void exec() throws Exception {
		this.el.setProcessing(  (arr) -> {
			ICommand cmd;
			try {
				cmd=((Queue<ICommand>)arr[0]).remove();
				System.out.println("\n"+(Long)arr[1]+": CMD="+cmd.getClass().getSimpleName());
				cmd.exec();
			} catch (Exception e) {
				if(e.getClass().getSimpleName().equals("NoSuchElementException")) {
					System.out.println((Long)arr[1]+": No CMD in queue. Exiting..."); 
					return(true);
				}
				else
					System.out.println((Long)arr[1]+": Exception from this CMD: "+e);
			}
			return(false);
		} );
	}
}

