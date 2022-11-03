package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientListener extends Thread{

	
	private BufferedReader sin;
	private Socket socket;
	private PrintWriter out;
	
	ClientListener(Socket socket, BufferedReader sin, PrintWriter out){
		this.sin=sin;
		this.socket=socket;
		this.out= out;
		
	}
	
	@Override
	public void run() {
		String line;
		while(socket.isConnected()) {
			try {
				line= sin.readLine();
				System.out.println(line);
			}catch(IOException e) {
				closeAll(socket,sin,out);
			}
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
	
}
