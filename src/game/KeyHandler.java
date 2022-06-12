package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
	
	private boolean up;
	private boolean down;
	private boolean right;
	private boolean left;

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		
		if(code == KeyEvent.VK_W) {
			up = true;
		}
		if(code == KeyEvent.VK_S) {
			down = true;
		}
		if(code == KeyEvent.VK_D) {
			right = true;
		}
		if(code == KeyEvent.VK_A) {
			left = true;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		
		if(code == KeyEvent.VK_W) {
			up=false;
		}
		if(code == KeyEvent.VK_S) {
			down=false;
		}
		if(code == KeyEvent.VK_D) {
			right=false;
		}
		if(code == KeyEvent.VK_A) {
			left=false;
		}
		
	}
	
	public boolean getUp() {
		return up;
	}
	
	public boolean getDown() {
		return down;
	}
	
	public boolean getLeft() {
		return left;
	}
	
	public boolean getRight() {
		return right;
	}
	
}
