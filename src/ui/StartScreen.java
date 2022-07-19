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
import game.Music;

public class StartScreen implements Screen{
//eigentlich muss man auch noch die Klasse "ActionListener" implementieren
//weiß nicht wie man zwei Klassen implementieren kann
	
	private boolean firstTime = true;
	private GamePanel gp;
	
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
		
		
		
		JPanel panel = new JPanel();
		gp.add(panel);
		
		JButton button = new JButton("Start");
		button.setBounds(200, 300, 200, 300);
		button.setSize(80, 30);
		button.setVisible(true);
		//folgende Zeile auskommentieren
		//button.addActionListener(this);
		gp.add(button);
		
		
		
		JPanel panel2 = new JPanel();
		gp.add(panel2);
		
		JButton button2 = new JButton("Settings");
		button2.setBounds(400, 300, 400, 300);
		button2.setSize(80, 30);
		button2.setVisible(true);
		//folgende Zeile auskommentieren
		//button2.addActionListener(this);
		gp.add(button2);
	}
	
	public void actionPerformed(ActionEvent e) {
		//Code, der beschreibt wie man auf das Klicken des Buttons reagiert
		//Also das Aufrufen der Spielseite oder Settings
		
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
