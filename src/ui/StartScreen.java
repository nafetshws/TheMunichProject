package ui;

import java.awt.Color;

//import java.awt.*;
import java.awt.Font;
//import java.awt.Graphics;
import java.awt.Graphics2D;
//import java.awt.event.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JButton;
//import javax.swing.JFrame; 
import javax.swing.JPanel;

import game.GamePanel;
import game.Main;
import game.Music;
import game.Settings;

public class StartScreen implements Screen, ActionListener{

	
	private boolean firstTime = true;
	private GamePanel gp;
	private JButton startButton;
	private JButton settingsButton;
	
	public StartScreen(GamePanel gp) {
		this.gp = gp;
		gp.setBackground(Color.darkGray);
		Music myMusic = new Music();
	}

	@Override
	public void update() {
		
	}

	@Override
	public void draw(Graphics2D g2) {
		
		
		
		if(firstTime) {
			int w = gp.getWidth();
			int h = gp.getHeight();
			System.out.println(w + "x" + h);
		
			
		
			firstTime = false;
		}
		
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 45F));
		String title = "Der Kampf um das goldene Flies";
		int x = calculateXPosition(g2, title);
		int y = 150;
		g2.setColor(Color.yellow);
		g2.drawString(title, x, y);
				

		startButton = new JButton("Start");
				
		settingsButton = new JButton("Settings");
			
		startButton.setBounds(450, 400, 450, 400);
		startButton.setSize(80, 30);
		startButton.addActionListener(this);
		gp.add(startButton);
		//startButton.setVisible(true);
				
		settingsButton.setBounds(750, 400, 750, 400);
		settingsButton.setSize(80, 30);
		settingsButton.addActionListener(this);
		gp.add(settingsButton);
		//settingsButton.setVisible(true);
		//Main.team1Window.frame.setVisible(true);
		System.out.println("draw gets called");
	}
	
	public void actionPerformed(ActionEvent aE) {
		//Code, der beschreibt wie man auf das Klicken des Buttons reagiert
		//Also das Aufrufen der Spielseite oder Settings
		if (aE.getSource()== startButton)
		{
			gp.changeScreen(1);
		}
		else {

		}
		//Link zur Seite wo ich die Infos herhabe:
		//https://docs.oracle.com/javase/tutorial/uiswing/events/actionlistener.html
	}
     
	public int calculateXPosition(Graphics2D g2, String text) {
		int totalLength = g2.getFontMetrics().stringWidth(text);
		if(gp.getWidth() > totalLength) {
			return (gp.getWidth() - totalLength) / 2;
		}
		return 0;
	}
}
