package AutoGenerated;
import uobject.*;
import icommand.*;
public class AutoGenerated_new {
	public static void IoCRegister() throws Exception {
		ICommand cmd;
		cmd=IoC.Resolve("IoC.Register", "Tank_Operations_IMovable", (IFunction)((arr) -> new AutoGenerated_MovableAdapter_Tank_Operations_IMovable((UObject)arr[0]) ) ); cmd.exec();
		cmd=IoC.Resolve("IoC.Register", "IRotatable", (IFunction)((arr) -> new AutoGenerated_RotatableAdapter_IRotatable((UObject)arr[0]) ) ); cmd.exec();
		cmd=IoC.Resolve("IoC.Register", "IMovable", (IFunction)((arr) -> new AutoGenerated_MovableAdapter_IMovable((UObject)arr[0]) ) ); cmd.exec();
		cmd=IoC.Resolve("IoC.Register", "IBurnable", (IFunction)((arr) -> new AutoGenerated_BurnableAdapter_IBurnable((UObject)arr[0]) ) ); cmd.exec();
	}
}
