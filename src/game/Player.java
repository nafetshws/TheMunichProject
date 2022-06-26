package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import multiplayer.Packet;
import multiplayer.PlayerAuthenticationPacket;
import multiplayer.PlayerPositionPacket;
import multiplayer.Server;

public class Player {
	
	private int x, y, size;
	private Color color;
	
	private int speed;
	
	public static final int GRAVITATIONAL_ACCELERATION = 20;
	private int jumpVelocity;
	
	private double lastTime;
	
	private boolean isJumping;
	private double jumpTime;
	private double s2ns = Math.pow(10, 9);
	
	//Multiplayer
	private Socket socket;
	private ReadFromServer readFromServer;
	private WriteToServer writeToServer;
	
	private int playerId;
	
	private int enemyX, enemyY, enemySpeed;
	
	
	public Player(int x, int y, Color color) {
		this.x = x;
		this.y = y;
		this.color = color;
		this.size = 75;
		this.speed = 8;
		this.jumpVelocity = -25;
		this.isJumping = false;
		this.jumpTime = 0;	
	}
	
	public void drawPlayer(Graphics2D g2) {
		g2.setColor(color);
		g2.fillRect(x, y, size, size);
	}
	
	public void moveLeft() {
		x -= speed;
	}
	
	public void moveRight() {
		x += speed;
	}
	
	public void updateY() {
		if(isJumping) {
			//Zählen der Zeit für die Sprungdauer/Zeit (jumpingTime)
			double currentTime = System.nanoTime();
			double timeInterval = currentTime - lastTime;
			lastTime = currentTime;
			
			jumpTime += timeInterval;
		}
		
		//Umwandlung der Zeit für Formel: Nanosekunden -> Sekunden
		double jumpTimeInSeconds = jumpTime / s2ns;
		
		// Zur Manipulation der Zeit einfach die Konstante verändern
		double gameJumpTime = jumpTimeInSeconds * 10;
		
		//y(t)=0.5*g*t*t+v*t
		y += (int)(0.5 * GRAVITATIONAL_ACCELERATION * gameJumpTime * gameJumpTime + jumpVelocity * gameJumpTime);
		
		if(y > 300) {
			//Damit der Spieler nicht durch den Boden fällt
			y = 300;
			isJumping = false;
			jumpTime = 0;
		}
	}
	
	public void jump() {
		jumpTime = 0;
		isJumping = true;
		lastTime = System.nanoTime();
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public boolean getIsJumping() {
		return isJumping;
	}
	
	public int getPlayerId() {
		return playerId;
	}
	
	//Multiplayer
	
	public int getEnemyX() {
		return enemyX;
	}

	public void setEnemyX(int enemyX) {
		this.enemyX = enemyX;
	}

	public int getEnemyY() {
		return enemyY;
	}

	public void setEnemyY(int enemyY) {
		this.enemyY = enemyY;
	}

	public int getEnemySpeed() {
		return enemySpeed;
	}

	public void setEnemySpeed(int enemySpeed) {
		this.enemySpeed = enemySpeed;
	}

	public void connectToServer() {
		try {
			socket = new Socket("localhost", Server.PORT);
			
			
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			
			Packet packet = (Packet) in.readObject();
			if (packet.getPacketId() == PlayerAuthenticationPacket.PACKET_ID) {
				PlayerAuthenticationPacket auth = (PlayerAuthenticationPacket) packet;
				playerId = auth.getPlayerId();
			}
			else {
				System.out.println("Failed to authenticate!");
			}
			
			if(playerId == 1) {
				System.out.println("Warte auf 2. Spieler...");
			}
			
			readFromServer = new ReadFromServer(in);
			writeToServer = new WriteToServer(out);
			
			Thread readThread = new Thread(readFromServer);
			Thread writeThread = new Thread(writeToServer);
			
			readThread.start();
			writeThread.start();

		} catch (IOException | ClassNotFoundException e) {
			System.out.println("Player failed to connect to Server");
			e.printStackTrace();
		}
	}
	
	public class WriteToServer implements Runnable{
		
		private ObjectOutputStream out;
		
		public WriteToServer(ObjectOutputStream out) {
			this.out = out;
			System.out.println("in constructor");
		}

		@Override
		public void run() {
			
			try {
				while(true) {
					PlayerPositionPacket packet = new PlayerPositionPacket(playerId, x, y, speed);
					out.writeObject(packet);
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
						case PlayerPositionPacket.PACKET_ID:
							PlayerPositionPacket position = (PlayerPositionPacket) p;
							enemyX = position.getXPos();
							enemyY = position.getYPos();
							enemySpeed = position.getSpeed();
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


