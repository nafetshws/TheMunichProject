package multiplayer;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
	
	private ObjectOutputStream outStream;
	private ObjectInputStream inStream;
	
	public static void main(String[] args) throws Exception{
		new Client();
	}
	
	public Client() throws Exception{
		Socket socket = new Socket("localhost", Server.PORT);
		
		outStream = new ObjectOutputStream(socket.getOutputStream());
		inStream = new ObjectInputStream(socket.getInputStream());
		
		sendPlayerPosition(1, 500, 700);
		PlayerPositionPacket receivedPlayerPosition = getOpponentPosition();
		
		receivedPlayerPosition.prettyPrintData();
		
		socket.close();
		
	}
	
	public PlayerPositionPacket getOpponentPosition() throws Exception {
		PlayerPositionPacket opponentPosition = (PlayerPositionPacket) inStream.readObject();
		return opponentPosition;
	}
	
	public void sendPlayerPosition(int playerId, int xPos, int yPos) throws Exception {
		PlayerPositionPacket p = new PlayerPositionPacket(playerId, xPos, yPos);
		outStream.writeObject(p);
	}

}
