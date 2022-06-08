package multiplayer;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
	
	public static void main(String[] args) throws Exception{
		new Client();
	}
	
	public Client() throws Exception{
		Socket socket = new Socket("127.0.0.1", Server.PORT);
		
		ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
		ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());
		
		
		Packet clientHello = new Packet("Hallo vom Client!");
		outStream.writeObject(clientHello);
		
		Packet p = (Packet) inStream.readObject();
		String msg = p.getMessage();
		System.out.println("Server: " + msg);
		
		socket.close();
		
	}

}
