package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
	
	private boolean up;
	private boolean down;
	private boolean right;
	private boolean left;
	
	private boolean upArrow;
	private boolean downArrow;
	private boolean rightArrow;
	private boolean leftArrow;

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		
		//Player 1
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
		
		//Player 2
		if(code == KeyEvent.VK_UP) {
			upArrow = true;
		}
		if(code == KeyEvent.VK_DOWN) {
			downArrow = true;
		}
		if(code == KeyEvent.VK_RIGHT) {
			rightArrow = true;
		}
		if(code == KeyEvent.VK_LEFT) {
			leftArrow = true;
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
		
		if(code == KeyEvent.VK_UP) {
			upArrow = false;
		}
		if(code == KeyEvent.VK_DOWN) {
			downArrow = false;
		}
		if(code == KeyEvent.VK_RIGHT) {
			rightArrow = false;
		}
		if(code == KeyEvent.VK_LEFT) {
			leftArrow = false;
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

	//Player 2
	
	public boolean isUpArrow() {
		return upArrow;
	}

	public boolean isDownArrow() {
		return downArrow;
	}

	public boolean isRightArrow() {
		return rightArrow;
	}

	public boolean isLeftArrow() {
		return leftArrow;
	}
	
}
