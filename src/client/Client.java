package client;

import java.io.*;
import java.net.*;

public class Client {
	private static int PORT;
	private static String HOST; 
	
	public static void main(String[] args) throws IOException {
		
		//Partie BTTP a déterminer
		BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in));			
		// connexion
		System.out.print("Tapez l'url de connexion (par défaut BTTP:localhost:1234)");
		String url = args[0];
		try {validBTTP(url);} 
		catch (Exception e) {
				System.out.println(e.getMessage());
				return;
		}
		Socket socket = null;		
		try {
			socket = new Socket(HOST, PORT);
			BufferedReader sin = new BufferedReader (new InputStreamReader(socket.getInputStream ( )));
			PrintWriter sout = new PrintWriter (socket.getOutputStream ( ), true);
			// Informe l'utilisateur de la connection
			System.out.println("Connect� au serveur " + socket.getInetAddress() + ":"+ socket.getPort());
			
			String line;
			
			// protocole BTTP jusqu'� saisie de "0" ou fermeture cot� service
			do {// r�ception et affichage de la question provenant du service
				line = sin.readLine();
				
				if (line == null) break; // fermeture par le service
				System.out.println(line);
				
				// prompt d'invite � la saisie
				
				System.out.print("->");
				line = clavier.readLine();
				
				if (line.equals("")) break; // fermeture par le client
				// envoie au service de la r�ponse saisie au clavier
				sout.println(line);
			} while (true);
			socket.close();
		}
		catch (IOException e) { System.err.println("Fin du service"); }
		// Refermer dans tous les cas la socket
		try { if (socket != null) socket.close(); } 
		catch (IOException e2) { ; }		
	}

	private static void validBTTP(String url) throws Exception { 
		String[] elts = url.split(":");
		if ((elts.length != 3) || 
				(!elts[0].equals("BTTP")) ||
				(!isNumeric(elts[2]))){
			throw new Exception("L'URL ne respecte pas le protocole BTTP");
		}
		HOST = elts[1]; 
		PORT =  Integer.parseInt(elts[2]);
	}

	private static boolean isNumeric(String string) {
		try {
			Integer.parseInt(string);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

}
