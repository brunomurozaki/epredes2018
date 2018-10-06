package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import controller.Server;

public class Client implements Runnable {

	private Socket clientSocket;
	private PrintWriter writer;
	private BufferedReader reader;
	private boolean isRunning = true;
	
	public Client(Socket clientSocket) throws IOException {
		this.clientSocket = clientSocket;
		this.writer = new PrintWriter(this.clientSocket.getOutputStream());
		this.reader = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
	}
	
	public void sendMessage(String message) {
		this.writer.println(message);
		this.writer.flush();
	}
	
	@Override
	public void run() {
		try {
			while(isRunning) {
				String message = reader.readLine();
				
				if(message.equals("endConnection")) {
					isRunning = false;
					Server.getInstance().removeClient(this);
				} 
				
				System.out.println(message);
			}
			
			clientSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
