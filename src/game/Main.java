package game;

import java.awt.Color;

import multiplayer.Server;
import ui.MainWindow;
import util.Character;

public class Main {
	
	public static void main(String[] args){
		
		//Erstellen eines ServerThreads der im Hintergrund laeuft
		Server server = new Server();
		Thread serverThread = new Thread(server);
		serverThread.start();
		
		Player player1 = new Player(100, 300, Character.Drache);
		Player player2 = new Player(600, 300, Character.Jason);
		
		player1.connectToServer();
		player2.connectToServer();
	
		
		MainWindow player1Window = new MainWindow(720, 480, player1);
		MainWindow player2Window = new MainWindow(720, 480, player2);
		
	}
	
	public int add(int a, int b) {
		return a + b;
	}
}
