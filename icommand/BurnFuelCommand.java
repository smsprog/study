package icommand;

import iburnable.*;

public class BurnFuelCommand implements ICommand {
	IBurnable ad;

	public BurnFuelCommand(IBurnable ad) throws Exception {
		this.ad=ad;
	}

	public void exec() throws Exception {
		ad.burn();
	}
}