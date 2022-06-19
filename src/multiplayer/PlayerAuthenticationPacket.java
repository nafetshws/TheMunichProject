package multiplayer;

public class PlayerAuthenticationPacket extends Packet{
	
	public static final int PACKET_ID = 10;
	private int playerId;

	public PlayerAuthenticationPacket(int id) {
		super(id, PACKET_ID);
		this.playerId = playerId;
	}
	

}
