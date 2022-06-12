package multiplayer;

import java.io.Serializable;

public class Packet implements Serializable{
	
	private int id;
	
	public Packet(int id) {
		this.id = id;
	}
	
	public int getSenderId() {
		return id;
	}

	public void prettyPrintData() {
		System.out.println("Sender: Player " + id);
		System.out.println();
	}

}
