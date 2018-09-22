package model;

import java.util.ArrayList;

public class Jogador {

	private String nome;
	private ArrayList<Carta> cartas;

	public Jogador(String nome) {

		this.nome = nome;
		cartas = new ArrayList<>();
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
}
