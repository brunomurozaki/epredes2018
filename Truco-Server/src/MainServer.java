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

public class MainServer {

	// Envia a mesma mensagem para uma lista de jogadores
	public static void broadcast_all(ArrayList<Jogador> lista_jogadores, String msg) throws IOException {

		for (int i = 0; i < lista_jogadores.size(); i++) {
			lista_jogadores.get(i).getOutToClient().writeBytes(msg);
		}

	}

	static HashMap<Jogador, String> enderecoPorJogador;

	public static void main(String[] args) throws IOException {

		enderecoPorJogador = new HashMap<>();
		BufferedReader inFromClient;
		DataOutputStream outToClient;
		String data = "";
		Socket connectionSocket;
		ArrayList<Jogador> jogadores_esperando = new ArrayList<>();

		// Cria um socket para receber as requisições
		ServerSocket welcomeSocket = new ServerSocket(666, 1, InetAddress.getByName("127.0.0.1"));
		System.out.println("Rodando servidor:\n" + "\tHost = " + welcomeSocket.getInetAddress().getHostAddress()
				+ "\n\tPorta = " + welcomeSocket.getLocalPort());

		while (true) {

			for (int i = 0; i < 4; i++) {
				// Aceita a conexão com algum cliente, cria um socket pra essa
				// conexão
				// e os canais de out e input stream
				connectionSocket = welcomeSocket.accept();
				inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
				outToClient = new DataOutputStream(connectionSocket.getOutputStream());
				System.out.println("\nNova conexão de " + connectionSocket.getInetAddress());

				data = inFromClient.readLine();
				System.out.println("recebeu " + data + " do cliente\n");

				// Cria um jogador com o nome dado pelo cliente, a socket para
				// ele criada pelo servidor, e com os streams para enviar e
				// receber dados desse cliente
				Jogador jogador = new Jogador(data, connectionSocket, outToClient, inFromClient);

				// Adiciona o jogador na lista de espera
				jogadores_esperando.add(jogador);

				if (jogadores_esperando.size() < 4)
					System.out.println("Mais um jogador, agora " + jogadores_esperando.size() + " esperando.");
				else
					System.out.println("Mais um jogador, agora a mesa está completa.");

				// Se depois de adicionar o jogador na lista de espera, essa
				// continuar com menos que 4 jogadores, informe ao jogador que
				// ele terá que esperar os outros entrarem
				if (jogadores_esperando.size() < 4)
					outToClient.writeBytes(data + ", você terá que esperar " + (4 - jogadores_esperando.size())
							+ " jogadores entrarem na mesa\n");
				else
					outToClient.writeBytes("Partida iniciando...\n");
			}
			
			System.out.println("Montando mesa.");

			// Se saiu do for, é pq 4 jogadores se conectaram ao servidor

			// Envia mensagem para todos os jogadores
			broadcast_all(jogadores_esperando, "Partida iniciando...\n");

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
