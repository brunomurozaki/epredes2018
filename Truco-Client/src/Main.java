import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;

//-------------------------CLIENT-----------------------

public class Main {

	public static void main(String[] args) throws IOException {

		System.out.println("Digite o seu nome/apelido:");
		BufferedReader inFromClient = new BufferedReader(new InputStreamReader(System.in));
		String data;
		data = inFromClient.readLine();

		// Cria socket para conexão com o servidor
		InetAddress serverAddress = InetAddress.getByName("192.168.0.105");
		int serverPort = 666;
		Socket clientSocket = new Socket(serverAddress, serverPort);
		System.out.println("Conectando ao servidor: " + clientSocket.getInetAddress());

		// Enviar nome do jogador ao servidor
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		outToServer.writeBytes(data);
		System.out.println("enviou " + data + " para o servidor");

		// clientSocket.close();

		// BufferedReader inFromServer = new BufferedReader(
		// new InputStreamReader(clientSocket.getInputStream()));

	}
}
