package flappybird;

import java.awt.image.BufferedImage;

public class Wall {
	
	// vyrobit obrazek pro zed
	
	public static final int WIDTH = 45;
	//rychlost pohybu zdi
	public static final int SPEED = -6;
	//mezera mezi horni a dolni casti zdi
	public static final int GAP = 200;
	
	//TODO
	
	private static BufferedImage img = null;
	private int x;
	private int y;
	private int height;
	
	public Wall(int distanceFromStart) {
		this.x = distanceFromStart;
		
		//TODO
	}
	
}
