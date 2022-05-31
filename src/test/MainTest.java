package test;

import static org.junit.Assert.*;

import org.junit.Test;
import game.Main;

public class MainTest {
	
	private Main main = new Main();

	@Test
	public void addition() {
		assertEquals(2, main.add(1, 1));
	}

}
