package controller;

import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import view.LoginWindow;
import view.MainWindow;

public class ApplicationController {

	private static ApplicationController instance;
	private LoginWindow loginWindow;
	private MainWindow mainWindow;
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
	
	public void showMessage(String msg) {
		this.mainWindow.showMessage(msg);
	}
	
	public void login(String name) {
		try {
			Communication.getInstance().registerUser(name);
			this.clientName = name;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void enableChat(){
		this.mainWindow.enableChat();
	}
	
	public void updateOnlineList(String list) {
		this.mainWindow.updateOnlineUsersList(list);
	}
	
	public static ApplicationController getInstance() {
		if(instance == null)
			instance = new ApplicationController();
		return instance;
	}
	
	public String getClientName() {
		return clientName;
	}
	
	public static JLabel generateSimpleLabel(String text) {
		JLabel retorno = new JLabel(text);
		retorno.setSize(300, 30);
		
		
		return retorno;
	}
	
}
