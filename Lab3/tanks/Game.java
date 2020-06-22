package tanks;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Game extends Frame {
	private static final int NUM_OF_TILES = 2;

	public enum State {
		EDIT, PLAY
	};

	private Web web;
	private State gameState = State.EDIT;
	private Button startButton;
	private TextField coinField;
	private Label pointLabel;

	private Checkbox[] tileCheckboxes = new Checkbox[NUM_OF_TILES];

	public Game() {
		super("Little Tanks");
		setSize(800, 600);

		web = new Web(this);
		add(web, BorderLayout.CENTER);
		add(generateTilePanel(), BorderLayout.EAST);
		add(generateControlPanel(), BorderLayout.SOUTH);
		addMenu();

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				web.stopWeb();
				dispose();
			}
		});

		setEditInterface();
		setVisible(true);
	}

	public State getGameState() {
		return gameState;
	}

	public String getSelectedField() {
		for (int i = 0; i < tileCheckboxes.length; i++) {
			if (tileCheckboxes[i].getState())
				return tileCheckboxes[i].getLabel();
		}

		throw new RuntimeException();
	}

	public void updateCoinCount(int count) {
		pointLabel.setText("Points: " + count);
	}

	private Panel generateControlPanel() {
		Panel holderPanel = new Panel(new GridLayout(1, 6));

		holderPanel.add(new Label());

		holderPanel.add(new Label("Coins: ", Label.CENTER));

		coinField = new TextField("10");
		holderPanel.add(coinField);

		pointLabel = new Label("Points: 0", Label.CENTER);
		holderPanel.add(pointLabel);

		startButton = new Button("Start");
		startButton.addActionListener(e -> {
			web.initWeb(Integer.parseInt(coinField.getText()));
			web.requestFocus();
		});
		holderPanel.add(startButton);

		holderPanel.add(new Label());

		return holderPanel;
	}

	private Panel generateTilePanel() {
		Panel holder = new Panel(new GridLayout(1, 2));

		holder.add(new Label("Tile: ", Label.CENTER));

		Panel tileHolder = new Panel(new GridLayout(0, 1));
		CheckboxGroup tileRadio = new CheckboxGroup();

		tileHolder.add(generateTileBorder(0, Grass.BG_COLOR, "Grass", tileRadio));
		tileHolder.add(generateTileBorder(1, Wall.BG_COLOR, "Wall", tileRadio));

		holder.add(tileHolder);

		return holder;
	}

	private Panel generateTileBorder(int i, Color color, String tileName, CheckboxGroup tileRadio) {
		Panel border = new Panel(new BorderLayout());
		border.setBackground(color);
		tileCheckboxes[i] = new Checkbox(tileName, i == 0 ? true : false, tileRadio);
		border.add(tileCheckboxes[i], BorderLayout.CENTER);

		return border;
	}

	private void addMenu() {
		MenuBar mb = new MenuBar();

		Menu menu = new Menu("Mode");

		MenuItem editModeItem = new MenuItem("Edit mode");
		editModeItem.addActionListener(e -> {
			if (gameState == State.EDIT)
				return;
			if (gameState == State.PLAY) {
				web.stopWeb();
			}

			setEditInterface();
			gameState = State.EDIT;
		});
		menu.add(editModeItem);

		MenuItem playModeItem = new MenuItem("Play mode");
		playModeItem.addActionListener(e -> {
			if (gameState == State.PLAY)
				return;

			setPlayInterface();
			gameState = State.PLAY;
		});
		menu.add(playModeItem);

		mb.add(menu);
		setMenuBar(mb);
	}

	private void setPlayInterface() {
		setTitle("Little Tanks - Play");
		startButton.setEnabled(true);
		coinField.setEnabled(true);
		for (int i = 0; i < tileCheckboxes.length; i++)
			tileCheckboxes[i].setEnabled(false);
	}

	private void setEditInterface() {
		setTitle("Little Tanks - Edit");
		startButton.setEnabled(false);
		coinField.setEnabled(false);
		for (int i = 0; i < tileCheckboxes.length; i++)
			tileCheckboxes[i].setEnabled(true);
	}

	public static void main(String[] args) {
		new Game();
	}

}
