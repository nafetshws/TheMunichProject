package multiplayer;

import game.Player;
import util.Direction;

public class TeamPositionPacket extends Packet{

	public static final int PACKET_ID = 2;
	
	private Player player1;
	private Player player2;
	
	private int p1x, p1y, p1speed, p1direction;
	private int p2x, p2y, p2speed, p2direction;
	
	public TeamPositionPacket(int id, Player player1, Player player2) {
		super(id, PACKET_ID);
		this.player1 = player1;
		this.player2 = player2;
	}
	
	public Player getPlayer1() {
		return player1;
	}

	public Player getPlayer2() {
		return player2;
	}
	
	

}
