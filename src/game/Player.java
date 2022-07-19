package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

import javax.imageio.ImageIO;

import multiplayer.InitializationPacket;
import multiplayer.Packet;
import multiplayer.TeamAuthenticationPacket;
import multiplayer.PlayerPositionPacket;
import multiplayer.Server;
import util.Character;
import util.Direction;

public class Player {
	
	private int worldx, worldy, size;
	private int screenX;
	private int screenY;
	
	private int speed;
	
	public static final int GRAVITATIONAL_ACCELERATION = 15;
	private int jumpVelocity;
	
	private double lastTime;
	
	private boolean isJumping;
	private double jumpTime;
	private double s2ns = Math.pow(10, 9);
	private int jumpHeight;
	
	//speichert die Bilder als Variablen (transient ist notwendig, damit die Klasse Player ueber das Netzwerk gesendet werden kann
	private BufferedImage right1, right2, left1, left2, front;
	private int imageCounter =  0;
	private Direction direction = Direction.Front;
	private Character character;
	
	private int playerId;
	
	private GamePanel gp;
	
	public Player(int x, int y, Character character) {
		this.worldx = x;
		this.jumpHeight = x;
		this.worldy = y;
		this.size = 100;
		this.speed = 6;
		this.jumpVelocity = -50;
		this.isJumping = false;
		this.jumpTime = 0;	
		this.character = character;

		loadPlayerImage();
	}
	
	public Player() {
		this.worldx = 0;
		this.worldy = 0;
		this.size = 100;
		this.speed = 6;
		this.jumpVelocity = -50;
		this.isJumping = false;
		this.jumpTime = 0;	
		this.character = Character.Drache;
		
		loadPlayerImage();
	}
	
	public void loadPlayerImage() {
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
		
		imageCounter++;
		
		if(imageCounter > 10) {
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
			}
			else {
				switch(direction) {
				case Right: 
					image = right2;
					break;
				case Left: 
					image = left2;
					break;
				default:
					image = front;
					break;
				}
			}
		
		if(imageCounter > 20) imageCounter=0;
		
		
		screenX = worldx - gp.getMe().getTeamX() + gp.getScreenX();
		screenY = worldy - gp.getMe().getTeamY() + gp.getScreenY();
		
		g2.drawImage(image, screenX, screenY, size, size, null);
	}
	
	public void moveLeft() {
		direction = Direction.Left;
		worldx -= speed;
	}
	
	public void moveRight() {
		direction = Direction.Right;
		worldx += speed;
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
		worldy = (int)(0.5 * GRAVITATIONAL_ACCELERATION * gameJumpTime * gameJumpTime + jumpVelocity * gameJumpTime + 300);
		
		if(worldy > 300) {
			//Damit der Spieler nicht durch den Boden fällt
			worldy = 300;
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
		loadPlayerImage();
	}

	public int getX() {
		return worldx;
	}

	public void setX(int x) {
		this.worldx = x;
	}

	public int getY() {
		return worldy;
	}

	public void setY(int y) {
		this.worldy = y;
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
	
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	public void setGamePanel(GamePanel gp) {
		System.out.println("About to set gp");
		this.gp = gp;
		System.out.println("State: " + this.gp.getMe().getTeamX());
	}
}


