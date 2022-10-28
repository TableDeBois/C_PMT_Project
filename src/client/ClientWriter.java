package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class ClientWriter extends Thread {

	private PrintWriter sout;
	private BufferedReader clavier;
	
	ClientWriter(PrintWriter out, BufferedReader clav){
		this.sout=out;
		this.clavier = clav;
	}
	
	
	@Override
	public void run() {
		
		String line;
		try {
			line = clavier.readLine();
			if (line.equals("exit-chat")) {
				this.interrupt();
			}else if(line.equals("")){
				
			}else {
				sout.println(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
