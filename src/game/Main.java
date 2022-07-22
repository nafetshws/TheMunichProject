package game;

import java.awt.Color;

import multiplayer.Server;
import ui.MainWindow;
import util.Character;
import world.TileManager;

public class Main {
	
	public static void main(String[] args){
		
		//Erstellen eines ServerThreads der im Hintergrund laeuft
		Server server = new Server();
		Thread serverThread = new Thread(server);
		serverThread.start();
		
		Player player1 = new Player(0, 5500, Character.Drache);
		Player player2 = new Player(0, 5500, Character.Jason);
		
		Player player3 = new Player(1500, 5500, Character.Medea);
		Player player4 = new Player(1600, 5500, Character.Koenig);
		
		Team team1 = new Team(player1, player2);
		Team team2 = new Team(player3, player4);
		
		team1.connectToServer();
		team2.connectToServer();
	
		MainWindow team1Window = new MainWindow(720, 480, team1);
		MainWindow team2Window = new MainWindow(720, 480, team2);
		
	}
		
	
	public int add(int a, int b) {
		return a + b;
	}
}
