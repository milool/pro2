package flappybird;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Bonus {

	public static final int WIDTH = 40;
	public static final int HEIGHT = 40;
	public static final int SCORE = 5;
	private Random random;
	private BufferedImage img = null;
	private WallList walls;
	private int x;
	private int y;
	
	public Bonus(BufferedImage img, WallList walls) {
		this.img = img;
		random = new Random();
		this.walls = walls;
		setNewBonus();
	}
	
	public void paint(Graphics g) {
		g.drawImage(img, x, y, null);
	}
	
	public void move() {
		x += Wall.SPEED;
		if (x <= -WIDTH) {
			setNewBonus();
		}
	}

	public void setNewBonus() {
		do {
			generateRandomCoordinates();
		} while (checkWalls());
	}
	
	private boolean checkWalls() {
		for (Wall w : walls) {
			if (isBonusInWall(w)) {
				return true;
			}
		}
		return false;
	}
	
	private void generateRandomCoordinates() {
		x = (int)(1.5 * GameScreen.WIDTH);
		y = random.nextInt(GameScreen.HEIGHT - 100);
	}
	
	public Rectangle getSkeleton() {
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}
	
	public boolean isBonusInWall(Wall w) {
		return getSkeleton().intersects(w.getSkeletonUpWall())||getSkeleton().intersects(w.getSkeletonDownWall());
	}
	
	public void setWallList(WallList walls) {
		this.walls = walls;
	}
	
}
