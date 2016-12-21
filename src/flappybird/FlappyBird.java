package flappybird;

import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import flappybird.gameimage.ImageManager;
import flappybird.gameimage.ImageSourceFile;
import flappybird.gameimage.ImageSourceUrl;

public class FlappyBird extends JFrame {

	public static final String FILE = "config.txt";
	
	private ImageManager im;
	
	public FlappyBird() {
		pack();
	}
	
	private void setImageSource() {
		String line = "1";
		
		try (BufferedReader in = new BufferedReader(new FileReader(FILE))) {
			line = in.readLine();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		if (line.equals("1")) {
			im = new ImageManager(new ImageSourceUrl());
		}
		else {
			im = new ImageManager(new ImageSourceFile());
		}
	}
	
	public void initGUI() {
		setSize(GameScreen.WIDTH, GameScreen.HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Flappy Bird(FIM)");
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public void start() {
		setImageSource();
		
		class MyThread extends SwingWorker<Object, Object> {
			private JFrame owner;
			private JLabel lb;
			private GameScreen gs;
			
			public void setOwner(JFrame owner) {
				this.owner = owner;
			}
			
			public void doBeforeExecute() {
				lb = new JLabel("Loading...");
				lb.setFont(new Font("Arial", Font.BOLD, 30));
				lb.setForeground(Color.WHITE);
				lb.setHorizontalAlignment(SwingConstants.CENTER);
				
				owner.add(lb);
				lb.setVisible(true);
				owner.revalidate();
				owner.repaint();
			}
			
			@Override
			protected Object doInBackground() throws Exception {
				im.prepareImages();
				gs = new GameScreen(im);
				gs.init();
				return null;
			}
			
			@Override
			protected void done() {
				super.done();
				owner.remove(lb);
				owner.revalidate();
				owner.add(gs);
				gs.setVisible(true);
				owner.revalidate();
				owner.repaint();
			}

		}
		
		MyThread t = new MyThread();
		t.setOwner(this);
		t.doBeforeExecute();
		t.execute();
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
