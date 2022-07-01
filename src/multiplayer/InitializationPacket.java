package multiplayer;

import game.Player;
import util.Character;
import util.Direction;

public class InitializationPacket extends Packet{
	
	public static final int PACKET_ID = 7;
	
	//position
	Player player1;
	Player player2;

	public InitializationPacket(int playerId, Player player1, Player player2) {
		super(playerId, PACKET_ID);
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
