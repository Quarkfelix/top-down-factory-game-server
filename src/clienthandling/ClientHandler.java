package clienthandling;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class ClientHandler implements Runnable {
	private HashMap<Integer, Client> clients = new HashMap<>();
	private boolean accepting = true;
	private ServerSocket server;
	private int currentidentifier = 0;
	
	private Socket tempclient;
	private InputStream in;
	private BufferedReader reader;

// ======================================== CONSTRUCTOR ========================================
	public ClientHandler(ServerSocket server) {
		synchronized (clients) {} //hoffentlich mach dass dass nur einer die liste modifizieren kann
		this.server = server;
		new Thread(this).start();
	}

// ======================================== RUN-METHOD =========================================	
	@Override
	public void run() {
		while(true) {
			while (accepting) {
				waitforClients();
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
// ======================================== METHODS ============================================
	/* if client sends 0 after connecting its the client.
	if client sends any other number it is the heartbeatsocket.
	client gets its heartbeatcode send through first socketconnection.*/
	private void waitforClients() {
		try {
			tempclient = server.accept();
			in = tempclient.getInputStream();
			reader = new BufferedReader(new InputStreamReader(in));
			int identifier = Integer.parseInt(reader.readLine());
			if(identifier == 0) {
				clients.put(clients.size(), new Client(tempclient, this.currentidentifier));
				this.currentidentifier++;
				System.out.println("accepted clients: " + clients.size());
			} else {
				clients.get(identifier).setHeartbeat(tempclient);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
// ======================================== GET/SET METHODS ====================================
	public Client getClient(int identifier) {
		return clients.get(identifier);
	}
	
	public void disconnectClient(int identifier) {
		clients.get(identifier).disconnect();
		clients.remove(identifier);
	}
}
