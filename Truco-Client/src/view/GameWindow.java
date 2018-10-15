package view;

import javax.swing.JFrame;

public class GameWindow extends JFrame {

	private static final int WIDTH = 600;
	private static final int HEIGHT = 600;
	
	private String names;
	
	public GameWindow(String names) {
		this.names = names;
		initializeComponents();
	}
	
	public void initializeComponents() {
		this.setSize(WIDTH, HEIGHT);
		this.setLayout(null);
		this.setTitle("Truco!");
	}
	
	public void showWindow() {
		this.setVisible(true);
	}
}
