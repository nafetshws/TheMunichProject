package game;

import multiplayer.Client;
import multiplayer.Server;
import ui.MainWindow;

public class Main {
	
	public static void main(String[] args) {
		MainWindow window = new MainWindow();
		
		boolean isServer = args[0].contains("server") ? true : false;
		
		if(isServer) {
			try {
				Server server = new Server();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			try {
				Client client = new Client();
			} catch (Exception e) {
				//Server existiert vermutlich nicht
				e.printStackTrace();
			}
		}
	}
	
	public int add(int a, int b) {
		return a + b;
	}
}
