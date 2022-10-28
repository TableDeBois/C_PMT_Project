package serveur;

import java.io.IOException;

public class LauchServeur {

	public static void main(String[] args) {
		Serveur serv = new Serveur();
		try {
			serv.ServeurMessages(8080);
			serv.run();
		} catch (IOException e) {
			System.err.println("Lancement du serveur échoué");
			e.printStackTrace();
		}
		
	}
	
}
