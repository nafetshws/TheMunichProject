package multiplayer;

import game.Player;
import util.Direction;

public class PlayerPositionPacket extends Packet{

	public static final int PACKET_ID = 2;
	
	private int x, y, speed;
	private Direction direction;
	
	public PlayerPositionPacket(int id, int x, int y, int speed, Direction direction) {
		super(id, PACKET_ID);
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.direction = direction;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getSpeed() {
		return speed;
	}

	public Direction getDirection() {
		return direction;
	}
	

}
