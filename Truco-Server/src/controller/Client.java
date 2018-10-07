package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.xml.ws.handler.MessageContext;

import util.Messages;

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
	
	// Envia mensagem ao cliente
	public void sendMessage(String message) {
		this.writer.println(message);
		this.writer.flush();
	}
	
	// Remove o cliente da lista do server e encerra a thread
	public void endClient(boolean isServerTermination) {
		try {
			// Tem esse if pois quando o server eh totalmente encerrado, nao faz diferenca remover da lista de sockets
			// e atrapalha o encerramento das threads.
			if(!isServerTermination) {
				Server.getInstance().removeClient(this);
			}
			isRunning = false;
			clientSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
			
	@Override
	public void run() {
		try {
			while(isRunning) {
				String message = reader.readLine();
				
				if(message.equals(Messages.INSERT)) {
					String name = reader.readLine();
					System.out.println("Nome: " + name);
					sendMessage(Messages.INSERT);
					if(Server.getInstance().registerClient(name, this)) {
						sendMessage(Messages.ACK);
					} else {
						sendMessage(Messages.NOK);
					}
				} else if(message.equals(Messages.ROOMLIST)) {
					
				} else if(message.equals(Messages.DISCONNECT)) {
					endClient(false);
				} 
				
				//System.out.println(message);
			}
		} catch (IOException e) {
			System.out.println("Perdemos contato com o socket cliente");
			endClient(false);
		}
	}
}
