package client;

import java.io.BufferedReader;
import java.io.IOException;

public class ClientListener extends Thread{

	
	private BufferedReader sin;
	
	ClientListener(BufferedReader sin){
		this.sin=sin;
	}
	
	@Override
	public void run() {
		String line;
		try {
			line = sin.readLine();
			if(line.equals(null)) {
				
			}else {
				System.out.println(line);
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
