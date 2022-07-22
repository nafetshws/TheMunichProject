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
import game.KeyHandler;
import game.Main;
import game.Music;
import game.Settings;
import multiplayer.Server;
import util.Menu;
import util.State;

public class StartScreen implements Screen, ActionListener{

	
	private boolean firstTime = true;
	private GamePanel gp;
	private JButton startButton;
	private JButton settingsButton;
	
	private KeyHandler keyHandler;
	
	private int index;
	
	public StartScreen(GamePanel gp, KeyHandler keyHandler) {
		this.gp = gp;
		gp.setBackground(Color.darkGray);
		index = 0;
		this.keyHandler = keyHandler;
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
		
		//TITLE
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 70F));
		String title = "Der Kampf um das goldene Flies";
		int x = calculateXPosition(g2, title);
		int y = 150;
		g2.setColor(Color.yellow);
		g2.drawString(title, x, y);
		
		//MENU
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50F));
		String startGameString = "Spiel erstellen";
		x = calculateXPosition(g2, startGameString);
		y = 500;
		g2.setColor(Color.white);
		g2.drawString(startGameString, x, y);
		
		String joinGameString = "Spiel beitreten";
		x = calculateXPosition(g2, joinGameString);
		y = 600;
		g2.setColor(Color.white);
		g2.drawString(joinGameString, x, y);
		
		String settingsString = "Einstellungen";
		x = calculateXPosition(g2, settingsString);
		y = 700;
		g2.setColor(Color.white);
		g2.drawString(settingsString, x, y);
		
		String quitString = "Quit";
		x = calculateXPosition(g2, quitString);
		y = 800;
		g2.setColor(Color.white);
		g2.drawString(quitString, x, y);
		
		switch(index) {
		case 0:
			g2.drawString(">", calculateXPosition(g2, startGameString) - 50, 500);
			break;
		case 1:
			g2.drawString(">", calculateXPosition(g2, joinGameString) - 50, 600);
			break;
		case 2:
			g2.drawString(">", calculateXPosition(g2, settingsString) - 50, 700);
			break;
		case 3:
			g2.drawString(">", calculateXPosition(g2, quitString) - 50, 800);
			break;
		default:
			break;
		}
		
				

//		startButton = new JButton("Start");
//				
//		settingsButton = new JButton("Settings");
//			
//		startButton.setBounds(450, 400, 450, 400);
//		startButton.setSize(80, 30);
//		startButton.addActionListener(this);
//		gp.add(startButton);
//		//startButton.setVisible(true);
//				
//		settingsButton.setBounds(750, 400, 750, 400);
//		settingsButton.setSize(80, 30);
//		settingsButton.addActionListener(this);
//		gp.add(settingsButton);
//		//settingsButton.setVisible(true);
//		//Main.team1Window.frame.setVisible(true);
//		System.out.println("draw gets called");
	}
	
	public void actionPerformed(ActionEvent aE) {
		//Code, der beschreibt wie man auf das Klicken des Buttons reagiert
		//Also das Aufrufen der Spielseite oder Settings
		if (aE.getSource() == startButton)
		{
			//gp.changeScreen(1);
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
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public void increaseIndex() {
		if(index <= 0) {
			index = 3;
		} else {
			index--;
		}
	}
	
	public void decreaseIndex() {
		if(index >= 3) {
			index = 0;
		} else {
			index++;
		}
	}
	
	public void select() {
		switch(index) {
		case 0:
			gp.changeScreen(State.Running);
			//Server server = new Server();
			//Thread serverThread = new Thread(server);
			//serverThread.start();
			break;
		case 1:
			gp.changeScreen(State.Running);
			break;
		case 2:
			break;
		case 3:
			System.exit(0);
			break;
		default:
			break;
		}
	}
}
