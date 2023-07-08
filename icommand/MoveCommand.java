package icommand;

import java.util.Vector;
import imovable.*;

public class MoveCommand implements ICommand {
	private IMovable ad;
	
	public MoveCommand(IMovable ad) throws Exception {
		this.ad=ad;
	}

	public void exec() throws Exception {
		ad.move();
	}
}