package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import util.Messages;

public class Communication {

	private Socket socket;
	private BufferedReader reader;
	private PrintWriter writer;
	private static Communication instance;
	
	private Thread receiveThread = null;
	
	private boolean isRunning = true;
	
	public Communication() throws UnknownHostException, IOException {
		socket = new Socket(InetAddress.getByName("127.0.0.1"), 6789);
		reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		writer = new PrintWriter(socket.getOutputStream());
		
		startListeningMessages();
	}
	
	// Envio mensagem de insercao de usuario no server
	public void registerUser(String name) {
		sendMessage(Messages.INSERT);
		sendMessage(name);
	}

	// Envio uma mensagem qualquer ao server
	public void sendMessage(String message) {
		writer.println(message);
		writer.flush();
	}
	
	// Encerra a thread de recebimento de mensagem
	public void stopCommunication() {
		sendMessage(Messages.DISCONNECT);
		isRunning = false;
	}
	
	// Escuta as mensagens vindas do servidor
	private void startListeningMessages() {

		if(receiveThread != null) {
			System.err.println("Thread de receber mensagens ja iniciada");
			return;
		}
		
		receiveThread = new Thread("ReceiveThread") {
			@Override
			public void run() {
				super.run();
				try {
					while(isRunning) {
						String message = reader.readLine();
						
						if(message.equals(Messages.INSERT)) {
							String res = reader.readLine();
							if(res.equals(Messages.ACK)) {
								ApplicationController.getInstance().successfulLogin();
							} else if(res.equals(Messages.NOK)) {
								ApplicationController.getInstance().failedLogin();
							} else {
								System.err.println("Protocolo invalido!");
							}
						}
						
						System.out.println(message);
					}
					
					socket.close();
				} catch (IOException ex) {
					
				}
			}
		};
		
		receiveThread.start();
	}
	
	public static Communication getInstance() throws UnknownHostException, IOException {
		if(instance == null) {
			instance = new Communication();
		}
		return instance;
	}
	
}
