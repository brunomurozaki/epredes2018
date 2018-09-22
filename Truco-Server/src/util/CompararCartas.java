package util;

import java.util.Comparator;

import model.Carta;

public class CompararCartas implements Comparator<Carta> {

	Carta vira;

	public CompararCartas(Carta vira) {

		this.vira = vira;
	}

	@Override
	public int compare(Carta primeiraCarta, Carta segundaCarta) {

		int rankingVira = vira.getValor().getRanking();

		// armazena o ranking da manilha, verificando o caso em que a manilha eh quatro
		int rankingManilha = (rankingVira == 10) ? 1 : rankingVira + 1;

		if (rankingManilha == primeiraCarta.getValor().getRanking()) {
			// primeira eh manilha

			if (rankingManilha == segundaCarta.getValor().getRanking()) {
				// as duas sao manilhas

				if (primeiraCarta.getNaipe().getRanking() > segundaCarta.getNaipe().getRanking()) {
					// primeiro naipe eh maior
					return -1;

				} else {
					// segundo naipe eh maior
					return 1;
				}
			} else {
				// somente a primeira eh manilha
				return -1;
			}
		}

		if (rankingManilha == segundaCarta.getValor().getRanking()) {
			// verifica se somente a segunda eh manilha
			return 1;
		}

		// cartas nao manilhas
		int ranking = primeiraCarta.getValor().getRanking();
		int rankingOutraCarta = segundaCarta.getValor().getRanking();

		if (ranking > rankingOutraCarta)
			return -1;
		if (ranking < rankingOutraCarta)
			return 1;
		else
			return 0;
	}
}
