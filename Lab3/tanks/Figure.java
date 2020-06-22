package tanks;

import java.awt.Graphics;

public abstract class Figure {
	private Field field;

	public Figure(Field field) {
		this.field = field;
	}

	public Field getField() {
		return field;
	}

	public void moveFigure(Field targetField) {
		field.repaint();
		this.field = targetField;
	}

	public void drawFigure() {
		draw(field.getGraphics(), field.getWidth(), field.getHeight());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Figure other = (Figure) obj;
		return field == other.field;
	}

	protected abstract void draw(Graphics g, int width, int height);

}
