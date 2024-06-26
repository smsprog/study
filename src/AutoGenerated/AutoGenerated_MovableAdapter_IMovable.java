package AutoGenerated;
import imovable.*;
import uobject.*;
import java.util.Vector;
import icommand.IoC;
public class AutoGenerated_MovableAdapter_IMovable implements IMovable {
	UObject obj;
	public AutoGenerated_MovableAdapter_IMovable(UObject obj) {
		System.out.println("AutoGenerated_MovableAdapter_IMovable: new()");
		this.obj = obj;
	}
	public void move(){  }
	public void setPosition(Vector newPosition) throws Exception {
		IoC.Resolve("IMovable:position.set", obj, newPosition);
	}
	public void setVelocity(double dx, double dy) throws Exception {
		IoC.Resolve("IMovable:velocity.set", obj, dx, dy);
	}
}
