package controller;

import java.io.IOException;

import javax.swing.JOptionPane;

import view.LoginWindow;

public class ApplicationController {

	private static ApplicationController instance;
	
	
	public ApplicationController() {
		// TODO Auto-generated constructor stub
	}
	
	public void startApp() {
		new LoginWindow().setVisible(true);
	}
	
	public void failedLogin() {
		JOptionPane.showMessageDialog(null, "Login ja utilizado no servidor", "Erro no Login", JOptionPane.ERROR_MESSAGE);
	}
	
	public void successfulLogin() {
		JOptionPane.showMessageDialog(null, "Login realizado com sucesso", "Login", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void login(String name) {
		try {
			
			Communication.getInstance().registerUser(name);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static ApplicationController getInstance() {
		if(instance == null)
			instance = new ApplicationController();
		return instance;
	}
	
}
