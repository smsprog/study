package icommand;

import uobject.*;
import imovable.*;

public class OrderCommand implements ICommand {
	String order;
	UObject[] ships;
	ICommand cmd;
	
	public OrderCommand(String order, UObject[] ships) throws Exception {
		this.order=order;
		this.ships=ships;
		
		IFunction f=(arr) -> {
			try {
				ICommand cmd=new MoveCommand(new MovableAdapter((UObject)arr[0], (double)arr[1]));
			} catch (Exception e) {
				System.out.println("Exception while registering IoC func 'Move': "+e);
				cmd=null;
			}
			return(cmd);
		};
		cmd=IoC.Resolve("Scopes.New", "Orders"); cmd.exec(); if(cmd==null)throw new Exception("OrderCommand.exec(): cannot create scope with IoC");
		cmd=IoC.Resolve("Scopes.Current", "Orders"); cmd.exec(); if(cmd==null)throw new Exception("OrderCommand.exec(): cannot change scope with IoC");
		cmd=IoC.Resolve("IoC.Register", "Move", (IFunction)f); cmd.exec(); if(cmd==null)throw new Exception("OrderCommand.exec(): cannot register func with IoC");
		
	}

	public void exec() throws Exception {
		System.out.println("OrderCommand.exec(): "+this.order);
		
		cmd=IoC.Resolve("Scopes.Current", "Orders"); cmd.exec(); if(cmd==null)throw new Exception("OrderCommand.exec(): cannot change scope with IoC");
		cmd=null;
		cmd=IoC.Resolve("Move", this.ships[0], 2.0); cmd.exec(); if(cmd==null)throw new Exception("OrderCommand.exec(): cannot resolve func with IoC");
		
		//moveAd.setVelocity(dx, dy);
	}
}
