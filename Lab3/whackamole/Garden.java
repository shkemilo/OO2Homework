package whackamole;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class Garden extends Panel implements Runnable {
	private final Color BG_COLOR = Color.GREEN;

	private Hole[][] holes;
	private int rows;
	private int cols;

	private int vegCount = 100;

	private Thread gardenThread = null;
	private int waitInterval = 750;

	private int stepCount;

	private List<Hole> freeHoles = new ArrayList<Hole>();

	public Garden(int rows, int cols) {
		super(new GridLayout(rows, cols, 20, 20));
		setBackground(BG_COLOR);

		this.rows = rows;
		this.cols = cols;

		populate();
		setStepCount(8);
	}

	public int getStepCount() {
		return stepCount;
	}

	public void setStepCount(int stepCount) {
		this.stepCount = stepCount;

		for (int i = 0; i < rows; i++)
			for (int j = 0; j < cols; j++)
				holes[i][j].setStepCount(stepCount);
	}

	public void setWaitInterval(int waitInterval) {
		this.waitInterval = waitInterval;
	}

	public synchronized void eatVegetable() {
		--vegCount;
		Game.getInstance().updateVegCountLabel(vegCount);

		if (vegCount == 0)
			Game.getInstance().gameOver();
	}

	public synchronized void startGarden() {
		vegCount = 100;
		Game.getInstance().updateVegCountLabel(vegCount);
		gardenThread = new Thread(this);
		gardenThread.start();
	}

	public synchronized void stopGarden() {
		if (gardenThread == null)
			return;

		gardenThread.interrupt();
		restart();
	}

	public void notifyFree(Hole hole) {
		freeHoles.add(hole);
	}

	@SuppressWarnings("static-access")
	@Override
	public void run() {
		try {
			while (!gardenThread.interrupted()) {
				gardenThread.sleep(waitInterval);
				Hole targetHole = generateAnimal();
				if (targetHole == null)
					continue;

				targetHole.createThread();
				targetHole.startThread();
				waitInterval *= 0.99;
			}
		} catch (InterruptedException e) {

		}

	}

	private Hole generateAnimal() {
		if (freeHoles.size() == 0)
			return null;

		int ind = (int) (Math.random() * freeHoles.size());
		Hole randomHole = freeHoles.remove(ind);
		randomHole.setAnimal(new Mole(randomHole));
		return randomHole;
	}

	private void populate() {
		holes = new Hole[rows][cols];
		for (int i = 0; i < rows; i++)
			for (int j = 0; j < cols; j++) {
				holes[i][j] = new Hole(this);
				freeHoles.add(holes[i][j]);

				holes[i][j].addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						Hole clickedHole = (Hole) e.getComponent();
						clickedHole.hit();
					};
				});
				add(holes[i][j]);
			}
	}

	private void restart() {
		freeHoles.clear();

		for (int i = 0; i < rows; i++)
			for (int j = 0; j < cols; j++) {
				holes[i][j].stopThread();
				freeHoles.add(holes[i][j]);
			}
	}
}
