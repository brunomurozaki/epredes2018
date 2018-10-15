package model;

import java.util.ArrayList;

import controller.Client;

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
		
		//cartas.remove(carta);
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
