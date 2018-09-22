package controller;

import model.Jogador;

public class Chat implements Runnable {

	public void mensagemSistema(String mensagem) {

		System.out.println("Mensagem do Sistema: " + mensagem);
	}

	public void mensagemJogador(Jogador jogador, String mensagem) {

		System.out.println("Mensagem de " + jogador.getNome() + ": " + mensagem);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

}
