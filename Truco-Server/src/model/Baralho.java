package model;

import java.util.Collections;
import java.util.Stack;

public class Baralho {

	private Stack<Carta> baralho = new Stack<>();

	public Baralho() {

		baralho.clear();
		for (Valor valor : Valor.values()) {

			for (Naipe naipe : Naipe.values()) {

				baralho.push(new Carta(valor, naipe));
			}
		}
	}

	public void embaralhar() {

		Collections.shuffle(baralho);
	};

	public Carta retirarCarta() {

		return baralho.pop();
	}

	@Override
	public String toString() {

		StringBuilder cartasDoBaralho = new StringBuilder();

		for (Carta carta : baralho) {

			cartasDoBaralho.append(carta);
			cartasDoBaralho.append("\n");
		}

		return cartasDoBaralho.toString();
	}
}
