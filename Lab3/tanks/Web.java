package tanks;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import tanks.Game.State;

public class Web extends Panel implements Runnable {
	private static final int TICK_TIME = 40;
	private static final int DEFAULT_DIMENSION = 17;
	private static final String COIN = "coin";
	private static final String TANK = "tank";
	private static final String PLAYER = "player";

	private int dimension;
	private Field[][] fields;
	private boolean[][] notFree;

	private HashMap<String, List<Figure>> allFigures = new HashMap<String, List<Figure>>();
	private Player player = null;

	private Game game;
	private Thread webThread;
	private boolean running = false;

	private int numOfCoins;
	private int collectedCoins;

	public Web(Game game, int dimension) {
		super(new GridLayout(dimension, dimension));
		this.game = game;
		this.dimension = dimension;

		generateFields();
		addPlayerListener();
		initHashMap();
	}

	public Web(Game game) {
		this(game, DEFAULT_DIMENSION);
	}

	public Field[][] getFields() {
		return fields;
	}

	public List<Figure> getFigures(String figureType) {
		return allFigures.get(figureType);
	}

	public synchronized void initWeb(int numOfCoins) {
		if (game.getGameState() != State.PLAY)
			return;

		if (running)
			stopWeb();

		this.numOfCoins = numOfCoins;
		for (int i = 0; i < dimension; i++)
			for (int j = 0; j < dimension; j++)
				notFree[i][j] = false;

		generateFigureType(numOfCoins, COIN);
		generateFigureType(numOfCoins / 3, TANK);
		generateFigureType(1, PLAYER);

		player = (Player) allFigures.get(PLAYER).get(0);

		collectedCoins = 0;
		game.updateCoinCount(collectedCoins);
		webThread = new Thread(this);
		running = true;
		webThread.start();
	}

	public synchronized void stopWeb() {
		if (!running)
			return;

		for (Figure figure : allFigures.get(TANK))
			((Tank) figure).stopTank();

		for (List<Figure> figures : allFigures.values())
			figures.clear();

		repaintFields();

		webThread.interrupt();
		running = false;
	}

	@Override
	public void paint(Graphics g) {
		for (List<Figure> figures : allFigures.values())
			for (Figure figure : figures)
				figure.drawFigure();
	}

	@SuppressWarnings("static-access")
	@Override
	public void run() {
		start();
		try {
			while (!webThread.interrupted()) {
				webThread.sleep(TICK_TIME);
				update();
				repaint();
			}
		} catch (InterruptedException e) {
			return;
		}
	}

	private void initHashMap() {
		allFigures.put(COIN, new ArrayList<Figure>());
		allFigures.put(TANK, new ArrayList<Figure>());
		allFigures.put(PLAYER, new ArrayList<Figure>());
	}

	private void start() {
		for (Figure figure : allFigures.get(TANK))
			((Tank) figure).startTank();
	}

	private void update() {
		List<Figure> coins = allFigures.get(COIN);
		for (int i = 0; i < coins.size(); i++)
			if (player.getField() == coins.get(i).getField()) {
				coins.remove(i);
				addCoin();
				player.getField().repaint();
			}

		List<Figure> tanks = allFigures.get(TANK);
		for (int i = 0; i < tanks.size(); i++)
			if (player.getField() == tanks.get(i).getField()) {
				tanks.remove(player);
				tanks.get(i).getField().repaint();
				stopWeb();
				break;
			}
	}

	private void addCoin() {
		game.updateCoinCount(++collectedCoins);
		if (allFigures.get(COIN).size() == 0 || collectedCoins == numOfCoins)
			stopWeb();
	}

	private void generateFields() {
		fields = new Field[dimension][dimension];
		notFree = new boolean[dimension][dimension];

		generateFieldType((int) (0.8 * dimension * dimension), "Grass");

		fillFields("Wall");

		addFieldListeners();
		addFields();
	}

	private void addFields() {
		for (int i = 0; i < dimension; i++)
			for (int j = 0; j < dimension; j++)
				add(fields[i][j]);
	}

	private void repaintFields() {
		for (int i = 0; i < dimension; i++)
			for (int j = 0; j < dimension; j++)
				fields[i][j].repaint();
	}

	private void generateFigureType(int target, String figureType) {
		Random rnd = new Random(System.currentTimeMillis());
		int count = 0;
		while (count < target) {
			int[] randomPos = generateRandomPos(rnd);
			int i = randomPos[0];
			int j = randomPos[1];
			if (notFree[i][j])
				continue;

			Field field = fields[i][j];
			Figure figure = figureFactory(figureType, field);
			if (field.isFigureAllowed(figure)) {
				allFigures.get(figureType).add(figure);
				notFree[i][j] = true;
				count++;
			}
		}
	}

	private void generateFieldType(int target, String fieldType) {
		Random rnd = new Random(System.currentTimeMillis());
		int count = 0;
		while (count < target) {
			int[] randomPos = generateRandomPos(rnd);
			if (fields[randomPos[0]][randomPos[1]] == null) {
				fields[randomPos[0]][randomPos[1]] = fieldFactory(fieldType);
				count++;
			}
		}
	}

	private void fillFields(String fieldType) {
		for (int i = 0; i < dimension; i++)
			for (int j = 0; j < dimension; j++)
				if (fields[i][j] == null)
					fields[i][j] = fieldFactory(fieldType);
	}

	private void addFieldListeners() {
		for (int i = 0; i < dimension; i++)
			for (int j = 0; j < dimension; j++)
				fields[i][j].addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						requestFocus();
						if (game.getGameState() != State.EDIT)
							return;

						Field clickedField = (Field) e.getComponent();
						int i = clickedField.getPosition()[0];
						int j = clickedField.getPosition()[1];

						if (fields[i][j].getClass().getSimpleName().equalsIgnoreCase(game.getSelectedField()))
							return;

						remove(fields[i][j]);
						fields[i][j] = fieldFactory(game.getSelectedField());
						fields[i][j].addMouseListener(this);
						add(fields[i][j], i * dimension + j);
						revalidate();
					}
				});
	}

	private Field fieldFactory(String fieldType) {
		switch (fieldType.toLowerCase()) {
		case "grass":
			return new Grass(this);
		case "wall":
			return new Wall(this);
		}

		throw new RuntimeException();
	}

	private Figure figureFactory(String figureType, Field field) {
		switch (figureType.toLowerCase()) {
		case "player":
			return new Player(field);
		case "coin":
			return new Coin(field);
		case "tank":
			return new Tank(field);
		}

		throw new RuntimeException();
	}

	private int[] generateRandomPos(Random rnd) {
		int[] pos = new int[2];
		pos[0] = rnd.nextInt(dimension);
		pos[1] = rnd.nextInt(dimension);

		return pos;
	}

	private void addPlayerListener() {
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (player == null)
					return;

				switch (e.getKeyCode()) {
				case KeyEvent.VK_W:
					movePlayerByOffset(-1, 0);
					break;
				case KeyEvent.VK_S:
					movePlayerByOffset(1, 0);
					break;
				case KeyEvent.VK_A:
					movePlayerByOffset(0, -1);
					break;
				case KeyEvent.VK_D:
					movePlayerByOffset(0, 1);
					break;
				}
			}
		});
	}

	private void movePlayerByOffset(int offsetI, int offsetJ) {
		Field temp = player.getField().getFieldOffset(offsetI, offsetJ);
		if (temp == null || !temp.isFigureAllowed(player))
			return;

		player.moveFigure(temp);
	}
}
