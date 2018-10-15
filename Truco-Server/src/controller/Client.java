package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import javax.xml.ws.handler.MessageContext;

import model.Jogador;
import util.Messages;

public class Client implements Runnable {

	private Socket clientSocket;
	private PrintWriter writer;
	private BufferedReader reader;
	private boolean isRunning = true;
	private boolean isReMatch = true;
	private ArrayList<Jogador> chat;
	
	private String name;

	public ArrayList<Jogador> getChat() {
		return chat;
	}

	public void setChat(ArrayList<Jogador> chat) {
		this.chat = chat;
	}

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
		
	public void broadcastChat(String msg) {
		for(Jogador jogador : this.chat) {
			jogador.getClient().sendMessage(msg);
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
						this.name = name;
					} else {
						sendMessage(Messages.NOK);
					}
				} else if(message.equals(Messages.ROOM)) {
					ApplicationController.getInstance().logData("Recebendo uma solicitacao de sala");
					int num = ApplicationController.getInstance().addJogador(this.name);
					ApplicationController.getInstance().logData("Adicionei em uma nova sala. Faltam " + num);
					String res = String.valueOf(num);
					sendMessage(Messages.ROOM);
					sendMessage(res);
				} else if (message.equals(Messages.CANCEL_WAIT)) {
					ApplicationController.getInstance().removeJogador(this.name);
				} else if(message.equals(Messages.ROOMLIST)) {
					
				} else if(message.equals(Messages.DISCONNECT)) {
					endClient(false);
				} else if(message.equals(Messages.CHAT)){
					ApplicationController.getInstance().logData("Recebendo msg de chat");
					String msg = reader.readLine();
					String type = reader.readLine();
					
					ApplicationController.getInstance().logData("Li as duas strs");
					
					ApplicationController.getInstance().sendChatMessage(this.name, msg, type);
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
	
	public String getName() {
		return name;
	}
}
