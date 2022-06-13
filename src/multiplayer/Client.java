package multiplayer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
	
	private ObjectOutputStream outStream;
	private ObjectInputStream inStream;
	
	private Socket socket;
	
	public boolean activeConnection = true;
	
	public static void main(String[] args) throws Exception{
		new Client();
	}
	
	public Client() throws Exception{
		socket = new Socket("localhost", Server.PORT);
		
		outStream = new ObjectOutputStream(socket.getOutputStream());
		inStream = new ObjectInputStream(socket.getInputStream());
		
		TestPacket testPacket = new TestPacket(1, "Test Nachricht 1 von Client");
		outStream.writeObject(testPacket);
		
		Thread.sleep(1000*5);
		
		TestPacket testPacket2 = new TestPacket(1, "Test Nachricht 2 von Client");
		outStream.writeObject(testPacket2);
		
		ConnectionClosePacket closePacket = new ConnectionClosePacket(1);
		outStream.writeObject(closePacket);
		
		
//		sendPlayerPosition(1, 500, 700);
//		PlayerPositionPacket receivedPlayerPosition = getOpponentPosition();
//		
//		receivedPlayerPosition.prettyPrintData();
		
		closeClientConnection();
		
	}
	
	public PlayerPositionPacket getOpponentPosition() throws Exception {
		PlayerPositionPacket opponentPosition = (PlayerPositionPacket) inStream.readObject();
		return opponentPosition;
	}
	
	public void sendPlayerPosition(PlayerPositionPacket packet) throws Exception {
		outStream.writeObject(packet);
	}
	
	public void closeClientConnection() {
		try {
			outStream.close();
			inStream.close();
			socket.close();
		} catch (IOException e) {
			System.out.println("Fehler beim Schließen des ClientSockets");
			e.printStackTrace();
		}

	}

}
