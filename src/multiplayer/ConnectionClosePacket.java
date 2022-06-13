package multiplayer;

public class ConnectionClosePacket extends Packet{
	
	public static final int PACKET_ID = 0;

	public ConnectionClosePacket(int id) {
		super(id, PACKET_ID);
	}
	
	

}
