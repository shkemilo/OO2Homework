package tanks;

import java.awt.Color;
import java.awt.Graphics;

public class Coin extends Figure {

	public Coin(Field field) {
		super(field);
	}

	@Override
	protected void draw(Graphics g, int width, int height) {
		g.setColor(Color.YELLOW);
		g.fillOval(width / 4, height / 4, width / 2, height / 2);
	}

}
