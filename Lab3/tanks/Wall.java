package tanks;

import java.awt.Color;

public class Wall extends Field {
	public static final Color BG_COLOR = Color.LIGHT_GRAY;

	public Wall(Web web) {
		super(web);
		setBackground(BG_COLOR);
	}

	@Override
	public boolean isFigureAllowed(Figure figure) {
		return false;
	}

}
