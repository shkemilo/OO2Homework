package tanks;

import java.awt.Canvas;

public abstract class Field extends Canvas {
	private Web web;

	private int dimension;

	public Field(Web web) {
		this.web = web;
		dimension = web.getFields().length;
	}

	public int[] getPosition() {
		for (int i = 0; i < dimension; i++)
			for (int j = 0; j < dimension; j++)
				if (web.getFields()[i][j] == this) {
					int[] fieldPos = new int[2];
					fieldPos[0] = i;
					fieldPos[1] = j;
					return fieldPos;
				}

		return null;
	}

	public Field getFieldOffset(int offsetI, int offsetJ) {
		int[] pos = getPosition();
		if (pos[0] + offsetI < 0 || pos[1] + offsetJ < 0 || pos[0] + offsetI >= dimension
				|| pos[1] + offsetJ >= dimension)
			return null;

		return web.getFields()[pos[0] + offsetI][pos[1] + offsetJ];
	}

	public abstract boolean isFigureAllowed(Figure figure);
}
