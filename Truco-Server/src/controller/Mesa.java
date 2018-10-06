package controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import model.Baralho;
import model.Carta;
import model.Jogador;

public class Mesa implements Runnable {

	private Baralho baralho;
	private Queue<Jogador> jogadores;
	private Carta vira;
	private int pontuacaoJogoTime1, pontuacaoJogoTime2, pontuacaoMaoTime1, pontuacaoMaoTime2;
	private int mao, rodada, premioMao;
	private HashMap<Jogador, Integer> jogadorPorTime;
	private static final int TIME1 = 1, TIME2 = 2;
	private static final int MAIOR = 1, MENOR = -1, MELOU = 0;
	private static Chat chat;

	public Mesa(Chat chat, Jogador jogador1, Jogador jogador2, Jogador jogador3, Jogador jogador4) {

		jogadores = new LinkedList<>();
		jogadores.add(jogador1);
		jogadores.add(jogador2);
		jogadores.add(jogador3);
		jogadores.add(jogador4);

		jogadorPorTime = new HashMap<>();
		jogadorPorTime.put(jogador1, TIME1);
		jogadorPorTime.put(jogador2, TIME2);
		jogadorPorTime.put(jogador3, TIME1);
		jogadorPorTime.put(jogador4, TIME2);

		mao = 0;
	}

	public void comecarJogo() {

		pontuacaoJogoTime1 = 0;
		pontuacaoJogoTime2 = 0;

		while (pontuacaoJogoTime1 < 12 && pontuacaoJogoTime2 < 12) {

			baralho = new Baralho();
			baralho.embaralhar();

			comecarMao();
		}

		if (pontuacaoJogoTime1 == 12)
			System.out.println("Time 1 ganhou!");
		else
			System.out.println("Time 2 ganhou!");
	}

	private void comecarMao() {

		mao++;
		premioMao = 1;

		distribuirCartas();

		pontuacaoMaoTime1 = 0;
		pontuacaoMaoTime2 = 0;

		System.out.println(mao + "ª mão | vale " + premioMao + " ponto | vira: " + vira + " \n");

		rodada = 0;

		while (pontuacaoMaoTime1 < 2 && pontuacaoMaoTime2 < 2) {

			rodada++;
			System.out.println("Rodada " + rodada);
			comecarRodada();
		}

		if (pontuacaoMaoTime1 == 2)
			pontuacaoJogoTime1 += premioMao;
		else
			pontuacaoJogoTime2 += premioMao;

		// remove cartas restantes
		for (Jogador jogador : jogadores) {
			jogador.entregarCartas();
		}
	}

	private void comecarRodada() {

		Jogador jogadorMaiorCarta = null, proximoJogador = null;
		Carta maiorCarta = null, tempCarta = null;
		boolean melado = false;

		for (int i = 0; i < jogadores.size(); i++) {

			// vez do jogador
			proximoJogador = proximoJogador();
			System.out.println("Vez do " + proximoJogador.getNome());
			System.out.println(proximoJogador.getCartas() + "\n");
			tempCarta = proximoJogador.removeCarta(null);

			if (maiorCarta == null) {
				maiorCarta = tempCarta;
				jogadorMaiorCarta = proximoJogador;
			} else {
				int resultado = compararCarta(tempCarta, maiorCarta);

				if (resultado == MENOR) {
					melado = false;
					maiorCarta = tempCarta;
					jogadorMaiorCarta = proximoJogador;
				} else if (resultado == MELOU) {
					melado = true;
					jogadorMaiorCarta = proximoJogador;
				}
			}
		}

		if (melado) {
			pontuacaoMaoTime1++;
			pontuacaoMaoTime2++;
		} else {
			if (jogadorPorTime.get(jogadorMaiorCarta) == TIME1) {
				pontuacaoMaoTime1++;
			} else {
				pontuacaoMaoTime2++;
			}
		}

		System.out.println(melado + " - " + maiorCarta);
	}

	public int compararCarta(Carta primeiraCarta, Carta segundaCarta) {

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

	private void distribuirCartas() {

		// distribui tres cartas para cada jogador
		for (int i = 0; i < 3; i++) {

			for (Jogador jogador : jogadores) {
				jogador.recebeCarta(baralho.retirarCarta());
			}
		}

		// vira ultima carta do baralho
		vira = baralho.retirarCarta();
	}

	// retorna proximo jogador da mesa
	private Jogador proximoJogador() {

		Jogador proximoJogador = jogadores.poll();
		jogadores.add(proximoJogador);

		return proximoJogador;
	}

	@Override
	public void run() {

		comecarJogo();
	}
}
