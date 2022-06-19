package game;

import java.awt.Color;
import java.awt.Graphics2D;

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
	
	public void drawSprite(Graphics2D g2) {
		g2.setColor(Color.black);
		g2.fillRect(x, y, size, size);
		g2.dispose();
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
	
	public boolean getIsJumping() {
		return isJumping;
	}
}
