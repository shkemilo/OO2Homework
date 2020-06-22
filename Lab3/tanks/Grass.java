package tanks;

import java.awt.Color;

public class Grass extends Field {
	public static final Color BG_COLOR = Color.GREEN;

	public Grass(Web web) {
		super(web);
		setBackground(BG_COLOR);
	}

	@Override
	public boolean isFigureAllowed(Figure figure) {
		return true;
	}

}
