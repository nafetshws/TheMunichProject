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
	
	private ServerSocket serverSocket;
	private Socket socket;
	
	public boolean activeConnection = true;
	
	public static void main(String[] args) throws Exception{
		new Server();
	}
	
	public Server() throws Exception{
		serverSocket = new ServerSocket(PORT);
		System.out.println("Server: Server läuft auf port " + PORT);
		socket = serverSocket.accept();
		
		outStream = new ObjectOutputStream(socket.getOutputStream());
		inStream = new ObjectInputStream(socket.getInputStream());
		
		while(activeConnection) {
			//TestPacket packet = (TestPacket) inStream.readObject();
			//packet.printData();
			Packet packet = (Packet) inStream.readObject();
			int id = packet.getPacketId();
			System.out.println("Packet id: " + id);
			
			if(id == 1) {
				TestPacket p = (TestPacket) packet;
				p.printData();
			}
			
			if(id == 0) {
				activeConnection = false;
			}
		}
		
		closeServerConnection();

	}
	
	public PlayerPositionPacket getOpponentPosition() throws Exception {
		PlayerPositionPacket opponentPosition = (PlayerPositionPacket) inStream.readObject();
		return opponentPosition;
	}
	
	public void sendPlayerPosition(PlayerPositionPacket packet) throws Exception {
		outStream.writeObject(packet);
	}
	
	public void closeServerConnection() {
		try {
			outStream.close();
			inStream.close();
			socket.close();
			serverSocket.close();
		} catch (IOException e) {
			System.out.println("Fehler beim Schließen des ServerSockets");
			e.printStackTrace();
		}

	}

}
