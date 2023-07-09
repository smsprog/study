package icommand;

import iburnable.*;

public class CheckFuelCommand implements ICommand {
	IBurnable ad;

	public CheckFuelCommand(IBurnable ad) throws Exception {
		this.ad=ad;
	}

	public void exec() throws Exception {
		ad.check();
	}
}