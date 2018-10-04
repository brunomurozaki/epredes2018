import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;

//-------------------------CLIENT-----------------------

public class MainClient {

	public static void main(String[] args) throws IOException {

		BufferedReader inFromClient;
		String data;
		InetAddress serverAddress = InetAddress.getByName("127.0.0.1");
		int serverPort = 666;
		Socket clientSocket;
		DataOutputStream outToServer;
		BufferedReader inFromServer;

		// É pedido que o usuário nos informe o seu nome para a partida
		System.out.println("Digite o seu nome/apelido para a partida:");
		inFromClient = new BufferedReader(new InputStreamReader(System.in));
		data = inFromClient.readLine();

		// Cria socket conectado ao servidor, e os streams para enviar dados
		// para e receber dados do servidor
		clientSocket = new Socket(serverAddress, serverPort);
		outToServer = new DataOutputStream(clientSocket.getOutputStream());
		inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

		System.out.println("Conectado ao servidor: " + clientSocket.getInetAddress());

		// Enviar nome do jogador ao servidor para que o jogador seja alocado a
		// uma mesa
		outToServer.writeBytes(data + "\n");

		// A partir daí o entra no estado de receber e enviar mensagens ao servidor
		while (!(data = inFromServer.readLine()).equals("disconnect")) {
			
			System.out.println(data);
			
			if((data = inFromClient.readLine()).equals("sair")) break;
		}

		clientSocket.close();
	}
}
