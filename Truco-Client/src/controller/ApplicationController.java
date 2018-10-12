package controller;

import java.io.IOException;

import javax.swing.JOptionPane;

import view.LoginWindow;
import view.MainWindow;

public class ApplicationController {

	private static ApplicationController instance;
	private LoginWindow loginWindow;
	private MainWindow mainWindow;
	
	public ApplicationController() {
		loginWindow = new LoginWindow();
		mainWindow = new MainWindow();
	}
	
	public void startMainWindow() {
		loginWindow.hideWindow();
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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void enableChat(){
		this.mainWindow.enableChat();
	}
	
	public static ApplicationController getInstance() {
		if(instance == null)
			instance = new ApplicationController();
		return instance;
	}
	
}
