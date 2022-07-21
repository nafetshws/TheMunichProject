package world;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

import javax.imageio.ImageIO;

import game.CollisionChecker;
import game.GamePanel;
import util.Collisions;

public class TileManager {
	
	private GamePanel gp;
	private Tile[] tile;
	private int numberOfDifferentTiles = 5;
	public static final int tileSize = 50;
	public Tile[][] map;
	
	
	
	public TileManager(GamePanel gp) {
		this.gp = gp;
		tile = new Tile[numberOfDifferentTiles];
		map = new Tile[gp.getMaxWorldRows()][gp.getMaxWorldCols()];
		
		getTileImage();
		
		//loadCustomMap("map2.txt");
		loadMap();
		
		//checkMap();
	}
	
	public void loadMap() {

		try {
			
			for(int col = 0; col < gp.getMaxWorldCols(); col++) {
				Tile tile = new Tile();
				tile.setCollision(true);
				
				String path = "tiles/row-1-column-1.png";
				BufferedImage img = ImageIO.read(getClass().getClassLoader().getResource(path));
				
				tile.setImage(img);
				
				this.map[0][col] = tile;
				this.map[gp.getMaxWorldRows()-1][col] = tile;
			}
			
			for(int row = 0; row < gp.getMaxWorldRows(); row++) {
				Tile tile = new Tile();
				tile.setCollision(true);
				
				String path = "tiles/row-1-column-1.png";
				BufferedImage img = ImageIO.read(getClass().getClassLoader().getResource(path));
				
				tile.setImage(img);
				
				this.map[row][0] = tile;
				this.map[row][gp.getMaxWorldCols()-1] = tile;
			}
			
			for(int row = 1; row <= map.length - 2; row++) {
				for(int col = 1; col <= (map[row].length)-2; col++) {
					String path = "tiles/row-" + row + "-column-" + col + ".png";
					BufferedImage img = ImageIO.read(getClass().getClassLoader().getResource(path));
					
					Tile tile = new Tile();
					tile.setImage(img);
					
					String rowXCol = Integer.toString(row) + "," + Integer.toString(col);
					if(Arrays.asList(Collisions.collisions).contains(rowXCol)) {
						System.out.println("Found collision: " + rowXCol);
						tile.setCollision(true);
					} else {
						tile.setCollision(false);
					}
					
					this.map[row][col] = tile;
				}
			}
		} catch (Exception e) {
			System.out.println("Fehler beim Laden der Map");
			e.printStackTrace();
		}
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
			
			tile[index] = new Tile(tileName);
			BufferedImage image = ImageIO.read(getClass().getClassLoader().getResource("tilesBackup/" + tileName + ".png"));
			tile[index].setImage(image);
			tile[index].setCollision(collision);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public void loadCustomMap(String fileName) {
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

