package flappybird.gameimage;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ImageManager {

	private Map<String, BufferedImage> images;
	private ImageSource zo;
	
	public ImageManager(ImageSource zo) {
		images = new HashMap<>();
		this.zo = zo;
		this.zo.fillMap();
	}
	
	private void prepareImage(GameImage o) {
		zo.setSource(o.getKey());
		images.put(o.getKey(), loadImage(o));
	}
	
	public void prepareImages() {
		prepareImage(GameImage.PLAYER);
		prepareImage(GameImage.BACKGROUND);
		prepareImage(GameImage.WALL);
		prepareImage(GameImage.BONUS);
	}

	private BufferedImage loadImage(GameImage o) {
		BufferedImage img;
		
		try {
			img = zo.getImage();
			if (img != null) {
				if (!imageHasFineDimensions(img, o.getWidth(), o.getHeight())) {
					img = editImage(img, o.getWidth(), o.getHeight());
				}
			}
			else {
				img = buildImage(o.getWidth(), o.getHeight(), o.getColor());
			}
		} catch(IOException e) {
			img = buildImage(o.getWidth(), o.getHeight(), o.getColor());
		}
		
		return img;
	}
	
	private BufferedImage editImage(BufferedImage img, int width, int height) {
		BufferedImage newImg = new BufferedImage(width, height, img.getType());
		Graphics2D g = newImg.createGraphics();
		g.drawImage(img, 0, 0, width, height, null);
		g.dispose(); // zlikvidovani graphics nad obrazkem = usetreni pameti
		return newImg;
	}

	private boolean imageHasFineDimensions(BufferedImage img, int width, int height) {
		return img.getWidth() == width && img.getHeight() == height;
	}

	private BufferedImage buildImage(int width, int height, Color color) {
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = img.createGraphics();
		g.setBackground(color);
		g.dispose();
		return img;
	}

	public BufferedImage getImage(GameImage o) {
		return images.get(o.getKey());
	}
	
}
