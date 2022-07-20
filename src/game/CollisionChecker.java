package game;

import world.Tile;
import world.TileManager;

public class CollisionChecker {
	
	private Tile[][] map;
	
	public CollisionChecker() {
		map = TileManager.map;
		System.out.println("Länge: " + map.length + "x" + map[0].length);
	}
	
	public void checkCollisions(Player player) {
		int leftX = player.getX();
		int rightX = player.getX() + (player.getSize() * player.getCharacterWidthFactor());
		int upperY = player.getY();
		int lowerY = player.getY() + (player.getSize() * player.getCharacterHeightFactor());
		
		switch(player.getDirection()) {
			case Left:
				break;
			case Right:
				break;
			default:
				//Front
				break;
		}
		
		

	}

}
