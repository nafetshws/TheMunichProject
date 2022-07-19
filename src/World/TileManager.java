package World;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import game.GamePanel;

public class TileManager {
	
	private GamePanel gp;
	private Tile[] tile;
	private int numberOfDifferentTiles = 5;
	private int tileSize = 100;
	private Tile[][] map;
	
	
	
	public TileManager(GamePanel gp) {
		this.gp = gp;
		tile = new Tile[numberOfDifferentTiles];
		map = new Tile[16][16];
		
		getTileImage();
		
		loadMap("map1.txt");
		
		//checkMap();
	}
	
	public void getTileImage() {
		setup(1, "grass", false);
		setup(0, "earth", false);
	}
	
	
	public void setup(int index, String tileName, boolean collision) {
		//hochladen der Bilder
		//Die Bilder werden in der Klasse Tile gespeichert
		try {
			
			tile[index] = new Tile();
			BufferedImage image = ImageIO.read(getClass().getClassLoader().getResource("tiles/" + tileName + ".png"));
			tile[index].setImage(image);
			tile[index].setCollision(collision);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public void loadMap(String fileName) {
		try {
			System.out.println("Before");
			InputStream in = getClass().getClassLoader().getResourceAsStream("map/" + fileName);
			BufferedReader bf = new BufferedReader(new InputStreamReader(in));
			
			String line = bf.readLine();
			int row = 0;
			
			while(line != null) {
				String rowAsString = line.replaceAll("\\s", "");	
				for(int col = 0; col < rowAsString.length(); col++) {
					int currentChar = Integer.parseInt(String.valueOf(rowAsString.charAt(col)));
					map[row][col] = tile[currentChar];
				}
				
				row++;
				line = bf.readLine();
			}
			
		} catch(Exception e) {
			System.out.println("Couldnt load file");
			e.printStackTrace();
		}
	}
	
	public void checkMap() {
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[i].length; j++) {
				BufferedImage img = map[i][j].getImage();
				if(img != null) {
					System.out.println(i + "x" + j + " wurde geladen");
					continue;
				}
				System.out.println("Fehler tile wurde nicht geladen");
			}
		}
	}
	
	public void draw(Graphics2D g2) {
		//g2.drawImage(tile[0].getImage(), 100, 1000, 100, 100,  null);
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[i].length; j++) {
				g2.drawImage(map[i][j].getImage(), j*tileSize, i*tileSize, tileSize, tileSize, null);
			}
		}
	}
	
}

