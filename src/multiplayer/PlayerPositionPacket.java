package multiplayer;

public class PlayerPositionPacket extends Packet{

	private int xPos;
	private int yPos;
	
	public PlayerPositionPacket(int id, int xPos, int yPos) {
		super(id);
		this.xPos = xPos;
		this.yPos = yPos;
	}
	
	@Override
	public void prettyPrintData() {
		System.out.println("Sender: Player " + super.getSenderId());
		System.out.println("X-Position: " + xPos);
		System.out.println("Y-Position: " + yPos);
		System.out.println();
	}
	
	public int getxPos() {
		return xPos;
	}

	public int getyPos() {
		return yPos;
	}

}
