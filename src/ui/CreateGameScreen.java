package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class CreateGameScreen implements Screen{

	@Override
	public void update() {
		
	}

	@Override
	public void draw(Graphics2D g2) {
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 80F));
		String title = "Erstelle ein neues Spiel";
		int x = 150;
		int y = 150;
		
		g2.drawString(title, x, y);
	}

}
