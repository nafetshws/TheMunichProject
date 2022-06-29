package multiplayer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import util.Direction;

public class Server {
	
	public static final int PORT = 3000;
	public static final int PAUSE_DATAFLOW_TIME = 6;
	
	private ServerSocket serverSocket;
	
	private Socket p1Socket;
	private Socket p2Socket;
	
	private ReadFromPlayer readFromP1;
	private ReadFromPlayer readFromP2;
	private WriteToPlayer writeToP1;
	private WriteToPlayer writeToP2;
	
	private int p1x, p1y, p2x, p2y;
	private int p1speed, p2speed;
	private Direction p1Direction, p2Direction;

	
	private int maxNumberOfPlayers;
	private int numberOfPlayers;

	
	public Server(){
		numberOfPlayers = 0;
		maxNumberOfPlayers = 2;
		
		p1x = 100;
		p1y = 300;
		p1speed = 8;
		
		p2x = 600;
		p2y = 300;
		p2speed = 8;
		
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
				
				ReadFromPlayer rfp = new ReadFromPlayer(numberOfPlayers, in);
				WriteToPlayer wtp = new WriteToPlayer(numberOfPlayers, out);
				
				if(numberOfPlayers == 1) {
					p1Socket = socket;
					readFromP1 = rfp;
					writeToP1 = wtp;
					Thread readThread1 = new Thread(readFromP1);
					Thread writeThread1 = new Thread(writeToP1);
					readThread1.start();
					writeThread1.start();
				}
				else {
					p2Socket = socket;
					readFromP2 = rfp;
					writeToP2 = wtp;
					Thread readThread2 = new Thread(readFromP2);
					Thread writeThread2 = new Thread(writeToP2);
					readThread2.start();
					writeThread2.start();					
				}

				
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
	
	public class ReadFromPlayer implements Runnable{
		
		private int playerId;
		private ObjectInputStream in;
		
		public ReadFromPlayer(int playerId, ObjectInputStream in) {
			this.playerId = playerId;
			this.in = in;
			System.out.println("Server input stream erstellt für Spieler #" + playerId);
		}

		@Override
		public void run() {
			try {
				while(true) {
					Packet p = (Packet) in.readObject();
					switch(p.getPacketId()) {
						case PlayerPositionPacket.PACKET_ID:
							PlayerPositionPacket position = (PlayerPositionPacket) p;
							if(position.getPlayerId() == 1) {
								p1x = position.getXPos();
								p1y = position.getYPos();
								p1speed = position.getSpeed();
								p1Direction = position.getDirection(); 
							}
							else {
								p2x = position.getXPos();
								p2y = position.getYPos();
								p2speed = position.getSpeed();
								p2Direction = position.getDirection();
							}
							break;
						default:
							break;
					}
				}

			} catch(Exception e) {
				e.printStackTrace();
			}
		}

	}
	
	public class WriteToPlayer implements Runnable{
		
		private int playerId;
		private ObjectOutputStream out;
		
		public WriteToPlayer(int playerId, ObjectOutputStream out) {
			this.playerId = playerId;
			this.out = out;
			System.out.println("Server out stream erstellt für Spieler #" + playerId);
		}

		@Override
		public void run() {
			try {
				while(true) {
					PlayerPositionPacket position;
					
					if(playerId == 1) {
						position = new PlayerPositionPacket(2, p2x, p2y, p2speed, p2Direction);
					}
					else {
						position = new PlayerPositionPacket(2, p1x, p1y, p1speed, p1Direction);
					}
					out.writeObject(position);
					Thread.sleep(Server.PAUSE_DATAFLOW_TIME);
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}

	}

}
