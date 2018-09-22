import java.util.HashMap;

import controller.Chat;
import controller.Mesa;
import model.Jogador;

public class Main {

	static HashMap<Jogador, String> enderecoPorJogador;

	public static void main(String[] args) {

		enderecoPorJogador = new HashMap<>();

		Chat chat = new Chat();
		Thread threadChat = new Thread(chat);
		threadChat.start();

		Mesa mesa = new Mesa(chat);
		Thread threadMesa = new Thread(mesa);
		threadMesa.start();

	}
}
