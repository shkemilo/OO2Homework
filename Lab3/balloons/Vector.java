package balloons;

public class Vector implements Cloneable {
	private double x;
	private double y;

	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void scalarMultiplication(double scalar) {
		x *= scalar;
		y *= scalar;
	}

	public void addVector(Vector v) {
		x += v.getX();
		y += v.getY();
	}

	@Override
	public Vector clone() {
		return new Vector(x, y);
	}

}
