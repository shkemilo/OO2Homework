package balloons;

import java.awt.Color;

public class Balloon extends CircularFigure {

	public Balloon(Vector origin, double radius, Color color, Vector speedVector, Scene scene) {
		super(origin, radius, color, speedVector, scene);

	}

	@Override
	public void notifyIntersect(CircularFigure cFigure) {
		return;

	}

}
