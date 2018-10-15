package controller;

import java.io.IOException;

import view.MainWindow;

public class ApplicationController {

	private MainWindow mainWindow;
	private Server server;
	
	private static ApplicationController instance;
	
	public ApplicationController() throws IOException {
		mainWindow = new MainWindow();
		server = Server.getInstance();
	}
	
	public void startApp() {
		mainWindow.startWindow();
	}
	
	public void startServer() throws IOException {
		logData("Starting the server...");
		if(server.startServer())
			logData("Server started!");
		else 
			logData("Error on starting the server...");
	}
	
	public void stopServer() throws IOException {
		server.stopServer();
	}
	
	public void logData(String data) {
		this.mainWindow.logData(data);
	}
	
	public int addJogador(String name) {
		return LobbyController.getInstance().addJogador(name, server.getClientMap().get(name));
	}
	
	public void removeJogador(String name) {
		LobbyController.getInstance().removeJogador(name);
	}
	
	public void sendChatMessage(String name, String message, String type) {
		LobbyController.getInstance().sendChatMessage(name, message, type);
	}
	
	public void play(String cardName, String playerName) {
		LobbyController.getInstance().getMesaByName(playerName).jogada(playerName, cardName);
	}
	
	public static ApplicationController getInstance() throws IOException {
		if(instance == null)
			instance = new ApplicationController();
		return instance;
	}
}
