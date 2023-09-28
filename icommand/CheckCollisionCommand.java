package icommand;

import java.lang.Math;
import java.util.Vector;
import imovable.*;
import uobject.*;

class Handler {
	Handler nextHandler;
	
	public Handler(Handler nextHandler) {
		this.nextHandler=nextHandler;
	}
	
	public void handle() throws Exception {
		nextHandler.handle();
	}
}

public class CheckCollisionCommand extends Handler implements ICommand {
	private Ship sh1, sh2;
	
	public CheckCollisionCommand(Ship sh1, Ship sh2, Handler nextHandler) throws Exception {
		super(nextHandler);
		this.sh1=sh1;
		this.sh2=sh2;
	}

	public void exec() throws Exception {
		this.handle();
	}

	public void handle() throws Exception {
		double dist;
		Vector pos1,pos2;
		String str="";
		
		pos1=sh1.getPosition();
		pos2=sh2.getPosition();
		
		dist=Math.sqrt(Math.pow((double)pos1.elementAt(0)-(double)pos2.elementAt(0), 2)+Math.pow((double)pos1.elementAt(1)-(double)pos2.elementAt(1), 2));
		if(dist<Globals.epsCollision)str="BANG!";
		System.out.println("CheckCollisionCommand(NOT IMPLEMENTED): "+sh1+" "+sh2+" dist="+dist+" "+str);
		
		if(str.equals(""))
			super.handle();
	}
}
