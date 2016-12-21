package flappybird;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageProducer;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import flappybird.gameimage.GameImage;
import flappybird.gameimage.ImageManager;
import flappybird.gameimage.ImageSourceFile;

public class GameScreen extends JPanel {

	public static final boolean DEBUG = true;
	
	public static final int HEIGHT = 800;
	public static final int WIDTH = 600;
	
	public static final int SPEED = -2;
	
	public static final int NUM_OF_WALLS = 4;
	
	private WallList walls;
	private Wall wall;
	private Wall prevWall;
	
	private int score = 0;
	private JLabel lbScore;
	private JLabel lbMsg;
	
	private Font font;
	private Font fontMsg;
	private Player player;
	private Bonus bonus;
	private BufferedImage bgImg;
	private Timer animationTimer;
	private boolean pause = false;
	private boolean gameRunning = false;
	private int bgMoveX = 0;
	
	public GameScreen(ImageManager im) {
		bgImg = im.getImage(GameImage.BACKGROUND);
		player = new Player(im.getImage(GameImage.PLAYER));
		Wall.setImg(im.getImage(GameImage.WALL));
		
		walls = new WallList();
		
		bonus = new Bonus(im.getImage(GameImage.BONUS),walls);
		
		buildLabels();
	}
	
	private void buildLabels() {
		font = new Font("Arial", Font.BOLD, 40);
		fontMsg = new Font("Arial", Font.BOLD, 20);
		this.setLayout(new BorderLayout());
		lbMsg = new JLabel("");
		lbMsg.setFont(fontMsg);
		lbMsg.setForeground(Color.WHITE);
		lbMsg.setHorizontalAlignment(SwingConstants.CENTER);
		
		lbScore = new JLabel("0");
		lbScore.setFont(font);
		lbScore.setForeground(Color.WHITE);
		lbScore.setHorizontalAlignment(SwingConstants.CENTER);
		
		this.add(lbScore, BorderLayout.NORTH);
		this.add(lbMsg, BorderLayout.CENTER);
	}
	
	private void buildWalls(int pocet) {
		Wall w;
		int distance = GameScreen.WIDTH;
		
		for (int i = 0; i < pocet; i++) {
			w = new Wall(distance);
			walls.add(w);
			distance += GameScreen.WIDTH/2;
		}
		
		distance -= GameScreen.WIDTH - Wall.WIDTH;
		Wall.setLastWallDistance(distance);
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		
		g.drawImage(bgImg, bgMoveX, 0, null);
		g.drawImage(bgImg, bgMoveX+bgImg.getWidth(), 0, null);
		
		if (GameScreen.DEBUG) {
			g.setColor(Color.WHITE);
			g.drawString("[posunPozadiX="+bgMoveX+"]", 10, 20);
		}
		
		for (Wall w : walls) {
			w.paint(g);
		}
		
		player.paint(g);
		bonus.paint(g);
		lbScore.paint(g);
		lbMsg.paint(g);
	}
	
	private void move() {
		if (!pause && gameRunning) {
			
			
			if (isWallCollision(prevWall, player)||isWallCollision(wall, player)||isGameScreenCollision(player)) {
				resetGameAfterCollision();
			}
			else {
				
				for (Wall w : walls) {
					w.move();
				}
				
				player.move();
				bonus.move();
			
				if (player.getX() >= wall.getX()) {
					setNextWallPrevious();
					incScoreWall();
					lbScore.setText(score + "");
				}
				
				if (isBonusCollision()) {
					bonus.setNewBonus();
					incScoreBonus();
					lbScore.setText(score + "");
				}
			
			}
			
			
			
			bgMoveX += GameScreen.SPEED;
			if (bgMoveX == -bgImg.getWidth()) {
				bgMoveX = 0;
			}
			
			
		}
	}
	
	private boolean isBonusCollision() {
		return player.getSkeleton().intersects(bonus.getSkeleton());
	}

	private void setNextWallPrevious() {
		wall = walls.getNext();
		prevWall = walls.getPrevious();
	}

	private void resetGameAfterCollision() {
		gameRunning = false;
		animationTimer.stop();
		animationTimer = null;
		resetGame();
		setWallCollisionMsg();
	}

	private boolean isWallCollision(Wall w, Player p) {
		return w.getSkeletonDownWall().intersects(p.getSkeleton())||w.getSkeletonUpWall().intersects(p.getSkeleton());
	}
	
	private boolean isGameScreenCollision(Player p) {
		return p.getY() <= 0 || p.getY() >= (GameScreen.HEIGHT - p.getHeight() + 40);
	}
	
	private void runGame() {
		animationTimer = new Timer(20, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				repaint();
				move();
			}
		});
		
		setEmptyMsg();
		gameRunning = true;
		animationTimer.start();
	}
	
	private void prepareNewGame() {
		resetGame();
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
							setEmptyMsg();
							pause = false;
						}
						else {
							setPauseMsg();
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
		setControlsMsg();
	}
	
	private void resetGame() {
		resetWalls();
		player.reset();
		lbScore.setText(score + ""); //GAME OVER - final score, then reset
		resetScore();
	}

	private void resetScore() {
		score = 0;
	}
	
	private void incScoreWall() {
		score += Wall.SCORE;
	}
	
	private void incScoreBonus() {
		score += Bonus.SCORE;
	}

	private void resetWalls() {
		walls.clear();
		buildWalls(NUM_OF_WALLS);
		setNextWallPrevious();
	}
	
	private void setWallCollisionMsg() {
		lbMsg.setFont(font);
		lbMsg.setText("Game Over!");
	}
	
	private void setControlsMsg() {
		lbMsg.setFont(fontMsg);
		lbMsg.setText("right mouse button = start/pause, left mouse button = jump");
	}
	
	private void setEmptyMsg() {
		lbMsg.setFont(font);
		lbMsg.setText("");
	}
	
	private void setPauseMsg() {
		lbMsg.setFont(font);
		lbMsg.setText("Pause");
	}
	
}
