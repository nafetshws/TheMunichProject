package game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import multiplayer.InitializationPacket;
import multiplayer.Packet;
import multiplayer.TeamAuthenticationPacket;
import multiplayer.TeamPositionPacket;
import multiplayer.Server;

public class Team {
	
	private Player player1;
	private Player player2;
	
	private Player enemy1;
	private Player enemy2;
	
	//Multiplayer
	private Socket socket;
	private ReadFromServer readFromServer;
	private WriteToServer writeToServer;
	
	public Team(Player player1, Player player2) {
		this.player1 = player1;
		this.player2 = player2;
		
		this.enemy1 = new Player();
		this.enemy2 = new Player();
	}
	
	public Player getPlayer1() {
		return player1;
	}

	public Player getPlayer2() {
		return player2;
	}
	
	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}
	
	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}

	public Player getEnemy1() {
		return enemy1;
	}

	public Player getEnemy2() {
		return enemy2;
	}

	public Socket getSocket() {
		return socket;
	}

	public ReadFromServer getReadFromServer() {
		return readFromServer;
	}

	public WriteToServer getWriteToServer() {
		return writeToServer;
	}

	public void connectToServer() {
		try {
			socket = new Socket("localhost", Server.PORT);
			
			
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			
			//auth
			Packet packet = (Packet) in.readObject();
			if (packet.getPacketId() == TeamAuthenticationPacket.PACKET_ID) {
				TeamAuthenticationPacket auth = (TeamAuthenticationPacket) packet;
				
				player1.setPlayerId(auth.getPlayer1Id());
				player2.setPlayerId(auth.getPlayer2Id());
			}
			else {
				System.out.println("Failed to authenticate!");
			}
			
			InitializationPacket initPacket = new InitializationPacket(player1.getPlayerId(), player1, player2);
			out.writeObject(initPacket);
			
			readFromServer = new ReadFromServer(in);
			writeToServer = new WriteToServer(out);
			
			Thread readThread = new Thread(readFromServer);
			Thread writeThread = new Thread(writeToServer);
			
			readThread.start();
			writeThread.start();

		} catch (Exception e) {
			System.out.println("Player failed to connect to Server");
			e.printStackTrace();
		}
	}
	
	private class WriteToServer implements Runnable{
		
		private ObjectOutputStream out;
		
		public WriteToServer(ObjectOutputStream out) {
			this.out = out;
		}

		@Override
		public void run() {
			try {
				while(true) {
					TeamPositionPacket positionPacket = new TeamPositionPacket(player1.getPlayerId(), player1, player2);
					out.writeObject(positionPacket);
					//System.out.println("Y: " + positionPacket.getPlayer1().getY());
					Thread.sleep(Server.PAUSE_DATAFLOW_TIME);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private class ReadFromServer implements Runnable{
		
		private ObjectInputStream in;
		
		public ReadFromServer(ObjectInputStream in) {
			this.in = in;
		}

		@Override
		public void run() {
			try {
				while(true) {
					Packet p = (Packet) in.readObject();
					switch(p.getPacketId()) {
						case TeamAuthenticationPacket.PACKET_ID:
							TeamAuthenticationPacket authPacket = (TeamAuthenticationPacket) p;
							player1.setPlayerId(authPacket.getPlayer1Id());
							player2.setPlayerId(authPacket.getPlayer2Id());
						case InitializationPacket.PACKET_ID:
							//System.out.println("Received init packet from server");
							InitializationPacket enemyDataPacket = (InitializationPacket) p;
							enemy1.setX(enemyDataPacket.getPlayer1().getX());
							enemy1.setY(enemyDataPacket.getPlayer1().getY());
							enemy1.setSpeed(enemyDataPacket.getPlayer1().getSpeed());
							enemy1.setDirection(enemyDataPacket.getPlayer1().getDirection());
							enemy1.setCharacter(enemyDataPacket.getPlayer1().getCharacter());
							
							enemy2.setX(enemyDataPacket.getPlayer2().getX());
							enemy2.setY(enemyDataPacket.getPlayer2().getY());
							enemy2.setSpeed(enemyDataPacket.getPlayer2().getSpeed());
							enemy2.setDirection(enemyDataPacket.getPlayer2().getDirection());
							enemy2.setCharacter(enemyDataPacket.getPlayer2().getCharacter());
							break;
						case TeamPositionPacket.PACKET_ID:
							//System.out.println("Received position from server");
							TeamPositionPacket positionPacket = (TeamPositionPacket) p;
							enemy1.setX(positionPacket.getPlayer1().getX());
							enemy1.setY(positionPacket.getPlayer1().getY());
							enemy1.setSpeed(positionPacket.getPlayer1().getSpeed());
							enemy1.setDirection(positionPacket.getPlayer1().getDirection());
							
							enemy2.setX(positionPacket.getPlayer2().getX());
							enemy2.setY(positionPacket.getPlayer2().getY());
							enemy2.setSpeed(positionPacket.getPlayer2().getSpeed());
							enemy2.setDirection(positionPacket.getPlayer2().getDirection());
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
	
	

}
