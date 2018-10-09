package controller;

import java.util.ArrayList;

import model.Jogador;

public class Chat implements Runnable {

	public void mensagemSistema(String mensagem) {

		System.out.println("Sistema: " + mensagem);
	}

	public void mensagemJogador(Jogador jogador, String mensagem) {

		System.out.println(jogador.getNome() + ": " + mensagem);
	}
	
	private ArrayList<Jogador> listaJogadores;
	
	public Chat(ArrayList<Jogador> listaJogadores){
		this.setListaJogadores(listaJogadores);
	}

	@Override
	public void run() {
		
		broadcastAll("System: Chat started. Watch your lang... Fuck you TRUCO PORRA!", listaJogadores);
		while(true){
			
			
		}
		
	}

	public ArrayList<Jogador> getListaJogadores() {
		return listaJogadores;
	}

	public void setListaJogadores(ArrayList<Jogador> listaJogadores) {
		this.listaJogadores = listaJogadores;
	}
	
	public void broadcastAll(String msg, ArrayList<Jogador> lista){
		for(Jogador jogador : lista){
			jogador.getClient().sendMessage(msg);
		}
	}

}
