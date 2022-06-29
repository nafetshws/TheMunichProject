package multiplayer;

import util.Direction;

public class PlayerPositionPacket extends Packet{

	public static final int PACKET_ID = 2;
	
	private int xPos;
	private int yPos;
	private int speed;
	private Direction direction;
	
	public PlayerPositionPacket(int id, int xPos, int yPos, int speed, Direction direction) {
		super(id, PACKET_ID);
		this.xPos = xPos;
		this.yPos = yPos;
		this.speed = speed;
		this.direction = direction;
	}
	
	@Override
	public void prettyPrintData() {
		System.out.println("Sender: Player " + super.getPlayerId());
		System.out.println("X-Position: " + xPos);
		System.out.println("Y-Position: " + yPos);
		System.out.println();
	}
	
	public int getXPos() {
		return xPos;
	}

	public int getYPos() {
		return yPos;
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public void setxPos(int xPos) {
		this.xPos = xPos;
	}

	public void setyPos(int yPos) {
		this.yPos = yPos;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	

}
