package multiplayer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import game.Player;
import game.Team;
import util.Character;
import util.Direction;

public class Server implements Runnable {

	public static final int PORT = 3000;
	public static final int PAUSE_DATAFLOW_TIME = 6;
	public static final int SERVER_SENDER_ID = 0;

	private ServerSocket serverSocket;

	private Socket team1Socket;
	private Socket team2Socket;

	private ReadFromPlayer readFromTeam1;
	private ReadFromPlayer readFromTeam2;
	private WriteToPlayer writeToTeam1;
	private WriteToPlayer writeToTeam2;

	private Team team1;
	private Team team2;

	private int maxNumberOfTeams;
	private int numberOfTeams;

	private int numberOfPlayers;

	public Server() {
		numberOfTeams = 0;
		maxNumberOfTeams = 2;
		numberOfPlayers = 0;

		try {
			serverSocket = new ServerSocket(PORT);
		} catch (IOException e) {
			System.out.println("Fehler beim Erstellen des GameServerSockets");
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		waitForConnections();
	}

	public void waitForConnections() {
		System.out.println("Warten auf Spieler...");

		while (numberOfTeams < maxNumberOfTeams) {
			try {
				Socket socket = serverSocket.accept();

				// Spieler hat sich verbunden

				ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

				numberOfTeams++;
				numberOfPlayers += 2;

				TeamAuthenticationPacket auth = new TeamAuthenticationPacket(numberOfPlayers - 1, numberOfPlayers);
				out.writeObject(auth);

				System.out.println("Spieler #" + numberOfTeams + " hat sich erfolgreich mit dem Server verbunden");

				ReadFromPlayer rfp = new ReadFromPlayer(numberOfTeams, in);
				WriteToPlayer wtp = new WriteToPlayer(numberOfTeams, out);

				if (numberOfTeams == 1) {
					team1Socket = socket;

					readFromTeam1 = rfp;
					writeToTeam1 = wtp;

					Thread readThread1 = new Thread(readFromTeam1);
					Thread writeThread1 = new Thread(writeToTeam1);

					readThread1.start();
					writeThread1.start();
				} else {
					team2Socket = socket;

					readFromTeam2 = rfp;
					writeToTeam2 = wtp;

					Thread readThread2 = new Thread(readFromTeam2);
					Thread writeThread2 = new Thread(writeToTeam2);

					readThread2.start();
					writeThread2.start();
				}

			} catch (Exception e) {
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

	public class ReadFromPlayer implements Runnable {

		private int playerId;
		private ObjectInputStream in;

		public ReadFromPlayer(int playerId, ObjectInputStream in) {
			this.playerId = playerId;
			this.in = in;
		}

		@Override
		public void run() {
			try {

				while (true) {
					Packet p = (Packet) in.readObject();
					switch (p.getPacketId()) {
						case InitializationPacket.PACKET_ID:
							InitializationPacket initPacket = (InitializationPacket) p;
							
							Player p1 = new Player();
							Player p2 = new Player();
							
							p1.setX(initPacket.getP1x());
							p1.setY(initPacket.getP1y());
							p1.setSpeed(initPacket.getP1speed());
							p1.setDirection(initPacket.getP1direction());
							p1.setCharacter(initPacket.getP1character());
							
							p2.setX(initPacket.getP2x());
							p2.setY(initPacket.getP2y());
							p2.setSpeed(initPacket.getP2speed());
							p2.setDirection(initPacket.getP2direction());
							p2.setCharacter(initPacket.getP2character());
							
							if (initPacket.getSenderId() == 1) {
								// Team 1
								team1 = new Team(p1, p2);
								System.out.println("Initialized team 1");
							} else {
								// Team 2
								team2 = new Team(p1, p2);
								System.out.println("Initialized team 2");
							}
	
							break;
						case TeamPositionPacket.PACKET_ID:
							TeamPositionPacket positionPacket = (TeamPositionPacket) p;
							
							PlayerPositionPacket player1PositionPacket = positionPacket.getPlayer1PositionPacket();
							PlayerPositionPacket player2PositionPacket = positionPacket.getPlayer2PositionPacket();
							
							if(positionPacket.getSenderId() == 1) {
								team1.getPlayer1().setX(player1PositionPacket.getX());
								team1.getPlayer1().setY(player1PositionPacket.getY());
								team1.getPlayer1().setSpeed(player1PositionPacket.getSpeed());
								team1.getPlayer1().setDirection(player1PositionPacket.getDirection());
								
								team1.getPlayer2().setX(player2PositionPacket.getX());
								team1.getPlayer2().setY(player2PositionPacket.getY());
								team1.getPlayer2().setSpeed(player2PositionPacket.getSpeed());
								team1.getPlayer2().setDirection(player2PositionPacket.getDirection());
							}
							else {
								team2.getPlayer1().setX(player1PositionPacket.getX());
								team2.getPlayer1().setY(player1PositionPacket.getY());
								team2.getPlayer1().setSpeed(player1PositionPacket.getSpeed());
								team2.getPlayer1().setDirection(player1PositionPacket.getDirection());
								
								team2.getPlayer2().setX(player2PositionPacket.getX());
								team2.getPlayer2().setY(player2PositionPacket.getY());
								team2.getPlayer2().setSpeed(player2PositionPacket.getSpeed());
								team2.getPlayer2().setDirection(player2PositionPacket.getDirection());
							}

					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public class WriteToPlayer implements Runnable {

		private int playerId;
		private ObjectOutputStream out;
		private boolean initializedData = false;

		public WriteToPlayer(int playerId, ObjectOutputStream out) {
			this.playerId = playerId;
			this.out = out;
		}

		@Override
		public void run() {
			try {
				while (true) {
					if(team1 != null && team2 != null) {
						if (numberOfTeams == 2 && !initializedData) {
							//System.out.println("Creating init packet from server");
							InitializationPacket initPacket;
							
							Player p1;
							Player p2;
							
							if (playerId == 1) {
								p1 = team2.getPlayer1();
								p2 = team2.getPlayer2();
							} else {
								p1 = team1.getPlayer1();
								p2 = team1.getPlayer2();
							}
							
							initPacket = new InitializationPacket(playerId, p1.getX(), p1.getY(), p1.getSpeed(), p1.getDirection(), p1.getCharacter(), p2.getX(), p2.getY(), p2.getY(), p2.getDirection(), p2.getCharacter());
							
							out.writeObject(initPacket);
							initializedData = true;
						}

						PlayerPositionPacket player1PositionPacket;
						PlayerPositionPacket player2PositionPacket;
						
						Player p1;
						Player p2;

						if (playerId == 1) {
							p1 = team2.getPlayer1();
							p2 = team2.getPlayer2();
						} else {
							p1 = team1.getPlayer1();
							p2 = team1.getPlayer2();							
						}
						
						player1PositionPacket = new PlayerPositionPacket(p1.getPlayerId(), p1.getX(), p1.getY(), p1.getSpeed(), p1.getDirection());
						player2PositionPacket = new PlayerPositionPacket(p2.getPlayerId(), p2.getX(), p2.getY(), p2.getSpeed(), p2.getDirection());
						
						TeamPositionPacket teamPositionPacket = new TeamPositionPacket(p1.getPlayerId(), player1PositionPacket, player2PositionPacket);
						
						out.writeObject(teamPositionPacket);

					}
					Thread.sleep(Server.PAUSE_DATAFLOW_TIME);
				}


			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
