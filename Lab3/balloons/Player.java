package balloons;

import java.awt.Color;
import java.awt.Graphics;

public class Player extends CircularFigure {
	private static final Color INNER_COLOR = Color.BLUE;

	public Player(Vector origin, double radius, Vector speedVector, Scene scene) {
		super(origin, radius, Color.GREEN, speedVector, scene);

	}

	@Override
	public void draw(Scene scene) {
		super.draw(scene);

		Graphics g = scene.getGraphics();
		drawCircle(g, INNER_COLOR, radius / 2);
	}

	@Override
	public void updatePosition(double offset) {
		origin.addVector(new Vector(offset, 0));
		correctOrigin();
	}

	@Override
	public void notifyIntersect(CircularFigure cFigure) {
		if (cFigure instanceof Balloon)
			scene.stopScene();

	}

	private void correctOrigin() {
		if (origin.getX() < 0)
			origin.addVector(new Vector(-origin.getX(), 0));
		if (origin.getX() > scene.getWidth())
			origin.addVector(new Vector(scene.getWidth() - origin.getX(), 0));
	}
}
