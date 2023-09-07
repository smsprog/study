package tank_operations_imovable;

import java.util.Vector;

public interface Tank_Operations_IMovable
{
Vector getPosition() throws Exception;
Vector setPosition(Vector newValue) throws Exception;
Vector getVelocity() throws Exception;
}
