package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.imageio.ImageIO;

import multiplayer.InitializationPacket;
import multiplayer.Packet;
import multiplayer.PlayerAuthenticationPacket;
import multiplayer.PlayerPositionPacket;
import multiplayer.Server;
import util.Character;
import util.Direction;

public class Player {
	
	private int x, y, size;
	
	private int speed;
	
	public static final int GRAVITATIONAL_ACCELERATION = 15;
	private int jumpVelocity;
	
	private double lastTime;
	
	private boolean isJumping;
	private double jumpTime;
	private double s2ns = Math.pow(10, 9);
	
	//speichert die Bilder als Variablen
	private BufferedImage right1, right2, left1, left2, front;
	private Direction direction = Direction.Front;
	private Character character;
	
	//Multiplayer
	private Socket socket;
	private ReadFromServer readFromServer;
	private WriteToServer writeToServer;
	
	private int playerId;
	
	//Enemy
	private int enemyX, enemyY, enemySpeed;
	private Character enemyCharacter;
	private Direction enemyDirection;
	private int enemySize;
	
	
	public Player(int x, int y, Character character) {
		this.x = x;
		this.y = y;
		this.size = 100;
		this.speed = 8;
		this.jumpVelocity = -50;
		this.isJumping = false;
		this.jumpTime = 0;	
		this.character = character;
		
		getPlayerImage();
	}
	
	public void getPlayerImage() {
		// laedt die richtigen Bilder;
		String defaultPath = "images/" + character.toString() + "/" + character.toString() + " ";
		try {
			//right1 = ImageIO.read(getClass().getResourceAsStream("/game/Drache rechts 1.png"));
			right1 = ImageIO.read(getClass().getClassLoader().getResource(defaultPath + "rechts 1.png"));
			right2 = ImageIO.read(getClass().getClassLoader().getResource(defaultPath + "rechts 2.png"));
			left1 = ImageIO.read(getClass().getClassLoader().getResource(defaultPath + "links 1.png"));
			left2 = ImageIO.read(getClass().getClassLoader().getResource(defaultPath + "links 2.png"));
			front = ImageIO.read(getClass().getClassLoader().getResource(defaultPath + "vorne.png"));
		} catch (IOException e) {
			System.out.println("Fehler beim Laden der Bilder!");
		}
	}
	
	public void drawPlayer(Graphics2D g2) {

		BufferedImage image = null;
		
		switch(direction) {
			case Right: 
				image = right1;
				break;
			case Left: 
				image = left1;
				break;
			default:
				image = front;
				break;
		}
		
		g2.drawImage(image, x, y, size, size, null);
	}
	
	public void moveLeft() {
		direction = Direction.Left;
		x -= speed;
	}
	
	public void moveRight() {
		direction = Direction.Right;
		x += speed;
	}
	
	public void dontMove() {
		direction = Direction.Front;
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
		
		//y(t)=0.5*g*t*t+v*t+y(0)
		y = (int)(0.5 * GRAVITATIONAL_ACCELERATION * gameJumpTime * gameJumpTime + jumpVelocity * gameJumpTime+300);
		
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
	
	public Character getCharacter() {
		return character;
	}

	public void setCharacter(Character character) {
		this.character = character;
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
	

	public Direction getEnemyDirection() {
		return enemyDirection;
	}
	
	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public Character getEnemyCharacter() {
		return enemyCharacter;
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
		}

		@Override
		public void run() {
			
			try {
				
				//Sende essentielle Information ueber den Spieler zum Server
				InitializationPacket initPacket = new InitializationPacket(playerId, x, y, speed, direction, character);
				out.writeObject(initPacket);
				
				while(true) {
					PlayerPositionPacket packet = new PlayerPositionPacket(playerId, x, y, speed, direction);
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
						case InitializationPacket.PACKET_ID:
							InitializationPacket enemyInformation = (InitializationPacket) p;
							enemyX = enemyInformation.getXPos();
							enemyY = enemyInformation.getYPos();
							enemySpeed = enemyInformation.getSpeed();
							enemyDirection = enemyInformation.getDirection();
							enemyCharacter = enemyInformation.getCharacter();
							break;
						case PlayerPositionPacket.PACKET_ID:
							PlayerPositionPacket position = (PlayerPositionPacket) p;
							enemyX = position.getXPos();
							enemyY = position.getYPos();
							enemySpeed = position.getSpeed();
							enemyDirection = position.getDirection();
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


