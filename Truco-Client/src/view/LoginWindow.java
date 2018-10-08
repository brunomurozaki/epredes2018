package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import controller.ApplicationController;

public class LoginWindow extends JFrame{

	private static final int WIDTH = 300;
	private static final int HEIGHT = 150;
	
	private JButton loginButton;
	private JButton cancelButton;
	private JTextField loginText;
	private JLabel infoLabel;
	
	
	public LoginWindow() {
		initializeComponents();
		initializeEvents();
	}
	
	
	private void initializeComponents() {
		
		// Login Window data
		this.setLayout(null);
		this.setTitle("Login - Truco Client");
		this.setSize(WIDTH, HEIGHT);
		this.setLocationRelativeTo(null);
		
		// login button data
		loginButton = new JButton("Login");
		loginButton.setSize(100, 50);
		loginButton.setLocation(170, 50);
		
		// Cancel button data
		cancelButton = new JButton("Cancel");
		cancelButton.setSize(100, 50);
		cancelButton.setLocation(10, 50);
		
		// label data
		infoLabel = new JLabel("Login:");
		infoLabel.setLocation(50, 10);
		infoLabel.setSize(100, 30);
		
		// textbox data
		loginText = new JTextField();
		loginText.setSize(120, 30);
		loginText.setLocation(100, 10);
		
		
		this.add(loginButton);
		this.add(cancelButton);
		this.add(infoLabel);
		this.add(loginText);
		
	}
	
	public void showWindow() {
		this.setVisible(true);
	}
	
	public void hideWindow() {
		this.setVisible(false);
	}
	
	private void initializeEvents() {
		
		// Window events
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				super.windowClosing(e);
				System.exit(0);
			}
		});
		
		this.loginButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ApplicationController.getInstance().login(loginText.getText());
			}
		});
		
		this.cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
	}
	
}
