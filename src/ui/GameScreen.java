package ui;

import java.awt.Graphics2D;

import game.KeyHandler;
import game.Player;

public class GameScreen implements Screen{
	
	public Player me;
	public Player enemy;
	
	private KeyHandler keyHandler;
	
	public GameScreen(KeyHandler keyHandler, Player me, Player enemy) {
		this.keyHandler = keyHandler;
		this.me = me;
		this.enemy = enemy;
	}

	@Override
	public void update() {
		//Bewegung der Spieler auf der X-Achse
		//wenn keine Bewegung nach rechts oder links passiert, dann wir die Direction auf front gestellt
		if(keyHandler.getRight() == true) me.moveRight();
		else if(keyHandler.getLeft() == true) me.moveLeft();
		else me.dontmove();
		
		
		if(keyHandler.getUp() && !me.getIsJumping()) me.jump();
		
		
		//Updated y Position vom Spieler
		me.updateY();
		
		enemy.setX(me.getEnemyX());
		enemy.setY(me.getEnemyY());
		enemy.setSpeed(me.getEnemySpeed());
		enemy.setDirection(me.getEnemyDirection());
	}

	@Override
	public void draw(Graphics2D g2) {
		me.drawPlayer(g2);
		enemy.drawPlayer(g2);
	}

}
