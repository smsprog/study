package icommand;

import java.util.Vector;
import irotatable.*;
import uobject.*;

public class RotateAndChangeVelocityCommand extends MacroCommand {
	public RotateAndChangeVelocityCommand(UObject o, double da, double dx, double dy) throws Exception {
		//System.out.println("RotateAndChangeVelocityCommand()");
		IRotatable	rotateAd=new RotatableAdapter(o, da);

		cmds=new Vector<ICommand>();
		cmds.add(new RotateCommand(rotateAd));
		cmds.add(new ChangeVelocityCommand(o, dx, dy));
	}
} 
