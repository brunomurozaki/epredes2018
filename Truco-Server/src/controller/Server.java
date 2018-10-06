package controller;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.ClosedByInterruptException;
import java.util.ArrayList;

public class Server {

	private ServerSocket serverSocket;
	private ArrayList<Client> clientList;
	private Thread acceptThread = null;
	private static Server instance;
	private boolean keepAlive = true;
	
	public Server() throws IOException {
		serverSocket = new ServerSocket();
		clientList = new ArrayList<Client>();
	}
	
	public void stopServer() throws IOException {
		for(int i = 0; i < clientList.size(); i++) {
			clientList.get(i).endClient(true);
		}
		
		keepAlive = false;
		serverSocket.close();
	}
	
	public void startServer() throws IOException {
	
		if(acceptThread != null) {
			System.err.println("Thread de accept já iniciada");
			return;
		}
	
		serverSocket.bind(new InetSocketAddress("127.0.0.1", 6789));
		System.out.println("Socket binded!");
		
		acceptThread = new Thread("Accpet Thread") {
			@Override
			public void run() {
				super.run();
				
				try {
					
					// Recebo a nova conexao e instancio o novo client
					while(keepAlive) {
						Socket clientSock = serverSocket.accept();
						System.out.println("Client Accepted!");
						
						Client client = new Client(clientSock);
						clientList.add(client);
						
						// Inicio a nova thread do cliente separadamente
						Thread clientThread = new Thread(client);
						clientThread.start();
					}
				} catch (IOException e) {
					System.out.println("Server será encerrado");
				}
			}
		};
		
		acceptThread.start();
	}
	
	
	public ArrayList<Client> getClientList() {
		return clientList;
	}
	
	public void removeClient(Client client) {
		if(!this.clientList.remove(client)) {
			System.err.println("Cliente nao existia na lista");
		}
	}
	
	public static Server getInstance() throws IOException {
		if(instance == null) {
			instance = new Server();
		}
		return instance;
	}
}
