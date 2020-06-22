package whackamole;

import java.awt.Graphics;

public abstract class Animal {
	protected Hole hole;

	public Animal(Hole hole) {
		this.hole = hole;
	}

	public void drawAnimal() {
		double percent = (hole.getCurrentStep() * 1.0) / hole.getStepCount();
		int adjWidth = (int) (hole.getWidth() * percent);
		int adjHeight = (int) (hole.getHeight() * percent);

		drawShape(hole.getGraphics(), (hole.getWidth() - adjWidth) / 2, (hole.getHeight() - adjHeight) / 2, adjWidth,
				adjHeight);
	}

	public abstract void ranAwayEffect();

	public abstract void hitEffect();

	protected abstract void drawShape(Graphics g, int x, int y, int width, int height);
}
