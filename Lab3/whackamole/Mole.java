package whackamole;

import java.awt.Color;
import java.awt.Graphics;

public class Mole extends Animal {

	public Mole(Hole hole) {
		super(hole);
	}

	@Override
	public void ranAwayEffect() {
		hole.getGarden().eatVegetable();
	}

	@Override
	public void hitEffect() {
		hole.stopThread();
	}

	@Override
	protected void drawShape(Graphics g, int x, int y, int width, int height) {
		g.setColor(Color.DARK_GRAY);
		g.fillOval(x, y, width, height);
	}

}
