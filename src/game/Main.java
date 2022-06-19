package game;

import java.awt.Color;

import multiplayer.Server;
import ui.MainWindow;

public class Main {
	
	public static void main(String[] args) {
		
		//Um die Applikation ausführen zu können, muss man vorher Server.java ausführen.
		
		Player player1 = new Player(100, 300, Color.black);
		Player player2 = new Player(600, 300, Color.red);
		
		player1.connectToServer();
		player2.connectToServer();
		
		MainWindow player1Window = new MainWindow(720, 480, player1);
		MainWindow player2Window = new MainWindow(720, 480, player2);
		
	}
	
	public int add(int a, int b) {
		return a + b;
	}
}
