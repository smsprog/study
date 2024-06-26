package AutoGenerated;
import tank_operations_imovable.*;
import uobject.*;
import java.util.Vector;
import icommand.IoC;
public class AutoGenerated_MovableAdapter_Tank_Operations_IMovable implements Tank_Operations_IMovable {
	UObject obj;
	public AutoGenerated_MovableAdapter_Tank_Operations_IMovable(UObject obj) {
		System.out.println("AutoGenerated_MovableAdapter_Tank_Operations_IMovable: new()");
		this.obj = obj;
	}
	public Vector getPosition() throws Exception {
		return IoC.Resolve("Tank_Operations_IMovable:position.get", obj);
	}
	public Vector setPosition(Vector newValue) throws Exception {
		return IoC.Resolve("Tank_Operations_IMovable:position.set", obj, newValue);
	}
	public Vector getVelocity() throws Exception {
		return IoC.Resolve("Tank_Operations_IMovable:velocity.get", obj);
	}
}
