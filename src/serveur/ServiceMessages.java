package serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServiceMessages implements Runnable{

	private static List<Socket> clients = new ArrayList<Socket>(); 
	private final Socket client;
	private static int cpt= 1;
	private final int numero;

	ServiceMessages(Socket socket){
		this.client=socket;
		this.clients.add(socket);
		this.numero=cpt++;
	}
	
	@Override
	public void run() {
		System.out.println("*********Connexion "+this.numero+" démarrée");
		String reponse = null;
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);

			//mettre en place système de chatbox
			
			out.println("Bonjour votre nom ?");
			out.println("write 'exit-chat' to quit chat");
			String nom = in.readLine();
//			out.println("Tapez le num�ro de cours ");
//			int noCours = Integer.parseInt(in.readLine());
//			out.println("Tapez le nombre de places souhait�es ");
//			int nbrePlaces = Integer.parseInt(in.readLine());

			
		} catch (IOException e) {
			// Fin du service d'inversion
			System.out.println("*********Connexion "+this.numero+" terminée");
		}

		try {
			client.close();
		} catch (IOException e2) {
		}
	}
	
	protected void finalize() throws Throwable {
		client.close();
	}
	
	
}
