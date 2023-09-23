package icommand;

import java.util.Vector;
import imovable.*;
import uobject.*;

public class CheckCollisionCommand implements ICommand {
	private Ship sh1, sh2;
	
	public CheckCollisionCommand(Ship sh1, Ship sh2) throws Exception {
		this.sh1=sh1;
		this.sh2=sh2;
	}

	public void exec() throws Exception {
		System.out.println("CheckCollisionCommand(NOT IMPLEMENTED): "+sh1+" "+sh2);
	}
}