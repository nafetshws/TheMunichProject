package items;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Item {
	
	private String name;
	private BufferedImage image;
	
	private int size, worldx, worldy;
	
	public Item(String name, int worldx, int worldy, int size) {
		this.name = name;
		this.worldx = worldx;
		this.worldy = worldy;
		this.size = size;
	}
	
	public void loadImage() {
		try {
			image = ImageIO.read(getClass().getClassLoader().getResource("items/" + name + ".png"));
		} catch(Exception e) {
			System.out.println("Fehler beim laden des Bildes");
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g2) {
		if(image == null) {
			loadImage();
			System.out.println("Fehler: Bild wurde noch nicht geladen");
			return;
		}
		g2.drawImage(image, worldx, worldy, size, size, null);
	}
	
}
