package multiplayer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

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
						if (initPacket.getSenderId() == 1) {
							// Team 1
							team1 = new Team(initPacket.getPlayer1(), initPacket.getPlayer2());
							System.out.println("Initialized team 1");
						} else {
							// Team 2
							team2 = new Team(initPacket.getPlayer1(), initPacket.getPlayer2());
							System.out.println("Initialized team 2");
						}
						break;
					case TeamPositionPacket.PACKET_ID:
						TeamPositionPacket positionPacket = (TeamPositionPacket) p;
						//System.out.println("Received position packet");
						//System.out.println("Y: " + positionPacket.getPlayer1().getX());
						if(positionPacket.getSenderId() == 1) {
							team1.getPlayer1().setX(positionPacket.getPlayer1().getX());
							team1.getPlayer1().setY(positionPacket.getPlayer1().getY());
							team1.getPlayer1().setSpeed(positionPacket.getPlayer1().getSpeed());
							team1.getPlayer1().setDirection(positionPacket.getPlayer1().getDirection());
							
							team1.getPlayer2().setX(positionPacket.getPlayer2().getX());
							team1.getPlayer2().setY(positionPacket.getPlayer2().getY());
							team1.getPlayer2().setSpeed(positionPacket.getPlayer2().getSpeed());
							team1.getPlayer2().setDirection(positionPacket.getPlayer2().getDirection());
						}
						else {
							team2.getPlayer1().setX(positionPacket.getPlayer1().getX());
							team2.getPlayer1().setY(positionPacket.getPlayer1().getY());
							team2.getPlayer1().setSpeed(positionPacket.getPlayer1().getSpeed());
							team2.getPlayer1().setDirection(positionPacket.getPlayer1().getDirection());
							
							team2.getPlayer2().setX(positionPacket.getPlayer2().getX());
							team2.getPlayer2().setY(positionPacket.getPlayer2().getY());
							team2.getPlayer2().setSpeed(positionPacket.getPlayer2().getSpeed());
							team2.getPlayer2().setDirection(positionPacket.getPlayer2().getDirection());
						}
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public class WriteToPlayer implements Runnable {

		private int teamId;
		private ObjectOutputStream out;
		private boolean initializedData = false;

		public WriteToPlayer(int teamId, ObjectOutputStream out) {
			this.teamId = teamId;
			this.out = out;
		}

		@Override
		public void run() {
			System.out.println("created wtp");
			try {
				while (true) {
					if(team1 != null && team2 != null) {
						
						if (numberOfTeams == 2 && !initializedData) {
							//System.out.println("Creating init packet from server");
							InitializationPacket initPacket;
							if (teamId == 1) {
								initPacket = new InitializationPacket(SERVER_SENDER_ID, team2.getPlayer1(),
										team2.getPlayer2());
							} else {
								initPacket = new InitializationPacket(SERVER_SENDER_ID, team1.getPlayer1(),
										team1.getPlayer2());
							}
							//System.out.println("Sending Init packet from server");
							out.writeObject(initPacket);
							initializedData = true;
						}

						TeamPositionPacket positionPacket;
						
						//System.out.println("Creating position packet");

						if (teamId == 1) {
							positionPacket = new TeamPositionPacket(SERVER_SENDER_ID, team2.getPlayer1(),
									team2.getPlayer2());
						} else {
							positionPacket = new TeamPositionPacket(SERVER_SENDER_ID, team1.getPlayer1(),
									team1.getPlayer2());
						}
						//System.out.println("Y: " + team1.getPlayer1().getY());

					}
					Thread.sleep(Server.PAUSE_DATAFLOW_TIME);
				}


			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
