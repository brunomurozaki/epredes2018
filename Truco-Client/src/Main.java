import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;

public class Main {

	public static void main(String[] args) throws IOException {

		// le msg do cliente
		BufferedReader inFromClient = new BufferedReader(new InputStreamReader(System.in));
		String data = inFromClient.readLine();

		// Cria socket para conexão com o servidor
		InetAddress serverAddress = InetAddress.getByName("172.115.0.178");
		int serverPort = 666;
		Socket clientSocket = new Socket(serverAddress, serverPort);

		System.out.println("Conectado ao servidor: " + clientSocket.getInetAddress());

		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		outToServer.writeBytes(data);

		// BufferedReader inFromServer = new BufferedReader(
		// new InputStreamReader(clientSocket.getInputStream()));
		clientSocket.close();

	}
}
