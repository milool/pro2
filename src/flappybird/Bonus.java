package flappybird;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Bonus {

	public static final int WIDTH = 40;
	public static final int HEIGHT = 40;
	public static final int SCORE = 5;
	private Random random;
	private static BufferedImage img = null;
	private WallList walls;
	private int x;
	private int y;
	
	public void paint(Graphics g) {
		g.drawImage(img, x, y, null);
	}
	
	public void move() {
		x += Wall.SPEED;
	}
	
}
