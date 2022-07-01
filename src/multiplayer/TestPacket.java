package multiplayer;

public class TestPacket extends Packet{
	
	public static final int PACKET_ID = 1;
	
	private String message;

	public TestPacket(int id, String message) {
		super(id, PACKET_ID);
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void printData() {
		System.out.println(super.getSenderId() + ": " + message);
	}

}
