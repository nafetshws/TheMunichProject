package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JPanel;

import util.Character;


public class GamePanel extends JPanel implements Runnable {
	
	private String os;
	//Sekunden zu Nanosekunden
	private double s2ns = Math.pow(10, 9);
	
	//frames per second
	private int FPS = 60;
	
	private boolean isJumping;

	//Thread ist die Zeit
	private Thread gameThread;
	// verwaltet die Tastenangabe
	private KeyHandler keyHandler = new KeyHandler();
	
	public Player me;
	public Player enemy;
	
	
	public GamePanel(Player me) {
		
		this.me = me;
		this.enemy = new Player(me.getEnemyX(), me.getEnemyY(), me.getEnemyCharacter());
		
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
		
		
		//game loop
		while(gameThread != null) {
			
			double currentTime = System.nanoTime();
			//Addiert zur Deltazeit, den Bruchteil von: 
			//	Wie viel Zeit beim Rendern des letzten Bildes vergangen ist,
			//	-------------------------------------------------------------------------------------------------------
			//	Wie viel Zeit maximal vergehen darf, damit man auf die entsprechende Anzahl an Bilder pro Sekunde kommt
			
			delta += (currentTime - lastTime) / nsPerTick;
			
			//Vergangen Zeit zwischen Jetzt und dem davor gerenderten Bild
			double timeInterval = (currentTime - lastTime);
			
			//Addiert zur vergangenen Zeit für fps counter
			timePassed += timeInterval;
			
			lastTime = currentTime;
			
			//Sobald die Zeit überschritten wurde, um die entsprechende Anzahl an Bilder/s zu erreichen, wird das neue Bild gerendert.
			if(delta >= 1) {
				update();
				repaint();
				
				//Notwendig zum Zählen der fps
				framesCounter++;
				
				//Optimierung für Linux
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
		if(keyHandler.getRight() == true) me.moveRight();
		if(keyHandler.getLeft() == true) me.moveLeft();
		
		if(keyHandler.getUp() && !me.getIsJumping()) me.jump();
		
		
		//Updated y Position vom Spieler
		me.updateY();
		
		enemy.setX(me.getEnemyX());
		enemy.setY(me.getEnemyY());
		enemy.setSpeed(me.getEnemySpeed());
		enemy.setDirection(me.getEnemyDirection());
		
	}
	
	public void paintComponent(Graphics g) {
		// Methode repaint (Warum die jetzt anders hei�t, wei� ich auch nicht)
		// Die folgenden 2 Zeilen verstehe ich nicht
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;
		
		//Zeichnet Spieler am Screen
		me.drawPlayer((Graphics2D) g);
		enemy.drawPlayer((Graphics2D) g);
		
		//Alles was hinter dispose steht wird nicht mehr gerendert
		g2.dispose();
		
	}
	

		
}
