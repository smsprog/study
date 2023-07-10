package icommand;

import java.util.Vector;
import irotatable.*;
import uobject.*;

public class RotateAndChangeVelocityCommand extends MacroCommand {
	public RotateAndChangeVelocityCommand(UObject o, double da, double dx, double dy) throws Exception {
		//System.out.println("RotateAndChangeVelocityCommand()");
		super(new RotateCommand(new RotatableAdapter(o, da)), new ChangeVelocityCommand(o, dx, dy));
		double v=(double)o.getProperty("v"); // check if the object has velocity
	}
} 
