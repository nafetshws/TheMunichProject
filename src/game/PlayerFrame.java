package game;

import java.awt.Dimension;

import javax.swing.JFrame;

public class PlayerFrame {
	
	private int width, height;
	
	private KeyHandler keyHandler;
	
	public PlayerFrame(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public void setUpGUI() {
        JFrame frame = new JFrame("Game");
        GamePanel gamePanel = new GamePanel();
        
        //Damit das Spiel richtig schließt
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.setResizable(true);
        //fullscreen
        //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //top bar verschwinden lassen - erstmal deaktiviert (einfacher zum Arbeiten)
        //frame.setUndecorated(true);
        
        frame.setPreferredSize(new Dimension(width, height));
        
        frame.add(gamePanel);
        gamePanel.startGameThread();

        // display it
        frame.pack();
        frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		PlayerFrame pF = new PlayerFrame(720, 480);
		pF.setUpGUI();
	}

}
