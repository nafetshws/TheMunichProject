package world;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import game.CollisionChecker;
import game.GamePanel;

public class TileManager {
	
	private GamePanel gp;
	private Tile[] tile;
	private int numberOfDifferentTiles = 5;
	public static final int tileSize = 100;
	public static Tile[][] map;
	
	
	
	public TileManager(GamePanel gp) {
		this.gp = gp;
		tile = new Tile[numberOfDifferentTiles];
		map = new Tile[gp.getMaxWorldRows()][gp.getMaxWorldCols()];
		
		getTileImage();
		
		loadMap("map1.txt");
		
		//checkMap();
	}
	
	public void getTileImage() {
		setup(3, "sky", false);
		setup(2, "wall", true);
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
		
		int worldRow = 0;
		int worldCol = 0;
		
		while(worldCol < gp.getMaxWorldCols() && worldRow < gp.getMaxWorldRows()) {
			int worldX = worldCol * TileManager.tileSize;
			int worldY = worldRow * TileManager.tileSize;
			int screenX = worldX - gp.getMe().getTeamX() + gp.getScreenX();
			int screenY = worldY - gp.getMe().getTeamY() + gp.getScreenY();
			
			if(worldX + TileManager.tileSize > gp.getMe().getTeamX() - gp.getScreenX() &&
				worldX - TileManager.tileSize < gp.getMe().getTeamX() + gp.getScreenX() &&
				worldY + TileManager.tileSize > gp.getMe().getTeamY() - gp.getScreenY() &&
				worldY - TileManager.tileSize < gp.getMe().getTeamY() + gp.getScreenY()) {

				g2.drawImage(map[worldRow][worldCol].getImage(), screenX, screenY, tileSize, tileSize,null);
			}
			worldCol++;
					
			if(worldCol == gp.getMaxWorldCols()) {
				worldCol = 0;
				worldRow++;
			}
		}
		
	}
	
	public Tile[][] getMap(){
		return map;
	}
	
}

