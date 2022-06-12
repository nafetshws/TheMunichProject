package ui;

import javax.swing.*;

import game.GamePanel;

import java.awt.*;

public class MainWindow {
	
	private static final int PREFFERED_SCREEN_WDITH = 1920;
	private static final int PREFFERED_SCREEN_HEIGHT = 1080;
	
    public static void main(String[] args) {
    	

        JFrame frame = new JFrame("TheMunichProject");
        GamePanel gamePanel = new GamePanel();
        
        //Damit das Spiel richtig schließt
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.setResizable(false);
        //fullscreen
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //top bar verschwinden lassen - erstmal deaktiviert (einfacher zum Arbeiten)
        //frame.setUndecorated(true);
        
        frame.add(gamePanel);
        gamePanel.startGameThread();

        // display it
        frame.pack();
        frame.setVisible(true);

    }
}
