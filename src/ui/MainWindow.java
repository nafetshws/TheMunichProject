package ui;

import javax.swing.*;

import game.GamePanel;
import game.Player;
import game.Team;
import multiplayer.Server;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class MainWindow {
	
	private static final int PREFFERED_SCREEN_WDITH = 1920;
	private static final int PREFFERED_SCREEN_HEIGHT = 1080;
	
    public MainWindow(int width, int height, Team me, Server server) {
        JFrame frame = new JFrame("Team #" + me.getPlayer1().getPlayerId());
        GamePanel gamePanel = new GamePanel(me);
        gamePanel.setVisible(true);
        //Damit das Spiel richtig schließt
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.setResizable(true);
        //fullscreen
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //top bar verschwinden lassen - erstmal deaktiviert (einfacher zum Arbeiten)
        //frame.setUndecorated(true);
        
        //fuer nicht fullscreen
        frame.setPreferredSize(new Dimension(PREFFERED_SCREEN_WDITH, PREFFERED_SCREEN_WDITH));
        
        frame.add(gamePanel);
        gamePanel.startGameThread();
        
        frame.addWindowListener((WindowListener) new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if(server != null) {
                	me.disconnect();
                	server.closeServerConnection();
                	System.out.println("Server wurde geschlossen");
                }
                System.exit(0);
            }
        });

        // display it
        frame.pack();
        frame.setVisible(true);

    }
}
