package controller;

import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import util.Messages;
import view.GameWindow;
import view.LoginWindow;
import view.MainWindow;
import view.WaitingWindow;

public class ApplicationController {

	private static ApplicationController instance;
	private LoginWindow loginWindow;
	private MainWindow mainWindow;
	private WaitingWindow waitingWidow;
	private GameWindow gameWindow;
	private String clientName;
	
	public ApplicationController() {
		loginWindow = new LoginWindow();
	}
	
	public void startMainWindow() {
		loginWindow.hideWindow();
		mainWindow = new MainWindow();
		mainWindow.showWindow();
	}
	
	public void startApp() {
		loginWindow.showWindow();
	}
	
	public void failedLogin() {
		JOptionPane.showMessageDialog(null, "Login ja utilizado no servidor", "Erro no Login", JOptionPane.ERROR_MESSAGE);
	}
	
	public void successfulLogin() {
		startMainWindow();
	}
	
	public void receiveChatMessage(String msg, String type) {
		if(gameWindow == null) {
			System.err.println("Mensagem de chat recebida fora da hora");
			return;
		}
		
		gameWindow.receiveChatMessage(msg, type);
	}
	
	public void sendChatMessage(String msg, String type) {
		if(gameWindow == null) {
			System.err.println("Mensagem de chat enviada fora da hora");
			return;
		}
		
		try {
			Communication.getInstance().sendChatMessage(msg, type);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void login(String name) {
		try {
			Communication.getInstance().registerUser(name);
			this.clientName = name;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void waitingRoom() {
		
		if(this.waitingWidow != null) {
			System.err.println("Erro! Waiting Window ja existe");
			return;
		}
		
		
		this.waitingWidow = new WaitingWindow();
		this.waitingWidow.showWindow();
		
		this.mainWindow.setEnabled(false);
		
		try {
			Communication.getInstance().enterRoom();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void changeWaitingMessage(int num) {
		if(this.waitingWidow == null) {
			return;
		}
		
		this.waitingWidow.changeMessage(num);
	}
	
	public void initGame(String names) {
		if(this.waitingWidow != null) {
			this.waitingWidow.dispose();
			this.waitingWidow = null;	
		}
		
		this.gameWindow = new GameWindow(names, clientName);
		gameWindow.showWindow();
	}
	
	public void cancelWait() {
		this.waitingWidow.dispose();
		this.waitingWidow = null;
		
		this.mainWindow.setEnabled(true);
		
		try {
			Communication.getInstance().sendMessage(Messages.CANCEL_WAIT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateScore(String score1, String score2) {
		if(gameWindow == null) {
			System.err.println("There is no game to update the score");
			return;
		}
		
		gameWindow.updateScore(score1, score2);
	}
	
	public void clearDealer() {
		if(gameWindow == null) {
			System.err.println("There is no game to clear the dealer");
			return;
		}
		
		gameWindow.clearDealer();
	}
	
	public void receiveDealer(String playerName, String cardName) {
		if(gameWindow == null) {
			System.err.println("There is no game to receive dealer card");
			return;
		}
		
		gameWindow.receiveDealerCard(playerName, cardName);
	}
	
	public void changeTurn(String name) {
		if(gameWindow == null) {
			System.err.println("There is no game to start round");
			return;
		}
		
		gameWindow.changeTurn(name);
	}
	
	public void play(String cardName) {
		System.out.println("Im gonna play " + cardName);
		
		if(gameWindow == null) {
			System.err.println("There is no game to play");
			return;
		}
		
		if(gameWindow.canPlay) {
			try {
				gameWindow.removeCard(clientName, cardName);
				Communication.getInstance().sendPlayMessage(cardName, clientName);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			JOptionPane.showMessageDialog(gameWindow, "Ainda nao eh sua vez!");
		}
	}
	
	public void drawCard(String card) {
		if(gameWindow == null) {
			System.err.println("There is no game to draw a card");
			return;
		}
		
		gameWindow.drawCard(card);
	}
	
	public void updateOnlineList(String list) {
		this.mainWindow.updateOnlineUsersList(list);
	}
	
	public static ApplicationController getInstance() {
		if(instance == null)
			instance = new ApplicationController();
		return instance;
	}
	
	public static JLabel generateSimpleLabel(String text) {
		JLabel retorno = new JLabel(text);
		retorno.setSize(300, 30);
		return retorno;
	}
	
	public String getClientName() {
		return clientName;
	}
}
