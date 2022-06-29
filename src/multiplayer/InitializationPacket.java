package multiplayer;

import util.Character;
import util.Direction;

public class InitializationPacket extends Packet{
	
	public static final int PACKET_ID = 7;
	
	//position
	private int xPos;
	private int yPos;
	private int speed;
	private Direction direction;
	
	//Charackter
	Character character;

	public InitializationPacket(int playerId, int xPos, int yPos, int speed, Direction direction, Character character) {
		super(playerId, PACKET_ID);
		this.xPos = xPos;
		this.yPos = yPos;
		this.speed = speed;
		this.direction = direction;
		this.character = character;
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

	public Direction getDirection() {
		return direction;
	}

	public Character getCharacter() {
		return character;
	}
	
}
