package flappybird;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Wall {
	
	public static final int WIDTH = 45;
	//rychlost pohybu zdi
	public static final int SPEED = -6;
	//mezera mezi horni a dolni casti zdi
	public static final int GAP = 200;
	
	private static int lastWallDistance;
	
	private Random random;
	
	private static BufferedImage img = null;
	private int x;
	private int y;
	private int height;
	
	public Wall(int distanceFromStart) {
		this.x = distanceFromStart;
		
		random = new Random();
		generateRandomValuesForWall();
	}

	private void generateRandomValuesForWall() {
		y = random.nextInt(GameScreen.HEIGHT-400) + GAP;
		height = GameScreen.HEIGHT - y;
	}
	
	public void paint(Graphics g) {
		//dolni zed
		g.drawImage(img, x, y, null);
		
		//horni zed
		g.drawImage(img, x, y-GameScreen.HEIGHT-GAP, null);
		
	}
	
	public void move() {
		x += SPEED;
		
		if (x <= -Wall.WIDTH) {
			x = Wall.lastWallDistance;
			
			//TODO
		}
		
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public static int getLastWallDistance() {
		return lastWallDistance;
	}

	public static void setLastWallDistance(int lastWallDistance) {
		Wall.lastWallDistance = lastWallDistance;
	}

	public static void setImg(BufferedImage img) {
		Wall.img = img;
	}
	
}
