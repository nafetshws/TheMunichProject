package game;

import ui.GameScreen;
import ui.Screen;
import util.State;
import world.Tile;
import world.TileManager;

public class CollisionChecker {
	
	private Tile[][] map;
	private GamePanel gp;
	private Player player;
	
	public CollisionChecker(GamePanel gp, Player player) {
		this.gp = gp;
		this.player = player;
		if(gp.getState() == State.Running) {
			GameScreen gS = (GameScreen) gp.getScreens().get(gp.getState());
			this.map = gS.getTileManager().getMap();
		}
		else {
			System.out.println("Fehler beim Laden der Map zum Checken von Collisions");
		}
	}
	
	public CollisionChecker(GamePanel gp) { 
		this.gp = gp;
		if(gp.getState() == State.Running) {
			GameScreen gS = (GameScreen) gp.getScreens().get(gp.getState());
			this.map = gS.getTileManager().getMap();
		}
	}
	
	public void checkCollisions(GamePanel gp, Player player) {
		
		this.gp = gp;
		if(gp.getState() == State.Running) {
			GameScreen gS = (GameScreen) gp.getScreens().get(gp.getState());
			this.map = gS.getTileManager().getMap();
		}
		
		if(this.map == null) {
			System.out.println("Fehler: Map ist null");
			return;
		}
		
		this.player = player;

		int leftX = player.getX();
		int rightX = player.getX() + (player.getSize() * player.getCharacterWidthFactor());
		int upperY = player.getY();
		int lowerY = player.getY() + (player.getSize() * player.getCharacterHeightFactor());
		
		int playerLeftCol = (leftX) / TileManager.tileSize;
		int playerRightCol = (rightX) / TileManager.tileSize;
		int playerTopRow = upperY / TileManager.tileSize;

		int playerBottomRow = ((lowerY)/ TileManager.tileSize); 

		System.out.println("Y: " + player.getY());
		
		if(playerBottomRow >= 118) return;
		
		//Fall nach unten
		if(map[playerBottomRow][playerLeftCol].getCollision() || map[playerBottomRow][playerRightCol].getCollision()) {
			//Kollision mit Boden
			//player.setIsJumping(false);
			player.stopJumping();
			player.stopFalling();
			player.setY0(player.getY());
		}
		else {
			//update player y position
			if(!player.getIsJumping()) {
				//Spieler fällt
				player.fall();
				//System.out.println("Spieler fällt");
			}

		}
		
		if(player.getIsFalling() || player.getIsJumping()) {
			if(map[playerBottomRow][playerRightCol].getCollision() || map[playerTopRow][playerRightCol].getCollision()) {
				player.setCollisionRight(true);
			}
			else {
				player.setCollisionRight(false);
			}
			
			//Collision left
			if(map[playerBottomRow][playerLeftCol].getCollision() || map[playerTopRow][playerLeftCol].getCollision()) {
				player.setCollisionLeft(true);
			}
			else {
				player.setCollisionLeft(false);
			}
		}
		else {
			//Collision rechts
			if(map[playerBottomRow-1][playerRightCol].getCollision() || map[playerTopRow][playerRightCol].getCollision()) {
				player.setCollisionRight(true);
			}
			else {
				player.setCollisionRight(false);
			}
			
			//Collision left
			if(map[playerBottomRow-1][playerLeftCol].getCollision() || map[playerTopRow][playerLeftCol].getCollision()) {
				player.setCollisionLeft(true);
			}
			else {
				player.setCollisionLeft(false);
			}
		}

	}

}
