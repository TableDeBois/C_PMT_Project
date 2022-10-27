package serveur;

import java.io.*;
import java.net.*;

public class Serveur implements Runnable {

	private ServerSocket listen_socket;

	// Cree un serveur TCP - objet de la classe ServerSocket
	public void ServeurCours(int port) throws IOException {
		listen_socket = new ServerSocket(port);
	}

	
	
	@Override
	public void run() {
		try {
			System.err.println("Lancement du serveur au port " + this.listen_socket.getLocalPort());
			while (true)
				new Thread(new ServiceMessages(listen_socket.accept())).start();
		} catch (IOException e) {
			try {
				this.listen_socket.close();
			} catch (IOException e1) {
			}
			System.err.println("Arrêt du serveur au port " + this.listen_socket.getLocalPort());
		}
		
	}

	// restituer les ressources --> finalize
		protected void finalize() throws Throwable {
			try {
				this.listen_socket.close();
			} catch (IOException e1) {
			}
		}
}
