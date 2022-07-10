package ui;

import java.awt.Font;
import java.awt.Graphics2D;

import game.GamePanel;

public class StartScreen implements Screen{
	
	private boolean firstTime = true;
	private GamePanel gp;
	
	public StartScreen(GamePanel gp) {
		this.gp = gp;
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
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
		String title = "Startbildschirm";
		int x = calculateXPosition(g2, title);
		int y = 150;
		
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
