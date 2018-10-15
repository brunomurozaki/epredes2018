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
	
	private Carta[] fullJogada;
	private Jogador[] vencedorPorRodada;
	
	private boolean [] respostaTruco;
	
	public Mesa() {
		jogadores = new ArrayList<>();
		jogadorPorTime = new HashMap<>();
		fullJogada = new Carta[MAX_MESA];
		respostaTruco = new boolean[MAX_MESA];
		vencedorPorRodada = new Jogador[3];
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
	
	// Remove o jogador que cancelou e atualiza os demais membros da sala o ocorrido
	public void removeJogador(String nome) {
		for(int i = 0; i < jogadores.size(); i++) {
			if(jogadores.get(i).getNome().equals(nome))
				jogadores.remove(i);
		}
		broadcastWaitingMessage();
	}
	
	// Envia para todos os jogadores de quem eh a vez
	private void broadcastChangeTurn(String name) {
		System.out.println("Agora é vez do: " + name);
		for(Jogador j : jogadores) {
			j.sendTurn(name);
		}
	}
	
	// Envia mensagem de chat para todos os clientes
	public void broadcastChatMessage(String message) {
		for(Jogador j : jogadores) {
			j.sendMessage(Messages.CHAT);
			j.sendMessage(message);
			j.sendMessage(Messages.PUBLIC);
		}
	}
	
	// Envia mensagem de quantos jogadores ainda faltam para a mesa estar completa
	// Se a mesa foi completa, envia a mensagem de start game
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
				
				// Welcome message
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
		jogadorPorTime.put(jogador, jogadores.size() % 2 == 0 ? TIME1 : TIME2);
		
		return true;
	}

	public void comecarJogo() {

		pontuacaoJogoTime1 = 0;
		pontuacaoJogoTime2 = 0;

		//while (pontuacaoJogoTime1 < 12 && pontuacaoJogoTime2 < 12) {

			baralho = new Baralho();
			baralho.embaralhar();

			comecarPartida();
		//}

		/*if (pontuacaoJogoTime1 == 12)
			System.out.println("Time 1 ganhou!");
		else
			System.out.println("Time 2 ganhou!");*/
	}

	// Inicio a partida, distribuindo as cartas e iniciando o turno 
	private void comecarPartida() {

		try {
			ApplicationController.getInstance().logData("Iniciando partida..." + mao);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		mao++;
		premioMao = 1;

		distribuirCartas();

		pontuacaoMaoTime1 = 0;
		pontuacaoMaoTime2 = 0;

		System.out.println(mao + "Âª mao | vale " + premioMao + " ponto | vira: " + vira + " \n");

		rodada = 0;

		//while (rodada < 3) {

			//rodada++;
			System.out.println("Rodada " + rodada);
			comecarTurno();
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
	
	// Jogada realizada pelo cliente
	public void jogada(String playerName, String carta) {
		Jogador jogadorJogada = getJogadorByName(playerName);
		Carta c = jogadorJogada.removeCarta(carta);
		int i = jogadores.indexOf(jogadorJogada);
		
		if(c == null) {
			System.err.println("Erro na carta. N estava no deck do jogador");
			return;
		}
		
		// acumulo a jogada no deck do dealer, pra poder ver depois quem foi a carta vencedora
		fullJogada[i] = c;
		
		for(Jogador j : jogadores) {
			j.sendDealer(jogadorJogada.getNome(), c.translateCard());	
		}
		
		// Se foi o ultimo jogador, termina a rodada. Caso contrario, inicia um novo turno 
		if(i == 3) {
			terminaRodada();
		} else {
			comecarTurno();	
		}
	}
	
	// Termino a rodada, e ja vejo quem foi o vencedor
	public void terminaRodada() {
		Jogador jogadorMaiorCarta = null;
		Carta maiorCarta = null;
		boolean melado = false;
		
		jogadorMaiorCarta = jogadores.get(0);
		maiorCarta = fullJogada[0];
		
		for(int i = 1; i < MAX_MESA; i++) {
			int resultado = compararCarta(maiorCarta, fullJogada[i]);
			
			if(resultado == MAIOR) {
				maiorCarta = fullJogada[i];
				jogadorMaiorCarta = jogadores.get(i);
			} else if(resultado == MELOU) {
				melado = true;
			}
		}

		// Faco a adicao na pontuacao dos times
		if (melado) {
			System.out.println("Melou cocho");
			pontuacaoMaoTime1++;
			pontuacaoMaoTime2++;
		} else {
			if (jogadorPorTime.get(jogadorMaiorCarta) == TIME1) {
				System.out.println("Deu time 1!");
				pontuacaoMaoTime1++;
			} else {
				System.out.println("Deu time 2!");
				pontuacaoMaoTime2++;
			}
		}
		
		rodada++;
		
		// zero o deck do dealer
		for(int i = 0; i < MAX_MESA; i++) {
			fullJogada[i] = null;
		}
		
		if((pontuacaoMaoTime1 == 2 && pontuacaoMaoTime2 < 2) || (pontuacaoMaoTime2 == 2 && pontuacaoMaoTime1 < 2) || rodada == 3) {
			terminaPartida();
		} else {
			comecarTurno();
		}
		
	}

	// Envio a todos os clientes para zerar o dealer
	private void broadcastClearDealer() {
		for(Jogador j : jogadores) {
			j.sendMessage(Messages.CLEAR_DEALER);
		}
	}
	
	// Encerro a partida e vejo se o jogo ja pode ser encerrado
	private void terminaPartida() {
		if(pontuacaoMaoTime1 > pontuacaoMaoTime2) {
			pontuacaoJogoTime1 += premioMao;
		} else if(pontuacaoMaoTime1 < pontuacaoMaoTime2) {
			pontuacaoJogoTime2 += premioMao;
		} 
		
		// Envio o score do jogo atualizado aos jogadores
		atualizaScoreJogadores();
		
		if(pontuacaoJogoTime1 >= 12) {
			terminaJogo(TIME1);
		} else if(pontuacaoJogoTime2 >= 12) {
			terminaJogo(TIME2);
		} else {
			comecarPartida();
		}
	}

	// Faz broadcast do score pros jogadores
	private void atualizaScoreJogadores() {
		for(Jogador j : jogadores) {
			j.sendMessage(Messages.SCORE);
			j.sendMessage(String.valueOf(pontuacaoJogoTime1));
			j.sendMessage(String.valueOf(pontuacaoJogoTime2));
		}
	}
	
	public void terminaJogo(int vencedor) {
		// TODO send end game message
	}
	
	public void askTruco(String playerName) {
	
		for(Jogador j : jogadores) {
			if(j.getNome().equals(playerName))
				continue;
			
		}
		
	}
	
	public void truco() {
		if(premioMao == 1)
		{
			premioMao = 3;
		} else {
			premioMao += 3;
		}
		
		if(premioMao > 12)
			premioMao = 12;
	}
	

	// Ja aviso ao cliente quem pode fazer a proxima jogada
	private void comecarTurno() {
		jogadorCorrente = proximoJogador();
		broadcastChangeTurn(jogadorCorrente.getNome());
	}

	// Retorna o valor sempre baseado na primeira carta
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
		
		broadcastVira();
	}

	// Envia para todos os jogadores a carta virada
	private void broadcastVira() {
		for(Jogador j : jogadores) {
			j.sendMessage(Messages.DRAW_VIRA);
			j.sendMessage(vira.translateCard());
		}
	}

	// retorna proximo jogador da mesa
	private Jogador proximoJogador() {
		if(currentPlayer == 4) {
			// Envio o clear aqui pois o cliente precisa ver a ultima carta jogada
			System.out.println("Ultimo jogador");
			broadcastClearDealer();
			currentPlayer = 0;
		}
		
		Jogador j = jogadores.get(currentPlayer);
		currentPlayer++;

		return j;
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
