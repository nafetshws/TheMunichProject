package multiplayer;

import game.Player;
import util.Character;
import util.Direction;

public class InitializationPacket extends Packet {

	public static final int PACKET_ID = 7;

	// Player1
	private int p1x, p1y, p1speed;
	private Direction p1direction;
	private Character p1character;

	// Player 2
	private int p2x, p2y, p2speed;
	private Direction p2direction;
	private Character p2character;

	public InitializationPacket(int senderId, int p1x, int p1y, int p1speed, Direction p1direction,
			Character p1character, int p2x, int p2y, int p2speed, Direction p2direction, Character p2character) {
		super(senderId, PACKET_ID);
		this.p1x = p1x;
		this.p1y = p1y;
		this.p1speed = p1speed;
		this.p1direction = p1direction;
		this.p1character = p1character;
		this.p2x = p2x;
		this.p2y = p2y;
		this.p2speed = p2speed;
		this.p2direction = p2direction;
		this.p2character = p2character;
	}
	
	public int getP1x() {
		return p1x;
	}

	public int getP1y() {
		return p1y;
	}

	public int getP1speed() {
		return p1speed;
	}

	public Direction getP1direction() {
		return p1direction;
	}

	public Character getP1character() {
		return p1character;
	}

	public int getP2x() {
		return p2x;
	}

	public int getP2y() {
		return p2y;
	}

	public int getP2speed() {
		return p2speed;
	}

	public Direction getP2direction() {
		return p2direction;
	}

	public Character getP2character() {
		return p2character;
	}
	
}
