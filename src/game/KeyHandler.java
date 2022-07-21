package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import ui.StartScreen;
import util.State;

public class KeyHandler implements KeyListener{
	
	private boolean up;
	private boolean down;
	private boolean right;
	private boolean left;
	
	private boolean upArrow;
	private boolean downArrow;
	private boolean rightArrow;
	private boolean leftArrow;
	
	private GamePanel gp;
	private State state = State.Running;
	
	public KeyHandler() {}
	
	public KeyHandler(GamePanel gp) {
		this.gp = gp;
		this.state = gp.getState();
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		
		if(gp.getState() == State.Start) {
			StartScreen screen = (StartScreen) gp.getScreens().get(State.Start);
			if(code == KeyEvent.VK_UP) {
				screen.increaseIndex();
			}
			
			if(code == KeyEvent.VK_DOWN) {
				screen.decreaseIndex();
			}
			
			if(code == KeyEvent.VK_ENTER) {
				screen.select();
			}
			return;
		}
		
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
