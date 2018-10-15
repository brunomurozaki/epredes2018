package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import controller.ApplicationController;

public class WaitingWindow extends JFrame{

	private static final int WIDTH = 380;
	private static final int HEIGHT = 150;
	
	private JLabel label;
	
	private JButton cancelButton;
	
	public WaitingWindow() {
		initializeComponents();
		initializeEvents();
	}
	
	private void cancel() {
		this.dispose();
		ApplicationController.getInstance().cancelWait();
	}
	
	private void initializeEvents() {
		this.cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				cancel();
			}
		});
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				cancel();
			}
		});
	}
	
	public void showWindow() {
		this.setVisible(true);
	}
	
	public void changeMessage(int num) {
		this.label.setText("Aguardando mais " + num + " jogadores");
	}
	
	private void initializeComponents() {
		this.setSize(WIDTH, HEIGHT);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		this.setTitle("Aguardando outros jogadores...");
		
		this.label = new JLabel();
		this.label.setSize(200, 30);
		this.label.setLocation(10, 10);
		this.label.setText("Aguardando mais 3 jogadores...");
		
		this.cancelButton = new JButton("Cancelar");
		this.cancelButton.setLocation(10, 50);
		this.cancelButton.setSize(150, 50);
		
		this.add(this.cancelButton);
		this.add(this.label);
	}
	
}
