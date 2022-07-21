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
	
	public CollisionChecker() { }
	
	public void checkCollisions(GamePanel gp, Player player) {
		this.player = player;
		if(gp.getState() == State.Running) {
			GameScreen gS = (GameScreen) gp.getScreens().get(gp.getState());
			this.map = gS.getTileManager().getMap();
		}
		
		int leftX = player.getX();
		int rightX = player.getX() + (player.getSize() * player.getCharacterWidthFactor());
		int upperY = player.getY();
		int lowerY = player.getY() + (player.getSize() * player.getCharacterHeightFactor());
		
		int playerLeftCol = (leftX - player.getSpeed()) / TileManager.tileSize;
		int playerRightCol = (rightX + player.getSpeed()) / TileManager.tileSize;
		int playerTopRow = upperY / TileManager.tileSize;
		int playerBottomRow = ((lowerY + player.getSpeed())/ TileManager.tileSize); 
		
		//Fall nach unten
		if(map[playerBottomRow][playerLeftCol].getCollision() || map[playerBottomRow][playerRightCol].getCollision()) {
			//Collision with bottom
			player.setY0(upperY);
			player.setIsFalling(false);
		}
		else {
			//update player y position
			if(!player.getIsJumping()) {
				player.fall();
			}

		}
		
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
