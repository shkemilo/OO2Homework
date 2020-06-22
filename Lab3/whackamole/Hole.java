package whackamole;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

public class Hole extends Canvas implements Runnable {
	private static final int ANIMATION_DELAY = 100;
	private static final int USER_WAIT_DELAY = 2000;

	private static final Color BG_COLOR = new Color(102, 51, 0);

	private Garden garden;
	private Animal animal = null;

	private int stepCount;
	private int currentStep;

	private Thread holeThread;
	private boolean running = false;

	private boolean isHit = false;

	public Hole(Garden garden) {
		this.garden = garden;

		setBackground(BG_COLOR);
	}

	public Garden getGarden() {
		return garden;
	}

	public Animal getAnimal() {
		return animal;
	}

	public void setAnimal(Animal animal) {
		this.animal = animal;
	}

	public int getStepCount() {
		return stepCount;
	}

	public void setStepCount(int stepCount) {
		this.stepCount = stepCount;
	}

	public boolean isRunning() {
		return running;
	}

	public synchronized void createThread() {
		holeThread = new Thread(this);
	}

	public synchronized void startThread() {
		holeThread.start();
		running = true;
		isHit = false;
	}

	public synchronized void stopThread() {
		if (holeThread == null)
			return;

		holeThread.interrupt();

		animal = null;
		repaint();
		garden.notifyFree(this);
		running = false;
	}

	public synchronized void hit() {
		if (animal == null)
			return;

		if (running)
			isHit = true;

		animal.hitEffect();
	}

	@Override
	public void paint(Graphics g) {
		if (animal == null)
			return;

		animal.drawAnimal();
	}

	@SuppressWarnings("static-access")
	@Override
	public void run() {
		for (currentStep = 1; currentStep <= stepCount; currentStep++) {
			animal.drawAnimal();
			repaint();
			try {
				holeThread.sleep(ANIMATION_DELAY);
			} catch (InterruptedException e) {
				return;
			}
		}

		try {
			holeThread.sleep(USER_WAIT_DELAY);
		} catch (InterruptedException e) {
			return;
		}
		removeAnimal();
		stopThread();
	}

	int getCurrentStep() {
		return currentStep;
	}

	private synchronized void removeAnimal() {
		if (animal == null)
			return;

		if (!isHit)
			animal.ranAwayEffect();

		animal = null;

		repaint();
	}
}
