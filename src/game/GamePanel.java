package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
	
	private String os;
	//Sekunden zu Nanosekunden
	private double s2ns = Math.pow(10, 9);
	
	private int xPos = 500;
	private int yPos = 700;
	private int speed = 8;
	
	//frames per second
	private int FPS = 60;
	
	private int jumpVelocity = -25;
	private double jumpTime = 0;
	private boolean isJumping;
	private int gravitationalAcceleration = 20;

	//Thread ist die Zeit
	private Thread gameThread;
	// verwaltet die Tastenangabe
	private KeyHandler keyHandler = new KeyHandler();

	
	
	
	
	public GamePanel() {
		
		os = System.getProperty("os.name");
		
		// erh�ht die Rendering Performance
		this.setDoubleBuffered(true);	
		
		this.setBackground(Color.white);
		 
		this.addKeyListener(keyHandler);
		this.setFocusable(true);
		
		
	}
	 
	public void startGameThread() {
		//startet die Zeit
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	@Override
	public void run() {
		
		//Anzahl an Nanosekunden, die pro Zyklus vergehen dürfen, damit man auf die entsprechende Anzahl an Bildern pro Sekunde kommt
		double nsPerTick = s2ns / FPS;
		double lastTime = System.nanoTime();
		//Artikel der das Prinzip von delta time erklärt: https://drewcampbell92.medium.com/understanding-delta-time-b53bf4781a03
		double delta = 0;
		
		//Für das Zählen der Bilder pro Sekunde (fps)
		int framesCounter = 0;
		double timePassed = 0;
		
		
		while(gameThread != null) {
			
			double currentTime = System.nanoTime();
			//Addiert zur Deltazeit, den Bruchteil von: 
			//	Wie viel Zeit beim Rendern des letzten Bildes vergangen ist,
			//	-------------------------------------------------------------------------------------------------------
			//	Wie viel Zeit maximal vergehen darf, damit man auf die entsprechende Anzahl an Bilder pro Sekunde kommt
			
			delta += (currentTime - lastTime) / nsPerTick;
			
			//Vergangen Zeit zwischen Jetzt und dem davor gerenderten Bild
			double timeInterval = (currentTime - lastTime);
			
			//addiert Zeit für den Sprung
			if(isJumping) jumpTime += timeInterval;
			
			//Addiert zur vergangenen Zeit für fps counter
			timePassed += timeInterval;
			
			lastTime = currentTime;
			
			//Sobald die Zeit überschritten wurde, um die entsprechende Anzahl an Bilder/s zu erreichen, wird das neue Bild gerendert.
			if(delta >= 1) {
				update();
				repaint();
				
				//Notwendig zum Zählen der fps
				framesCounter++;
				
				//Optimierung von Swing für Linux
				if(os.contains("Linux")) {
					Toolkit.getDefaultToolkit().sync();
				}
				//Delta zurücksetzen
				delta--;
			}
			
			if(timePassed >= s2ns) {
				//System.out.println("FPS: " + framesCounter);
				framesCounter = 0;
				timePassed = 0;
			}
			

		}
	}

	
	public void update() {
		//Bewegung der Spieler auf der X-Achse
		if(keyHandler.getRight()) {
			xPos = xPos + speed;
		}
		else if(keyHandler.getLeft()) {
			xPos = xPos-speed;		
		}
		
		//Bewegung der Spieler auf der Y-Achse
		
		
		double jumpTimeInSeconds = jumpTime / s2ns;
		
		// Zur Manipulation der Zeit einfach die Konstante verändern
		double gameJumpTime = jumpTimeInSeconds * 10;
		
		//y(t)=0.5*g*t*t+v*t
		yPos += (0.5 * gravitationalAcceleration * gameJumpTime * gameJumpTime + jumpVelocity * gameJumpTime);
		
		if(yPos > 700) {
			//Damit der Spieler nicht durch den Boden fällt
			yPos = 700;
			isJumping = false;
			jumpTime = 0;
		}
		
		if(keyHandler.getUp() && !isJumping) {
			//Sprung registriert
			jumpTime = 0;
			isJumping = true;
		}
		
	}
	
	public void paintComponent(Graphics g) {
		// Methode repaint (Warum die jetzt anders hei�t, wei� ich auch nicht)
		// Die folgenden 2 Zeilen verstehe ich nicht
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		//erzeugt ein quadratisches Objekt
		g2.setColor(Color.black);
		g2.fillRect(xPos,yPos, 100, 100);
		g2.dispose();
		
	}
		
	
	
}
