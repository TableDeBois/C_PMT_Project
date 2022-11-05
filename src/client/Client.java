package client;

import java.io.*;
import java.net.*;

public class Client {
	private static int PORT;
	private static String HOST; 
	
	private Socket socket;
	private String pseudo;
	private BufferedReader sin;
	private PrintWriter out;
	private BufferedReader clavier;
	
		
	public void clientWriter(ClientListener listener) {
		try {
			out.println(pseudo);
			out.flush();
			
			while(socket.isConnected()) {
				String msg = clavier.readLine();
				
				out.println(pseudo + " : " + msg);
				out.flush();
			}
		}catch(IOException e) {
			closeAll(socket, sin, out);
		}
	}
	
	public void closeAll(Socket socket, BufferedReader sin, PrintWriter out){
		try {
			if(sin!=null) {
				sin.close();
			}
			if(out!=null) {
				out.close();
			}
			if (socket!=null) {
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	
	Client(Socket socket, String name, BufferedReader clavier){
		try {
			this.socket=socket;
			this.sin = new BufferedReader (new InputStreamReader(socket.getInputStream ( )));
			this.clavier=clavier;
			this.out =  new PrintWriter (socket.getOutputStream ( ), true);
			this.pseudo = name;
		}catch(IOException e) {
			closeAll(socket, sin, out);
		}
	}
	
	public static void main(String[] args) throws IOException {
		
		//Partie BTTP a déterminer
		BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in));			
		// connexion
		//System.out.print("Tapez l'url de connexion (par défaut BTTP:localhost:1234)");
		String url = "BTTP:localhost:8080";
		//String url = clavier.readLine();
		//String url = args[0];
		try {validBTTP(url);} 
		catch (Exception e) {
				System.out.println(e.getMessage());
				return;
		}
		Socket socket = null;
		
		try {
			System.out.println("Bienvenue dans le chat !");
			System.out.println("Tapez 'exit-chat' pour quitter le chat");
			System.out.println("Tapez 'mp [pseudo] [message]' envoyer un message privé");
			System.out.println("Entrez votre pseudo : ");
			String name = clavier.readLine();
			socket = new Socket(HOST, PORT);
			Client client = new Client(socket,name,clavier);
			
			ClientListener listener = new ClientListener(client.socket,client.sin,client.out);
			listener.start();
			client.clientWriter(listener);
		}
		catch (IOException e) { System.err.println("Fin du service"); }
		// Refermer dans tous les cas la socket
		try { if (socket != null) socket.close(); } 
		catch (IOException e2) { e2.printStackTrace();; }		
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
