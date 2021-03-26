package infrastructure;

import java.io.IOException;
import java.net.ServerSocket;

import clienthandling.ClientHandler;

public class Server {
	public static ClientHandler clientHandler;
	private ServerSocket socket;
	private int port = 50532;
	
// ======================================== CONSTRUCTOR ========================================
	public Server() {
		try {
			socket = new ServerSocket(50532);
			System.out.println("server created!\nlistening on port " + port);
			clientHandler = new ClientHandler(socket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
// ======================================== RUN-METHOD =========================================	

// ======================================== METHODS ============================================

// ======================================== GET/SET METHODS ====================================

// ======================================== PAINT-METHODS ======================================

}
