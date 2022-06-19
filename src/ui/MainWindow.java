package ui;

import javax.swing.*;

import game.GamePanel;

import java.awt.*;

public class MainWindow {
	
	private static final int PREFFERED_SCREEN_WDITH = 1920;
	private static final int PREFFERED_SCREEN_HEIGHT = 1080;
    
    public MainWindow(int width, int height, String title) {
        JFrame frame = new JFrame(title);
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
}
