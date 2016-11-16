package flappybird;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.Timer;

import flappybird.gameimage.GameImage;
import flappybird.gameimage.ImageSourceFile;

public class GameScreen extends JPanel {

	public static final boolean DEBUG = true;
	
	public static final int HEIGHT = 800;
	public static final int WIDTH = 600;
	
	public static final int SPEED = -2;
	
	private Player player;
	private BufferedImage bgImg;
	private Timer animationTimer;
	private boolean pause = false;
	private boolean gameRunning = false;
	private int bgMoveX = 0;
	
	public GameScreen() {
		ImageSourceFile s = new ImageSourceFile();
		s.fillMap();
		s.setSource(GameImage.BACKGROUND.getKey());
		
		try {
			bgImg = s.getImage();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		s.setSource(GameImage.PLAYER.getKey());

		try {
			player = new Player(s.getImage());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		
		g.drawImage(bgImg, bgMoveX, 0, null);
		g.drawImage(bgImg, bgMoveX+bgImg.getWidth(), 0, null);
		
		if (GameScreen.DEBUG) {
			g.setColor(Color.WHITE);
			g.drawString("[posunPozadiX="+bgMoveX+"]", 10, 20);
		}
		
		player.paint(g);
		
	}
	
	private void move() {
		if (!pause && gameRunning) {
			//TODO
			
			bgMoveX += GameScreen.SPEED;
			if (bgMoveX == -bgImg.getWidth()) {
				bgMoveX = 0;
			}
			
			player.move();
		}
	}
	
	private void runGame() {
		animationTimer = new Timer(20, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				repaint();
				move();
			}
		});
		
		gameRunning = true;
		animationTimer.start();
	}
	
	private void prepareNewGame() {
		
	}
	
	public void init() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					//TODO player jump
					player.jump();
				}
				
				if (e.getButton() == MouseEvent.BUTTON3) {
					if (gameRunning) {
						if (pause) {
							pause = false;
						}
						else {
							pause = true;
						}
					}
					else {
						prepareNewGame();
						runGame();
					}
				}
			}
		});
		
		setSize(WIDTH, HEIGHT);
	}
	
}
