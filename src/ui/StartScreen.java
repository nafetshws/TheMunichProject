package ui;

import java.awt.Color;

//import java.awt.*;
import java.awt.Font;
//import java.awt.Graphics;
import java.awt.Graphics2D;
//import java.awt.event.*;

//import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JButton;
//import javax.swing.JFrame; 
import javax.swing.JPanel;

import game.GamePanel;
import game.Music;

public class StartScreen implements Screen{
	
	private boolean firstTime = true;
	private GamePanel gp;
	
	public StartScreen(GamePanel gp) {
		this.gp = gp;
		gp.setBackground(Color.darkGray);
	}

	@Override
	public void update() {
		Music myMusic = new Music();
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
		
		//Graphics2D g3 = new Graphics2D();
		
		JPanel panel = new JPanel();
		gp.add(panel);
		
		JButton button = new JButton("Start");
		button.setBounds(200, 200, 200, 200);
		button.setSize(80, 30);
		button.setVisible(true);
		//button.addActionListener(this);
		gp.add(button);
		
		
			
		//g3.setFont(g3.getFont().deriveFont(Font.BOLD, 30F));
		//String title2 = "Start";
		//int x2 = 200;
		//int y2 = 300;
		//g3.setColor(Color.yellow);
			
		//g3.drawString(title2, x2, y2);
		
		
	}
	private void addFileMenuItems(JMenu fileMenu) {
		
		JMenuItem quitItem = new JMenuItem("Quit");
	    fileMenu.add(quitItem);    
	}
	
	
     
	
	public int calculateXPosition(Graphics2D g2, String text) {
		int totalLength = g2.getFontMetrics().stringWidth(text);
		if(gp.getWidth() > totalLength) {
			return (gp.getWidth() - totalLength) / 2;
		}
		return 0;
	}
}
