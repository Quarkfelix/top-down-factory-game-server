package clienthandling;

import java.net.Socket;

import infrastructure.Main;
import infrastructure.Server;

public class Heartbeat implements Runnable {
	private Socket clientHeart;
	private int pulserate = 1000; //ms
	private int identifier;
	
	private boolean pulsemissed = false; //true if no puls read. on second missed pulse client gets notified
	
// ======================================== CONSTRUCTOR ========================================
	public Heartbeat(int identifier) {
		this.identifier = identifier;
		new Thread(this).start();
	}
// ======================================== RUN-METHOD =========================================	
	@Override
	public void run() {
		//5 seconds time to connect heartbeat after client otherwise kill connection
		for (int i = 0; i < 10; i++) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(clientHeart != null) {
				i = 10; //break out
			}
		}
		//client did not try to connect for heartbeat
		if(clientHeart == null) {
			disconnect();
		}
		
		while(true) {
			try {
				Thread.sleep(pulserate);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sendPulse();
			checkPulse();
		}
	}
// ======================================== METHODS ============================================
	public void setHeart(Socket clientHeart) {
		this.clientHeart = clientHeart;
	}
	
	private void sendPulse() {
		
	}
	
	private void checkPulse() {
		
	}
	
	private void disconnect() {
		Server.clientHandler.getClient(identifier).disconnect();
	}
// ======================================== GET/SET METHODS ====================================
	
}
