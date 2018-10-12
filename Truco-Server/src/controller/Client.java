package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.xml.ws.handler.MessageContext;

import model.Jogador;
import util.Messages;

public class Client implements Runnable {

	private Socket clientSocket;
	private PrintWriter writer;
	private BufferedReader reader;
	private boolean isRunning = true;
	private boolean isReMatch = true;

	// Cuida de toda a comunicacao entre o servidor e o cliente. Cada accept gera uma nova instancia dessa classe
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
					// Se o cliente foi registrado no ClientMap com sucesso
					if(Server.getInstance().registerClient(name, this)) {
						sendMessage(Messages.ACK);
						// Adicione ele à lista de jogadores em espera
						Server.getInstance().addJogador(new Jogador(name, this));
						
						// Se 4 jogadores se registrarem, começar uma thread de Chat e de Mesa
						if(Server.getInstance().getJogadorList().size() == 4){
							
							ApplicationController.getInstance().logData(name + " registered. Table complete. Creating table...");
							Server.getInstance().broadcastEspera(Messages.PLAY);
							Server.getInstance().broadcastEspera("Sistema: Partida iniciando...");
							
							System.out.println("teste: " + Server.getInstance().getJogadorList().get(0).getNome());
							
							String aux = "One chat starting with players:";
							for (int i = 0; i < Server.getInstance().getJogadorList().size(); i++) {
								aux = aux + " " + Server.getInstance().getJogadorList().get(i).getNome() + ",";
							}
							ApplicationController.getInstance().logData(aux);
							Chat chat = new Chat(Server.getInstance().getJogadorList());
							Thread threadChat = new Thread(chat);
							threadChat.start();
							
//							Mesa mesa = new Mesa(chat);
//							Thread threadMesa = new Thread(mesa);
//							threadMesa.start();
							if(!isReMatch)
								Server.getInstance().getJogadorList().clear();
						}else{
							ApplicationController.getInstance().logData(name + " registered. Now waiting " + (4-Server.getInstance().getJogadorList().size()) + " players.");
							Server.getInstance().broadcastEspera(Messages.CHAT);
							Server.getInstance().broadcastEspera("Sistema: Esperando " + (4-Server.getInstance().getJogadorList().size()) + " jogadores se conectarem.");
						}
					} else {
						sendMessage(Messages.NOK);
					}
				} else if(message.equals(Messages.ROOMLIST)) {
					
				} else if(message.equals(Messages.DISCONNECT)) {
					endClient(false);
				} else if(message.equals(Messages.CHAT)){
					String msg = reader.readLine();
					Server.getInstance().broadcastEspera(msg);
				}
				
				//System.out.println(message);
			}
		} catch (IOException e) {
			System.out.println("Perdemos contato com o socket cliente");
			try {
				ApplicationController.getInstance().logData("Lost connection to client");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			endClient(false);
		}
	}
}
