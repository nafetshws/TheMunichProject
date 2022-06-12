package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
	
	private String os;
	
	private int xPos = 500;
	private int yPos = 700;
	private int speed = 4;
	
	//FPS (genauer gesagt die Zeit in Millisekunden, die das Programm wartet bevor es einen neuen Durchlauf startet)
	private int fPS = 8; 
	private int jumpVelocity = -30;
	private int gravitationalAcceleration = 1;
	
	//benutze ich als Zeitz�hlung
	private int count=0;
	
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
		
		while(gameThread != null) {
			
			count++;
			// updated Spielinformationen;
			update();
			
			// setzt die neuen Spielinformationen graphisch um
			repaint();
			//optimiert Leistung für linux
			if(os.contains("Linux")) {
				Toolkit.getDefaultToolkit().sync();
			}
			
			try {
				Thread.sleep(fPS);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	public void update() {
		//bewegt die Spieler
		
		if(keyHandler.getRight()) {
			xPos = xPos + speed;
		}
		else if(keyHandler.getLeft()) {
			xPos = xPos-speed;		
		}
		
		//y(t)=0.5*g*t*t+v*t+y
		yPos = (int) (0.5*gravitationalAcceleration*count*count+jumpVelocity*count+700);
		if(yPos >= 700) {
			yPos = 700;
					
		}
		
		if(keyHandler.getUp()) {
			//startet die Zeitz�hlung f�r den Sprung
			//startet nur, wenn sich das objekt auf dem Boden befindet
			if(yPos >= 700) {
				count = 0;
			}
			
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
