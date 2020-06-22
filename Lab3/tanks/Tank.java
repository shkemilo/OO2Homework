package tanks;

import java.awt.Color;
import java.awt.Graphics;

public class Tank extends Figure implements Runnable {
	private static final int WAIT_INTERVAL = 500;

	private Thread tankThread;
	private boolean running = false;

	private enum Direction {
		UP, DOWN, LEFT, RIGHT
	};

	public Tank(Field field) {
		super(field);
	}

	public synchronized void startTank() {
		if (running)
			return;

		tankThread = new Thread(this);
		tankThread.start();
		running = true;
	}

	public synchronized void stopTank() {
		if (!running)
			return;

		tankThread.interrupt();
		running = false;
	}

	@SuppressWarnings("static-access")
	@Override
	public void run() {
		try {
			while (!tankThread.interrupted()) {
				tankThread.sleep(WAIT_INTERVAL);
				moveInRandomDirection();
			}
		} catch (InterruptedException e) {
			getField().repaint();
			return;
		}
	}

	@Override
	protected void draw(Graphics g, int width, int height) {
		g.setColor(Color.BLACK);
		g.drawLine(0, 0, width, height);
		g.drawLine(0, height, width, 0);
	}

	private void moveInRandomDirection() {
		Field temp = null;
		while (temp == null) {
			temp = moveInDirection(getRandomDirection());

			if (temp != null && !temp.isFigureAllowed(this)) {
				temp = null;
			}
		}

		moveFigure(temp);
	}

	private Direction getRandomDirection() {
		return Direction.values()[(int) (Math.random() * Direction.values().length)];
	}

	private Field moveInDirection(Direction dir) {
		switch (dir) {
		case UP:
			return getField().getFieldOffset(0, -1);
		case DOWN:
			return getField().getFieldOffset(0, 1);
		case LEFT:
			return getField().getFieldOffset(-1, 0);
		case RIGHT:
			return getField().getFieldOffset(1, 0);
		}

		return null;
	}

}
