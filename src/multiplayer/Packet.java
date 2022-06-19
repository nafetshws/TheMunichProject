package multiplayer;

import java.io.Serializable;

public class Packet implements Serializable{
	
	private int playerId;
	private final int PACKET_ID;
	
	public Packet(int playerId, int PACKET_ID) {
		this.playerId = playerId;
		this.PACKET_ID = PACKET_ID;
	}
	
	public int getPlayerId() {
		return playerId;
	}

	public void prettyPrintData() {
		System.out.println("Sender: Player " + playerId);
		System.out.println();
	}
	
	public int getPacketId() {
		return PACKET_ID;
	}

}
