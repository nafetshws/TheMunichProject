package multiplayer;

import java.io.Serializable;

public class Packet implements Serializable{
	
	private String message;
	
	public Packet(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}

}
