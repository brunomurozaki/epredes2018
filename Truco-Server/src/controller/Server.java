package controller;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import model.Jogador;

public class Server {

	private ServerSocket serverSocket;
	private ArrayList<Client> clientList;
	private ArrayList<Jogador> listaEsperaJogadores;
	private HashMap<String, Client> clientMap;
	private Thread acceptThread = null;
	private static Server instance;
	private boolean keepAlive = true;
	
	public Server() throws IOException {
		serverSocket = new ServerSocket();
		clientList = new ArrayList<Client>();
		clientMap = new HashMap<String, Client>();
		listaEsperaJogadores = new ArrayList<Jogador>();
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
	public boolean startServer() throws IOException {
	
		if(acceptThread != null) {
			ApplicationController.getInstance().logData("Thread de accept já iniciada");
			return false;
		}
	
		serverSocket.bind(new InetSocketAddress("127.0.0.1", 6789));
		ApplicationController.getInstance().logData("Socket binded!");
		
		acceptThread = new Thread("Accept Thread") {
			@Override
			public void run() {
				super.run();
				
				try {
					
					// Recebo a nova conexao e instancio o novo client
					while(keepAlive) {
						Socket clientSock = serverSocket.accept();
						ApplicationController.getInstance().logData("Client Accepted!");
						
						Client client = new Client(clientSock);
						clientList.add(client);
						
						// Inicio a nova thread do cliente separadamente
						Thread clientThread = new Thread(client);
						clientThread.start();
						
					}
				} catch (IOException e) {
					try {
						ApplicationController.getInstance().logData("Server será encerrado");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		};
		
		acceptThread.start();
		return true;
	}
	
	
	// Remove o client da lista de sockets e da lista de registro e da lista de espera
	public void removeClient(Client client) throws IOException {
		if(!this.clientList.remove(client)) {
			ApplicationController.getInstance().logData("Cliente nao existia na lista");
		}
		
		Set<String> keys = clientMap.keySet();

		// N sei se funciona, mas acho que sim pq as instancias sao as mesmas
		for(String s : keys) {
			if(clientMap.get(s) == client) {
				clientMap.remove(s);
			}
		}
		
		for (int i = 0; i < listaEsperaJogadores.size(); i++) {
			if(listaEsperaJogadores.get(i).getClient().equals(client))
				listaEsperaJogadores.remove(i);
		}
	}
	
	public void broadcastEspera(String msg){
		for(Jogador jogador : listaEsperaJogadores){
			jogador.getClient().sendMessage(msg);
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
	
	public ArrayList<Jogador> getJogadorList(){
		return listaEsperaJogadores;
	}
	
	public void addJogador(Jogador jogador){
		listaEsperaJogadores.add(jogador);
	}
}
