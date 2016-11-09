package flappybird.gameimage;

import java.awt.Color;

import flappybird.GameScreen;
import flappybird.Player;

public enum GameImage {

	PLAYER("player", Player.WIDTH, Player.HEIGHT, new Color(255, 255, 255)),
	BACKGROUND("background", GameScreen.WIDTH*3, GameScreen.HEIGHT, new Color(0, 0, 150));
	
	//num of items in enum
	private static final int size = GameImage.values().length;
	
	//array for iteration
	private static final GameImage[] images = GameImage.values();
	
	private final String key;
	private final int width;
	private final int height;
	private final Color color;
	
	private GameImage(String key, int width, int height, Color color) {
		this.key = key;
		this.width = width;
		this.height = height;
		this.color = color;
	}
	
	public String getKey() {
		return key;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public Color getColor() {
		return color;
	}
	
	public static int getSize() {
		return size;
	}
	
	public static GameImage[] getImages() {
		return images;
	}
	
}
