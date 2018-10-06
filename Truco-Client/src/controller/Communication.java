package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Communication {

	private Socket socket;
	private BufferedReader reader;
	private PrintWriter writer;
	
	private Thread receiveThread = null;
	
	private boolean isRunning = true;
	
	public Communication() throws UnknownHostException, IOException {
		socket = new Socket(InetAddress.getByName("127.0.0.1"), 6789);
		reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		writer = new PrintWriter(socket.getOutputStream());
		
		startListeningMessages();
	}
	
	public void sendMessage(String message) {
		writer.println(message);
		writer.flush();
	}
	
	// Encerra a thread de recebimento de mensagem
	public void stopCommunication() {
		isRunning = false;
	}
	
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
						System.out.println(message);
					}
					
					socket.close();
				} catch (IOException ex) {
					
				}
			}
		};
	}
}
