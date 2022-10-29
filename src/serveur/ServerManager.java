package serveur;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Map;

public class ServerManager extends Thread{

	private static Map<Socket,User> clients;
	
	
	ServerManager() {
	}
	
	ServerManager(Map<Socket,User> liste){
		ServerManager.clients = liste;
	}
	
	
	@Override
	public void run() {
		
	}
}
