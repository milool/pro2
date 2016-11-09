package flappybird;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class FlappyBird extends JFrame {

	private GameScreen gs;
	
	public FlappyBird() {
		gs = new GameScreen();
		gs.init();
		getContentPane().add(gs, BorderLayout.CENTER);
		pack();
	}
	
	public void initGUI() {
		setSize(GameScreen.WIDTH, GameScreen.HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void start() {
		
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new FlappyBird().initGUI();
			}
		});
	}

}
