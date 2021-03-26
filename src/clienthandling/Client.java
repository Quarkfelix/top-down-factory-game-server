package clienthandling;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable {
	private Socket connection;
	private Socket clientHeart;
	private Heartbeat heartbeat;
	private boolean connected = false; // this is set to false if Heartbeat fails

	private InputStream in;
	private OutputStream out;
	private BufferedReader reader;
	private PrintWriter writer;
	private int identifier;

// ======================================== CONSTRUCTOR ========================================
	public Client(Socket connection, int heartbeatcode) {
		this.identifier = heartbeatcode;
		this.connection = connection;
		new Thread(this).start();
	}

// ======================================== RUN-METHOD =========================================	
	@Override
	public void run() {
		heartbeat = new Heartbeat(this.identifier);
		try {
			in = connection.getInputStream();
			out = connection.getOutputStream();
			writer = new PrintWriter(out);
			reader = new BufferedReader(new InputStreamReader(in));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sendHeartbeatcode();
	}

// ======================================== METHODS ============================================
	private void sendHeartbeatcode() {
		writer.write(this.identifier + "/n");
		writer.flush();
	}

	public void disconnect() {
		writer.write("disconect/n");
		writer.flush();
	}

// ======================================== GET/SET METHODS ====================================
	public void setHeartbeat(Socket clientHeart) {
		this.clientHeart = clientHeart;
		heartbeat.setHeart(this.clientHeart);
	}

}
