package multiplayer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	public static final int PORT = 3000;
	private ObjectOutputStream outStream;
	private ObjectInputStream inStream;
	
	
	
	public static void main(String[] args) throws Exception{
		new Server();
	}
	
	public Server() throws Exception{
		ServerSocket serverSocket = new ServerSocket(PORT);
		System.out.println("Server: Server läuft auf port " + PORT);
		Socket socket = serverSocket.accept();
		
		outStream = new ObjectOutputStream(socket.getOutputStream());
		inStream = new ObjectInputStream(socket.getInputStream());
		
		PlayerPositionPacket receivedPlayerPosition = getOpponentPosition();
		receivedPlayerPosition.prettyPrintData();
		sendPlayerPosition(0, 500, 700);
		
		
//		//read
//		Packet serverHello = (Packet) inStream.readObject();
//		String message = serverHello.getMessage();
//		System.out.println("Client: " + message);
//		
//		//write
//		Packet p = new Packet("Hallo vom Server!");
//		outStream.writeObject(p);
		
		serverSocket.close();
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
