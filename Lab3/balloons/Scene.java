package balloons;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Scene extends Canvas implements Runnable {
	private static final int BOTTOM_OFFSET = 40;
	private static final int TOP_OFFSET = 30;
	private static final double PLAYER_MOVE_UNIT = 5;

	private double playerRadius = 15;

	private double balloonRadius = 10;
	private Vector balloonSpeed = new Vector(0, 50);
	private double balloonSpeedIntensity = 50;
	private double balloonProbability = 0.1;

	private int tickTime = 60;

	private boolean running = false;

	private Game game;

	private Thread sceneThread;

	private Player player;
	private List<CircularFigure> figures = new ArrayList<CircularFigure>();

	public Scene(Game game) {
		this.game = game;

		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_RIGHT:
					player.updatePosition(PLAYER_MOVE_UNIT);
					break;
				case KeyEvent.VK_LEFT:
					player.updatePosition(-PLAYER_MOVE_UNIT);
					break;
				}
			}
		});
	}

	public synchronized void startScene() {
		if (running)
			return;

		sceneThread = new Thread(this);
		initScene();
		running = true;
		sceneThread.start();
	}

	public synchronized void stopScene() {
		if (!running)
			return;

		running = false;
		sceneThread.interrupt();
	}

	public void addToScene(CircularFigure cFigure) {
		figures.add(cFigure);
	}

	public void removeFromScene(CircularFigure cFigure) {
		figures.remove(cFigure);
	}

	@Override
	public void paint(Graphics g) {
		player.draw(this);
		for (CircularFigure cFigure : figures)
			cFigure.draw(this);
	}

	@SuppressWarnings("static-access")
	@Override
	public void run() {
		try {
			while (!sceneThread.interrupted()) {
				sceneThread.sleep(tickTime);
				update();
				repaint();
			}
		} catch (InterruptedException e) {

		}
	}

	private Balloon generateBalloon() {
		Random rnd = new Random(System.currentTimeMillis());
		Color randomColor = new Color((float) generateDoubleInBounds(rnd, 0.5, 1),
				(float) generateDoubleInBounds(rnd, 0.5, 1), (float) generateDoubleInBounds(rnd, 0.5, 1));
		double xPos = generateDoubleInBounds(rnd, 0, getWidth());
		double xComp = generateDoubleInBounds(rnd, -0.5, 0.5);
		double yComp = Math.sqrt(1 - Math.pow(xComp, 2));
		Vector speedVector = new Vector(xComp, yComp);
		speedVector.scalarMultiplication(balloonSpeedIntensity);
		return new Balloon(new Vector(xPos, TOP_OFFSET), balloonRadius, randomColor, speedVector, this);
	}

	private void initScene() {
		player = new Player(new Vector(getWidth() / 2, getHeight() - BOTTOM_OFFSET), playerRadius, null, this);
		figures = new ArrayList<CircularFigure>();
		repaint();
	}

	private void update() {
		if (Math.random() <= balloonProbability) {
			addToScene(generateBalloon());
			balloonSpeed.scalarMultiplication(1.005);
			balloonSpeedIntensity *= 1.01;
		}

		for (int i = 0; i < figures.size(); i++)
			figures.get(i).updatePosition(tickTime / 1000.0);

		notifyFigures();

		notifyPlayer();
	}

	private void notifyFigures() {
		for (int i = 0; i < figures.size() - 1; i++)
			for (int j = i + 1; j < figures.size(); j++) {
				CircularFigure figure1 = figures.get(i);
				CircularFigure figure2 = figures.get(j);

				if (Circle.areIntersected(figure1, figure2)) {
					figure1.notifyIntersect(figure2);
					figure2.notifyIntersect(figure1);
				}
			}
	}

	private void notifyPlayer() {
		for (int i = 0; i < figures.size(); i++)
			if (Circle.areIntersected(player, figures.get(i))) {
				player.notifyIntersect(figures.get(i));
				figures.get(i).notifyIntersect(player);
			}
	}

	private double generateDoubleInBounds(Random rnd, double low, double high) {
		return (rnd.nextDouble() + low) * (high - low);
	}
}
