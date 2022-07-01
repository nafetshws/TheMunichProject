package multiplayer;

public class TeamAuthenticationPacket extends Packet{
	
	public static final int PACKET_ID = 10;
	
	private int player1Id;
	private int player2Id;

	public TeamAuthenticationPacket(int player1Id, int player2Id) {
		super(player1Id, PACKET_ID);
		this.player1Id = player1Id;
		this.player2Id = player2Id;
	}

	public int getPlayer1Id() {
		return player1Id;
	}

	public int getPlayer2Id() {
		return player2Id;
	}
	
}
