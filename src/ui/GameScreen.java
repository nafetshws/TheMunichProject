package ui;

import java.awt.Dimension;
import java.awt.Graphics2D;

import World.TileManager;
import game.GamePanel;
import game.KeyHandler;
import game.Player;
import game.Team;

public class GameScreen implements Screen{
	
	private GamePanel gp;
	
	private Team me;
	private Team enemyTeam;
	
	private KeyHandler keyHandler;
	
	private TileManager tileM;
	
	public GameScreen(GamePanel gp, KeyHandler keyHandler, Team me, Team enemyTeam) {
		this.gp = gp;
		this.keyHandler = keyHandler;
		this.me = me;
		this.enemyTeam = enemyTeam;
		tileM = new TileManager(gp);
	}

	@Override
	public void update() {
		
		//Bewegung der Spieler auf der X-Achse
		//wenn keine Bewegung nach rechts oder links passiert, dann wir die Direction auf front gestellt
		
		//---Player 1---
		if(keyHandler.getRight() == true) me.getPlayer1().moveRight();
		else if(keyHandler.getLeft() == true) me.getPlayer1().moveLeft();
		else me.getPlayer1().dontMove();
		
		//Sprung registriert?
		if(keyHandler.getUp() && !me.getPlayer1().getIsJumping()) me.getPlayer1().jump();
		
		//Updated y Position vom Spieler
		me.getPlayer1().updateY();
		
		//---Player 2----
		
		if(keyHandler.isRightArrow() == true) me.getPlayer2().moveRight();
		else if(keyHandler.isLeftArrow() == true) me.getPlayer2().moveLeft();
		else me.getPlayer2().dontMove();
		
		//Sprung registriert?
		if(keyHandler.isUpArrow() && !me.getPlayer2().getIsJumping()) me.getPlayer2().jump();
		
		//Updated y Position vom Spieler
		me.getPlayer2().updateY();
		
		//Updated die Informationen vom gegnerischen Team
		enemyTeam.getPlayer1().setX(me.getEnemy1().getX());
		enemyTeam.getPlayer1().setY(me.getEnemy1().getY());
		enemyTeam.getPlayer1().setSpeed(me.getEnemy1().getSpeed());
		enemyTeam.getPlayer1().setDirection(me.getEnemy1().getDirection());
		
		enemyTeam.getPlayer2().setX(me.getEnemy2().getX());
		enemyTeam.getPlayer2().setY(me.getEnemy2().getY());
		enemyTeam.getPlayer2().setSpeed(me.getEnemy2().getSpeed());
		enemyTeam.getPlayer2().setDirection(me.getEnemy2().getDirection());
		
	}

	@Override
	public void draw(Graphics2D g2) {
		// anscheinend ist es wichtig die Tiles vor den Spielern zu malen
	
		tileM.draw(g2);
		
		me.getPlayer1().drawPlayer(g2);
		me.getPlayer2().drawPlayer(g2);
		
		enemyTeam.getPlayer1().drawPlayer(g2);
		enemyTeam.getPlayer2().drawPlayer(g2);
	}

}
