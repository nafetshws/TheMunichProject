package multiplayer;

import java.io.Serializable;

public class Packet implements Serializable{
	
	private int id;
	private final int PACKET_ID;
	
	public Packet(int id, int PACKET_ID) {
		this.id = id;
		this.PACKET_ID = PACKET_ID;
	}
	
	public int getSenderId() {
		return id;
	}

	public void prettyPrintData() {
		System.out.println("Sender: Player " + id);
		System.out.println();
	}
	
	public int getPacketId() {
		return PACKET_ID;
	}

}
