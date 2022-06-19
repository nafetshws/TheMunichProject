package multiplayer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	public static final int PORT = 3000;
	
	private ServerSocket serverSocket;
	
	private int maxNumberOfPlayers;
	private int numberOfPlayers;

	
	public Server(){
		numberOfPlayers = 0;
		maxNumberOfPlayers = 2;
		
		try {
			serverSocket = new ServerSocket(PORT);
		} catch (IOException e) {
			System.out.println("Fehler beim Erstellen des GameServerSockets");
			e.printStackTrace();
		}
	}
	
	public void waitForConnections() {
		System.out.println("Warten auf Spieler...");
		
		while(numberOfPlayers < maxNumberOfPlayers) {
			try {
				Socket socket = serverSocket.accept();
			
				//Spieler hat sich verbunden
				
				
				ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
				
				numberOfPlayers++;
				
				PlayerAuthenticationPacket auth = new PlayerAuthenticationPacket(numberOfPlayers);
				out.writeObject(auth);
				
				System.out.println("Spieler #" + numberOfPlayers + " hat sich erfolgreich mit dem Server verbunden");
			} catch (IOException e) {
				System.out.println("Fehler bei Verbindung des Servers mit dem Spieler");
				e.printStackTrace();
			}
		}
		
		System.out.println("Alle Spieler haben sich erfolgreich mit dem Server verbunden!");
	}
	
	public static void main(String[] args) {
		Server gameServer = new Server();
		gameServer.waitForConnections();
	}
	
	
	public void closeServerConnection() {
		try {
			serverSocket.close();
		} catch (IOException e) {
			System.out.println("Fehler beim Schließen des ServerSockets");
			e.printStackTrace();
		}

	}

}
