package whackamole;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Game extends Frame {
	private static Game instance = null;
	private Garden garden = new Garden(4, 4);

	private Label vegCountLabel = new Label("Vegetables: 0");
	private Button controlButton;
	private Panel difficultyPanel = new Panel(new GridLayout(0, 1));

	private Checkbox easy;
	private Checkbox medium;
	private Checkbox hard;

	private boolean running = false;

	private Game() {
		super("Whack-a-Mole");
		setSize(800, 600);
		add(garden, BorderLayout.CENTER);
		add(generatePanel(), BorderLayout.EAST);

		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				garden.stopGarden();
				dispose();
			}
		});

		setVisible(true);
	}

	public static Game getInstance() {
		if (instance == null)
			instance = new Game();

		return instance;
	}

	public void updateVegCountLabel(int vegCount) {
		vegCountLabel.setText("Vegetables: " + vegCount);
	}

	private Panel generatePanel() {
		addDifficultyControl();

		Panel panel = new Panel(new GridLayout(0, 1));
		panel.add(difficultyPanel);
		addControlButton(panel);
		panel.add(vegCountLabel);
		return panel;
	}

	private void addControlButton(Panel panel) {
		controlButton = new Button();
		controlButton.setLabel("Start");
		controlButton.addActionListener(e -> {
			if (running) {
				gameOver();
			} else {
				startGame();
			}
		});

		panel.add(controlButton);
	}

	public void gameOver() {
		garden.stopGarden();
		running = false;
		controlButton.setLabel("Start");
		enableDifficultyControl(true);
	}

	private void startGame() {
		lockInDifficulty();
		running = true;
		controlButton.setLabel("Stop");
		enableDifficultyControl(false);
		garden.startGarden();
	}

	private void addDifficultyControl() {
		CheckboxGroup difficultyControl = new CheckboxGroup();
		easy = new Checkbox("Easy", false, difficultyControl);

		medium = new Checkbox("Medium", true, difficultyControl);

		hard = new Checkbox("Hard", false, difficultyControl);

		difficultyPanel.add(new Label("Difficulty: "));
		difficultyPanel.add(easy);
		difficultyPanel.add(medium);
		difficultyPanel.add(hard);
	}

	private void enableDifficultyControl(boolean enable) {
		easy.setEnabled(enable);
		medium.setEnabled(enable);
		hard.setEnabled(enable);
	}

	private void lockInDifficulty() {
		if (easy.getState()) {
			garden.setWaitInterval(1000);
			garden.setStepCount(10);
			return;
		}
		if (medium.getState()) {
			garden.setWaitInterval(750);
			garden.setStepCount(8);
			return;
		}
		if (hard.getState()) {
			garden.setWaitInterval(500);
			garden.setStepCount(6);
			return;
		}
	}

	public static void main(String[] args) {
		Game.getInstance();
	}
}
