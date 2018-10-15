package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import model.Baralho;
import model.Carta;
import model.Jogador;
import util.Messages;

public class Mesa implements Runnable {

	private Baralho baralho;
	private ArrayList<Jogador> jogadores;
	private Carta vira;
	private int pontuacaoJogoTime1, pontuacaoJogoTime2, pontuacaoMaoTime1, pontuacaoMaoTime2;
	private int mao, rodada, premioMao;
	private HashMap<Jogador, Integer> jogadorPorTime;
	private static final int TIME1 = 1, TIME2 = 2;
	private static final int MAIOR = 1, MENOR = -1, MELOU = 0;
	private static final int MAX_MESA = 4;
	private int currentPlayer = 0;
	private Jogador jogadorCorrente;

	public Mesa() {
		jogadores = new ArrayList<>();
		jogadorPorTime = new HashMap<>();
		mao = 0;
	}
	
	public boolean hasJogador(String name) {
		for(Jogador j : jogadores) {
			if(j.getNome().equals(name))
				return true;
		}
		
		return false;
	}
	
	public Jogador getJogadorByName(String name) {
		for(Jogador j : jogadores) {
			if(j.getNome().equals(name))
				return j;
		}
		
		return null;
	}
	
	public void removeJogador(String nome) {
		for(int i = 0; i < jogadores.size(); i++) {
			if(jogadores.get(i).getNome().equals(nome))
				jogadores.remove(i);
		}
		broadcastWaitingMessage();
	}
	
	private void broadcastChangeTurn(String name) {
		for(Jogador j : jogadores) {
			j.sendTurn(name);
		}
	}
	
	public void broadcastChatMessage(String message) {
		for(Jogador j : jogadores) {
			j.sendMessage(Messages.CHAT);
			j.sendMessage(message);
			j.sendMessage(Messages.PUBLIC);
		}
	}
	
	public void broadcastWaitingMessage() {
		int num = getNumPlayers();
		
		if(num == 0) {
			String names = "";
			
			for(Jogador j : jogadores) {
				names += j.getNome() + ";";
			}
			
			for(Jogador j : jogadores) {
				j.sendMessage(Messages.START_GAME);
				j.sendMessage(names);
				
				j.sendMessage(Messages.CHAT);
				j.sendMessage("SERVER - INICIANDO JOGO");
				j.sendMessage(Messages.PUBLIC);
			}	
		} else {
			for(Jogador j : jogadores) {
				j.sendMessage(Messages.ROOM);
				j.sendMessage(String.valueOf(getNumPlayers()));
			}	
		}
	}
	
	public boolean mesaFull() {
		return jogadores.size() == MAX_MESA ? true : false;
	}
	
	public int getNumPlayers() {
		return MAX_MESA - jogadores.size();
	}
	
	public boolean addJogador(Jogador jogador) {
		if(jogadores.size() == MAX_MESA) {
			return false;
		}
		
		jogadores.add(jogador);
		
		return true;
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

		System.out.println(mao + "ª mao | vale " + premioMao + " ponto | vira: " + vira + " \n");

		rodada = 0;

		//while (rodada < 3) {

			//rodada++;
			System.out.println("Rodada " + rodada);
			comecarRodada();
		//}

		/*if (pontuacaoMaoTime1 == 2)
			pontuacaoJogoTime1 += premioMao;
		else
			pontuacaoJogoTime2 += premioMao;

		// remove cartas restantes
		for (Jogador jogador : jogadores) {
			jogador.entregarCartas();
		}*/
	}
	
	public void jogada(String playerName, String carta) {
		Jogador jogadorJogada = getJogadorByName(playerName);
		Carta c = jogadorJogada.removeCarta(carta);
		
		if(c == null) {
			System.err.println("Erro na carta. N estava no deck do jogador");
			return;
		}
		
	}

	public void comecarRodada() {

		/*Jogador jogadorMaiorCarta = null, proximoJogador = null;
		Carta maiorCarta = null, tempCarta = null;
		boolean melado = false;*/
		
		jogadorCorrente = proximoJogador();
		broadcastChangeTurn(jogadorCorrente.getNome());

		/*for (int i = 0; i < jogadores.size(); i++) {

			// vez do jogador
			proximoJogador = proximoJogador();
			broadcastChangeTurn(proximoJogador.getNome());
			
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
		}*/

		//System.out.println(melado + " - " + maiorCarta);
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
				Carta carta = baralho.retirarCarta();
				jogador.recebeCarta(carta);
			}
		}

		// vira ultima carta do baralho
		vira = baralho.retirarCarta();
	}

	// retorna proximo jogador da mesa
	private Jogador proximoJogador() {
		if(currentPlayer == 3)
			currentPlayer = 0;
		return jogadores.get(currentPlayer++);
	}

	@Override
	public void run() {
		try {
			ApplicationController.getInstance().logData("Iniciando um jogo");
			comecarJogo();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
