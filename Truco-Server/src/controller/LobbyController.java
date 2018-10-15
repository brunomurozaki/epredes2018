package controller;

import java.util.ArrayList;

import model.Jogador;

public class LobbyController {

	private static LobbyController instance;
	private ArrayList<Mesa> gameList;
	private ArrayList<Thread> gameThread;
	
	
	public LobbyController() {
		gameList = new ArrayList<>();
		gameThread = new ArrayList<>();
	}
	
	public Mesa getMesaByName(String name) {
		for(Mesa m : gameList) {
			if(m.hasJogador(name))
				return m;
		}
		
		return null;
	}
	
	public void removeJogador(String name) {
		Mesa m;
		
		for(int i = 0; i < gameList.size(); i++) {
			m = gameList.get(i);
			if(m.hasJogador(name))
			{
				m.removeJogador(name);
			}
		}
		
	}
	
	public void sendChatMessage(String name, String message, String type) {
		for(Mesa m : gameList) {
			if(m.hasJogador(name)) {
				m.broadcastChatMessage(name + " - " + message);
			}
		}	
	}
	
	public int addJogador(String name, Client client) {
		Jogador jogador = new Jogador(name, client);
		
		for(Mesa m : gameList) {			
			if(m.mesaFull())
				continue;
			
			m.addJogador(jogador);
			m.broadcastWaitingMessage();
			
			if(m.mesaFull()) {
				Thread t = new Thread(m);
				gameThread.add(t);
				t.start();
			}
			
			return m.getNumPlayers();
		}
		
		
		Mesa m = new Mesa();
		m.addJogador(jogador);
		this.gameList.add(m);
		return m.getNumPlayers();
	}
	
	public static LobbyController getInstance() {
		if(instance == null)
			instance = new LobbyController();
		
		return instance;
	}
	
}
