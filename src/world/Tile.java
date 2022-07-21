package world;

import java.awt.image.BufferedImage;

public class Tile {
	
	private BufferedImage image;
	private boolean collision = false;
	public String tileName;
	
	public Tile() {}
	
	public Tile(String tileName) {
		this.tileName = tileName;
	}
	
	public BufferedImage getImage() {
		return image;
	}
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	public boolean getCollision() {
		return collision;
	}
	public void setCollision(boolean collision) {
		this.collision = collision;
	}
	
	
}
