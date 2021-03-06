package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import game.GamePanel;

public class CreateGameScreen implements Screen{
	
	private GamePanel gp;
	
	private boolean oneTime = true;
	
	public CreateGameScreen(GamePanel gp) {
		this.gp = gp;
	}

	@Override
	public void update() {
		
	}

	@Override
	public void draw(Graphics2D g2) {
		
		if(oneTime) {
			//Höhe und Breite des Fensters
			int w = gp.getWidth();
			int h = gp.getHeight();
			System.out.println(w + "x" + h);
			oneTime = false;
		}
		//Schriftart festlegen
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
		//Title festlegen
		String title = "Erstelle ein neues Spiel";
		//X-Position berechnen, damit der Text mittig ist
		int x = calculateXPosition(g2, title);
		int y = 150;
		
		//Text zeichnen
		g2.drawString(title, x, y);
	}
	
	public int calculateXPosition(Graphics2D g2, String text) {
		int totalLength = g2.getFontMetrics().stringWidth(text);
		if(gp.getWidth() > totalLength) {
			return (gp.getWidth() - totalLength) / 2;
		}
		return 0;
	}

}
