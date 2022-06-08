package multiplayer;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	public static final int PORT = 3000;
	
	public static void main(String[] args) throws Exception{
		new Server();
	}
	
	public Server() throws Exception{
		ServerSocket serverSocket = new ServerSocket(PORT);
		System.out.println("Server: Server läuft auf port " + PORT);
		Socket socket = serverSocket.accept();
		
		ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
		ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());
		
		//read
		Packet serverHello = (Packet) inStream.readObject();
		String message = serverHello.getMessage();
		System.out.println("Client: " + message);
		
		//write
		Packet p = new Packet("Hallo vom Server!");
		outStream.writeObject(p);
		
		serverSocket.close();
	}

}
