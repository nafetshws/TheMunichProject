package ui;

import javax.swing.*;

import game.GamePanel;

import java.awt.*;

public class MainWindow {
    public static void main(String[] args) {

        JFrame frame = new JFrame("Game");
        GamePanel gamePanel = new GamePanel();
        // set frame site
        frame.setMinimumSize(new Dimension(800, 600));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        
        frame.add(gamePanel);
        gamePanel.startGameThread();

        // display it
        frame.pack();
        frame.setVisible(true);

    }
}
