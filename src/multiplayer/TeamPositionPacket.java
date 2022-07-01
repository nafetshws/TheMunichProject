package multiplayer;

public class TeamPositionPacket extends Packet{
	
	public static final int PACKET_ID = 12;
	
	private PlayerPositionPacket player1PositionPacket;
	private PlayerPositionPacket player2PositionPacket;
	

	public TeamPositionPacket(int senderId, PlayerPositionPacket player1PositionPacket, PlayerPositionPacket player2PositionPacket) {
		super(senderId, PACKET_ID);
		this.player1PositionPacket = player1PositionPacket;
		this.player2PositionPacket = player2PositionPacket;
	}

	public PlayerPositionPacket getPlayer1PositionPacket() {
		return player1PositionPacket;
	}

	public PlayerPositionPacket getPlayer2PositionPacket() {
		return player2PositionPacket;
	}

}
