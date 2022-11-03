package serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServiceMessages implements Runnable{

	private static List<ServiceMessages> clients = new ArrayList<>(); 
	private BufferedReader in;
	private PrintWriter out;
	private Socket client;
	private static int cpt= 1;
	private final int numero;
	private User user;

	ServiceMessages(Socket socket){
		this.numero=cpt++;
		try {
			this.client=socket;
			this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.out =  new PrintWriter(client.getOutputStream(), true);
			
			this.user = new User(in.readLine());
			ServiceMessages.clients.add(this);
			sayAll("SERVER : "+ this.user.getPseudo()+ " vient de se connecter !");
		} catch (IOException e) {
			e.printStackTrace();
			closeAll(socket, in, out);
		}
		
	}
	
	@Override
	public void run() {
		String message;
		System.err.println("Nouvelle connection !");
		while (client.isConnected()) {
			try {
				message = this.in.readLine();
				int points = message.indexOf(":");
				String m = message.substring(points+2);
				m=m.substring(0,3);
				if(m.equals("mp ")) {
					privateMessage(message);
				}else {
					sayAll(message);
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				closeAll(client, in, out);
				break;
			}
		}
		
	}
	
	public void privateMessage(String msg) {
		int points = msg.indexOf(":");
		String whisp = msg.substring(points+5);
		int space = whisp.indexOf(" ");
		String username;
		if (space != -1) {
			username = whisp.substring(0, space);
			whisp = whisp.substring(space+1);
			for (ServiceMessages service : clients) {
				try {
					if(service.user.getPseudo().equals(username)) {
						service.out.println(msg.substring(0, points-1) + " vous dit : " + whisp);
						service.out.flush();
					}
				} catch (Exception e) {
					closeAll(client, in, out);
				}
			}
		}else {
			sayAll(msg);
		}
		
	}
	
	protected void finalize() throws Throwable {
		client.close();
	}
	
	
	public void sayAll(String msg) {
		for (ServiceMessages service : clients) {
			try {
				if(!service.user.getPseudo().equals(user.getPseudo())) {
					service.out.println(msg);
					service.out.flush();
				}
			} catch(Exception e) {
				closeAll(client, in, out);
			}
		}
	}
	
	
	public void removeService() {
		clients.remove(this);
		sayAll("SERVER : " + user.getPseudo()+" a quitté le chat !");
	}
	
	public void closeAll(Socket socket,BufferedReader in, PrintWriter out) {
		removeService();
		try {
			if(in!=null) {
				in.close();
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
	
}
