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
	
	int xPos=100;
	int yPos=100;
	int speed=4;
	//Bildrate pro Sekunde in Millisekunden
	int fPS=16; 
	
		//Thread ist die Zeit
	Thread gameThread;
	// verwaltet die Tastenangabe
	KeyHandler keyH = new KeyHandler();
	
	
	public GamePanel() {
			//setzt eine feste Gr��e f�r den Spielbereich. Wahrscheinlich ist das unn�tig, aber das Tutorial hat das so gemacht, deswegen habe ich es so nachprogrammiert. Wir k�nnen das ja noch sp�ter �ndern. 
		this.setPreferredSize(new Dimension(screenWith, screenHeight));
			// erh�ht die Rendering Performance
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
		System.out.println("The System is running");
		
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
