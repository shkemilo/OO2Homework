package balloons;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Game extends Frame {
	private Scene scene;

	public Game() {
		super("Balloons");
		setSize(600, 700);
		scene = new Scene(this);

		add(scene, BorderLayout.CENTER);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				scene.stopScene();
				dispose();
			}
		});

		setVisible(true);
		scene.startScene();
		scene.requestFocus();
	}

	public static void main(String[] args) {
		new Game();
	}
}
