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
		
		int entityLeftCol = leftX / TileManager.tileSize;
		int entityRightCol = rightX / TileManager.tileSize;
		int entityTopRow = upperY / TileManager.tileSize;
		int playerBottomRow = (lowerY / TileManager.tileSize); 
		
		if(map[playerBottomRow][entityLeftCol].getCollision() || map[playerBottomRow][entityRightCol].getCollision()) {
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
		
		
		

	}

}
