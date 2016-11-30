package flappybird;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Player {
	
	public static final int WIDTH = 40;
	public static final int HEIGHT = 40;
	private static final int SPEED = 1; //koeficient zrychleni
	private static final int FALL_SPEED = 2; //rychlost padu
	
	private BufferedImage img = null;
	private int x; // x souradnice hrace - nemeni se
	private int y; // y souradnice hrace - meni se
	private int speed;
	
	public Player(BufferedImage img) {
		this.img = img;
		x = (GameScreen.WIDTH - img.getWidth()) / 2;
		y = GameScreen.HEIGHT / 2;
		
		speed = SPEED;
	}
	
	public void reset() {
		y = GameScreen.HEIGHT / 2;
		speed = SPEED;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void jump() {
		speed = -17;
	}
	
	/**
	 * call this method periodically to make player moving
	 */
	public void move() {
		speed += SPEED;
		y += speed;
	}
	
	public void paint(Graphics g) {
		g.drawImage(img, x, y, null);
		
		if (GameScreen.DEBUG) {
			g.setColor(Color.WHITE);
			g.drawString("[x="+x+", y="+y+", rychlost="+speed+"]", x, y-5);
		}
	}
	
	public int getHeight() {
		return img.getHeight();
	}
	
	public Rectangle getSkeleton() {
		return new Rectangle(x, y, img.getWidth(), img.getHeight());
	}
	
}
