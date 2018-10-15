package model;

import java.io.IOException;
import java.util.ArrayList;

import controller.ApplicationController;
import controller.Client;
import util.Messages;

public class Jogador {

	private String nome;
	private ArrayList<Carta> cartas;
	private Client client;

	public Jogador(String nome, Client client) {
		this.nome = nome;
		cartas = new ArrayList<>();
		this.client = client;
	}

	public void recebeCarta(Carta carta) {
		cartas.add(carta);
		
		try {
			ApplicationController.getInstance().logData("Enviando a mensagem de draw");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Avisa o cliente da carta
		client.sendMessage(Messages.DRAW_CARD);
		client.sendMessage(carta.translateCard());
	}

	public String getNome() {

		return nome;
	}

	@Override
	public String toString() {

		return nome + ": " + cartas;
	}

	public String getCartas() {

		return cartas.toString();
	}

	public Carta removeCarta(Carta carta) {
		return cartas.remove(0);
	}
	
	public void entregarCartas() {
		
		cartas.clear();
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
	
	public void sendMessage(String message) {
		client.sendMessage(message);
	}
}
