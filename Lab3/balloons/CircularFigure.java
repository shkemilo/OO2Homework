package balloons;

import java.awt.Color;

public abstract class CircularFigure extends Circle {
	private Vector speedVector;
	protected Scene scene;

	public CircularFigure(Vector origin, double radius, Color color, Vector speedVector, Scene scene) {
		super(origin, radius, color);
		this.speedVector = speedVector;
		this.scene = scene;
	}

	public void updatePosition(double dt) {
		Vector originShift = speedVector.clone();
		originShift.scalarMultiplication(dt);
		origin.addVector(originShift);

		if (!inBounds())
			scene.removeFromScene(this);
	}

	private boolean inBounds() {
		return !(origin.getX() < 0 || origin.getY() < 0 || origin.getX() > scene.getWidth()
				|| origin.getY() > scene.getHeight());
	}

	public abstract void notifyIntersect(CircularFigure cFigure);
}
