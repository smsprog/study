package icommand;

import java.util.Vector;
import uobject.*;
import imovable.*;
import iburnable.*;

public class MoveAndBurnFuelCommand extends MacroCommand {
	public MoveAndBurnFuelCommand(UObject o, double dx, double dy, double burnRate) throws Exception {
		super(new CheckFuelCommand(new BurnableAdapter(o, burnRate)), new MoveCommand(new MovableAdapter(o, dx, dy)), new BurnFuelCommand(new BurnableAdapter(o, burnRate)));
	}
}
