package balloons;

import java.awt.Color;
import java.awt.Graphics;

public class Circle {
	protected Vector origin;
	protected double radius;

	private Color color;

	public Circle(Vector origin, double radius, Color color) {
		this.origin = origin;
		this.radius = radius;
		this.color = color;
	}

	public void draw(Scene scene) {
		Graphics g = scene.getGraphics();
		drawCircle(g, color, radius);
	}

	public static boolean areIntersected(Circle c1, Circle c2) {
		return Math.pow(c1.radius + c2.radius, 2) > (Math.pow(c2.origin.getX() - c1.origin.getX(), 2)
				+ Math.pow(c2.origin.getY() - c1.origin.getY(), 2));
	}

	protected void drawCircle(Graphics g, Color color, double radius) {
		g.setColor(color);
		g.fillOval((int) (origin.getX() - radius), (int) (origin.getY() - radius), (int) (2 * radius),
				(int) (2 * radius));
	}
}
