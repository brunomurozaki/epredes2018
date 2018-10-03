import java.util.ArrayList;
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

//-------------------------SERVER-----------------------

public class Main {

	static HashMap<Jogador, String> enderecoPorJogador;

	public static void main(String[] args) throws IOException {

		enderecoPorJogador = new HashMap<>();
		BufferedReader inFromClient;
		String data = "";
		Socket connectionSocket;
		DataOutputStream outToClient;
		ArrayList<Jogador> jogadores_esperando = new ArrayList<>();

		// Cria um socket para receber as requisi��es
		ServerSocket welcomeSocket = new ServerSocket(666, 1, InetAddress.getLocalHost());
		System.out.println("Rodando servidor:\n" + "\tHost = " + welcomeSocket.getInetAddress().getHostAddress()
				+ "\n\tPorta = " + welcomeSocket.getLocalPort());

		while (true) {

			for (int i = 0; i < 4; i++) {
				// Aceita a conex�o com algum cliente, cria um socket pra essa
				// conex�o
				// e os canais de out e input stream
				connectionSocket = welcomeSocket.accept();
				inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
				outToClient = new DataOutputStream(connectionSocket.getOutputStream());
				System.out.println("\nNova conex�o de " + connectionSocket.getInetAddress());

				data = inFromClient.readLine();
				System.out.println("recebeu " + data + " do cliente");

				Jogador jogador = new Jogador(data);
				jogadores_esperando.add(jogador);
				outToClient.writeBytes(data+", voc� foi conectado ao servidor");
			}

		}

		// Chat chat = new Chat();
		// Thread threadChat = new Thread(chat);
		// threadChat.start();
		//
		// Mesa mesa = new Mesa(chat);
		// Thread threadMesa = new Thread(mesa);
		// threadMesa.start();
	}
}
