package icommand;

import uobject.*;
import imovable.*;

public class ChangeVelocityCommand implements ICommand {
	private IMovable moveAd;
	private double dx, dy;
	
	public ChangeVelocityCommand(UObject o, double dx, double dy) throws Exception {
		moveAd=new MovableAdapter(o);
		this.dx=dx;
		this.dy=dy;
		this.moveAd=moveAd;
	}

	public void exec() throws Exception {
		moveAd.setVelocity(dx, dy);
	}
}