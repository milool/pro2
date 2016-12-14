package flappybird.gameimage;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;

public class ImageSourceUrl extends ImageSource {

	private static final String FILE = "images.csv";
	private static final String DELIMITER = ";";
	
	
	@Override
	public void fillMap() {
		loadCSV();
	}

	private void loadCSV() {
		try (BufferedReader in = new BufferedReader(new FileReader(FILE))) {
			String line;		
			for (int i = 0; i < GameImage.getSize() && (line = in.readLine()) != null; i++) {
				processLine(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void processLine(String line) {
		StringTokenizer st = new StringTokenizer(line, DELIMITER);
		if (st.countTokens() == 2) {
			String key = st.nextToken();
			String url = st.nextToken();
			if (isKeyKnown(key)){
				getMap().put(key, url);
			}
			else {
				System.out.println("Unknown key: "+key);
			}
		}
	}

	private boolean isKeyKnown(String key) {
		for (GameImage img : GameImage.getImages()) {
			if (img.getKey().equals(key)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public BufferedImage getImage() throws IOException {
		URL url = new URL(getSource());
		URLConnection con = url.openConnection();
		con.setReadTimeout(3000);
		InputStream in = con.getInputStream();
		BufferedImage img = ImageIO.read(in);
		in.close();
		return img;
	}

}
