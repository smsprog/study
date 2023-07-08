package icommand;

import java.util.Vector;
import irotatable.*;

public class RotateCommand implements ICommand {
	private IRotatable ad;
	
	public RotateCommand(IRotatable ad) throws Exception {
		//System.out.println("RotatableAdapter.RotatableAdapter(): "+a);
		this.ad=ad;
	}

	public void exec() throws Exception {
		ad.rotate();
	}
}
