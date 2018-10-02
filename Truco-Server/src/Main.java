import java.util.HashMap;

import controller.Chat;
import controller.Mesa;
import model.Jogador;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;



public class Main {
	

	static HashMap<Jogador, String> enderecoPorJogador;

	public static void main(String[] args) throws IOException {

		enderecoPorJogador = new HashMap<>();
		
		// Cria um socket para receber as requisições
		ServerSocket welcomeSocket = new ServerSocket(666, 1, InetAddress.getLocalHost());
		System.out.println("Rodando servidor:\n" + "\tHost = " +
		welcomeSocket.getInetAddress().getHostAddress() + 
				"\n\tPorta = " + welcomeSocket.getLocalPort());
		
		BufferedReader inFromClient;
		String data = "";
		
        while (true) {
        	
        	//Aceita a conexão com algum cliente e cria um socket pra essa conexão
        	Socket connectionSocket = welcomeSocket.accept();
            System.out.println("\nNova conexão de " + connectionSocket.getInetAddress());
            
            
            
            // Cadeias de entrada e saída ligadas ao socket
        	inFromClient = new BufferedReader(
        			new InputStreamReader(connectionSocket.getInputStream()));
        	DataOutputStream outToClient = 
        			new DataOutputStream(connectionSocket.getOutputStream());
        	
        	data = inFromClient.readLine();
            System.out.println("\r\nMensagem de " + connectionSocket.getInetAddress() + ": " + data);
        }
        
        
//		Chat chat = new Chat();
//		Thread threadChat = new Thread(chat);
//		threadChat.start();
//
//		Mesa mesa = new Mesa(chat);
//		Thread threadMesa = new Thread(mesa);
//		threadMesa.start();

	}
}
