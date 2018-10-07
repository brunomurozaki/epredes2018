package controller;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.ClosedByInterruptException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Server {

	private ServerSocket serverSocket;
	private ArrayList<Client> clientList;
	private HashMap<String, Client> clientMap;
	private Thread acceptThread = null;
	private static Server instance;
	private boolean keepAlive = true;
	
	public Server() throws IOException {
		serverSocket = new ServerSocket();
		clientList = new ArrayList<Client>();
		clientMap = new HashMap<String, Client>();
	}
	
	// Encerra o servidor e todas as suas threads
	public void stopServer() throws IOException {
		for(int i = 0; i < clientList.size(); i++) {
			clientList.get(i).endClient(true);
		}
		
		keepAlive = false;
		serverSocket.close();
	}
	
	// Registra o cliente. Se o nome ja existe, retorna falso
	public boolean registerClient(String name, Client client) {
		
		if(clientMap.containsKey(name))
			return false;
		
		clientMap.put(name, client);
		return true;
	}
	
	// Inicia o servidor e a thread de accept (nao eh a thread de mensagens do cliente)
	public void startServer() throws IOException {
	
		if(acceptThread != null) {
			System.err.println("Thread de accept j� iniciada");
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
					System.out.println("Server ser� encerrado");
				}
			}
		};
		
		acceptThread.start();
	}
	
	
	// Remove o client da lista de sockets e da lista de registro
	public void removeClient(Client client) {
		if(!this.clientList.remove(client)) {
			System.err.println("Cliente nao existia na lista");
		}
		
		Set<String> keys = clientMap.keySet();

		// N sei se funciona, mas acho que sim pq as instancias sao as mesmas
		for(String s : keys) {
			if(clientMap.get(s) == client) {
				clientMap.remove(s);
			}
		}
	}
	

	public ArrayList<Client> getClientList() {
		return clientList;
	}
	
	
	public static Server getInstance() throws IOException {
		if(instance == null) {
			instance = new Server();
		}
		return instance;
	}
}
