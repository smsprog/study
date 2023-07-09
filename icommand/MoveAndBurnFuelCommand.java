package icommand;

import java.util.Vector;
import uobject.*;
import imovable.*;
import iburnable.*;

public class MoveAndBurnFuelCommand extends MacroCommand {
	public MoveAndBurnFuelCommand(UObject o, double dx, double dy, double burnRate) throws Exception {
		IMovable	moveAd=new MovableAdapter(o, dx, dy);
		IBurnable	burnAd=new BurnableAdapter(o, burnRate);

		cmds=new Vector<ICommand>();
		cmds.add(new CheckFuelCommand(burnAd));
		cmds.add(new MoveCommand(moveAd));
		cmds.add(new BurnFuelCommand(burnAd));
	}
}
