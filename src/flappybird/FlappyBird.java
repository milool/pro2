package flappybird;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class FlappyBird extends JFrame {

	private GameScreen gs;
	
	public FlappyBird() {
		pack();
	}
	
	public void initGUI() {
		setSize(GameScreen.WIDTH, GameScreen.HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Flappy Bird(FIM)");
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public void start() {
		gs = new GameScreen();
		gs.init();
		getContentPane().add(gs, BorderLayout.CENTER);
		gs.setVisible(true);
		this.revalidate();
		this.repaint();
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				FlappyBird app = new FlappyBird();
				app.initGUI();
				app.start();
			}
		});
	}

}
