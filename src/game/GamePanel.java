package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
	
		//keine Ahnung, was das macht. 
	private static final long serialVersionUID = 1L;
	
	//format
	int screenWith=1920;
	int screenHeight=1080;
	
	int xPos=500;
	int yPos=700;
	int speed=4;
	//FPS (genauer gesagt die Zeit in Millisekunden, die das Programm wartet bevor es einen neuen Durchlauf startet)
	int fPS=16; 
	int jumpVelocity=-30;
	int g=1;
	//benutze ich als Zeitzählung
	int count=0;
	
		//Thread ist die Zeit
	Thread gameThread;
	// verwaltet die Tastenangabe
	KeyHandler keyH = new KeyHandler();
	
	
	public GamePanel() {
			//setzt eine feste Größe für den Spielbereich. Wahrscheinlich ist das unnötig, aber das Tutorial hat das so gemacht, deswegen habe ich es so nachprogrammiert. Wir können das ja noch später ändern. 
		this.setPreferredSize(new Dimension(screenWith, screenHeight));
			// erhöht die Rendering Performance
		this.setDoubleBuffered(true);	
		
		this.setBackground(Color.white);
		 
		this.addKeyListener(keyH);
		this.setFocusable(true);
		
		
	}
	 
	public void startGameThread() {
		//startet die Zeit
		gameThread = new Thread(this);
		gameThread.start();
	}


	
	public void run() {
		
		while(gameThread != null) {
		
		count++;
		// updated Spielinformationen;
		update();
		// setzt die neuen Spielinformationen graphisch um
		repaint();
		
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
		
		
		if(keyH.right==true) {
			xPos=xPos+speed;
		}
		if(keyH.left==true) {
			xPos=xPos-speed;		
		}
		
			// y=0.5*g*t*t+v*t+y
		yPos=(int) (0.5*g*count*count+jumpVelocity*count+700);
		if(700<yPos) {
			yPos=700;
					
		}
		
		if(keyH.up==true) {
			//startet die Zeitzählung für den Sprung
			//startet nur, wenn sich das objekt auf dem Boden befindet
			if(yPos==700) {
				count=0;
			}
			
		}
		
	}
	public void paintComponent(Graphics g) {
		// Methode repaint (Warum die jetzt anders heißt, weiß ich auch nicht)
		// Die folgenden 2 Zeilen verstehe ich nicht
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		//erzeugt ein quadratisches Objekt
		g2.setColor(Color.black);
		g2.fillRect(xPos,yPos, 100, 100);
		g2.dispose();
		
	}
		
	
	
}
