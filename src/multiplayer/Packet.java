package multiplayer;

import java.io.Serializable;

public class Packet implements Serializable{
	
	private int senderId;
	private final int PACKET_ID;
	
	public Packet(int senderId, int PACKET_ID) {
		this.senderId = senderId;
		this.PACKET_ID = PACKET_ID;
	}
	
	public int getSenderId() {
		return senderId;
	}

	public void prettyPrintData() {
		System.out.println("Sender: Player " + senderId);
		System.out.println();
	}
	
	public int getPacketId() {
		return PACKET_ID;
	}

}
