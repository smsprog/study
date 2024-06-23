package imovable;

import java.util.Vector;

public interface IMovable {
	void move() throws Exception;
	void setPosition(Vector newPosition) throws Exception;
	void setVelocity(double dx, double dy) throws Exception;
}

