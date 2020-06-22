package tanks;

import java.awt.Color;
import java.awt.Graphics;

public class Player extends Figure {

	public Player(Field field) {
		super(field);
	}

	@Override
	protected void draw(Graphics g, int width, int height) {
		g.setColor(Color.RED);
		g.drawLine(width / 2, 0, width / 2, height);
		g.drawLine(0, height / 2, width, height / 2);
	}

}
