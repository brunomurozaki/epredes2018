package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import controller.ApplicationController;
import controller.Client;
import util.Messages;

public class Jogador {

	private String nome;
	private ArrayList<Carta> cartas;
	private HashMap<String, Carta> cartasMap;
	private Client client;

	public Jogador(String nome, Client client) {
		this.nome = nome;
		cartas = new ArrayList<>();
		cartasMap = new HashMap<>();
		this.client = client;
	}
	
	public void sendTurn(String name) {
		sendMessage(Messages.START_ROUND);
		sendMessage(name);
	}

	public void recebeCarta(Carta carta) {
		cartas.add(carta);
		cartasMap.put(carta.translateCard(), carta);
		
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

	public Carta removeCarta(String carta) {
		Carta c = cartasMap.get(carta);
		cartas.remove(c);
		cartasMap.remove(carta);
		return c;
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
