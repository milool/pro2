package flappybird.gameimage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageSourceFile extends ImageSource {

	private static final String PATH = "img/";
	
	@Override
	public void fillMap() {
		getMap().put(GameImage.BACKGROUND.getKey(), "bg.png");
		getMap().put(GameImage.PLAYER.getKey(), "super-tux.png");
	}

	@Override
	public BufferedImage getImage() throws IOException {
		return ImageIO.read(new File(PATH + getSource()));
	}

}
