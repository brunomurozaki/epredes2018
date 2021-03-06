package view;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScorePanel extends JPanel {

	private static final int WIDTH = 100;
	private static final int HEIGHT = 130;
	
	private JLabel time1Score;
	private JLabel time2Score;
	
	private JLabel turnLabel;
	
	private JLabel playValue;
	
	public ScorePanel() {
		initializeComponets();
	}
	
	private void initializeComponets() {
		this.setSize(WIDTH, HEIGHT);
		this.setBackground(Color.white);
		this.setLayout(null);
		
		this.time1Score = new JLabel("Time 1: 0");
		this.time1Score.setSize(100, 30);
		this.time1Score.setLocation(10, 10);
		
		this.time2Score = new JLabel("Time 2: 0");
		this.time2Score.setSize(100, 30);
		this.time2Score.setLocation(10, 40);
		
		this.turnLabel = new JLabel();
		this.turnLabel.setSize(100, 30);
		this.turnLabel.setLocation(10, 70);
		
		this.playValue = new JLabel("Valor jogo: 1");
		this.playValue.setSize(100, 30);
		this.playValue.setLocation(10, 100);
		
		this.add(time1Score);
		this.add(time2Score);
		this.add(turnLabel);
		this.add(playValue);
	}
	
	public void changeScore(String score, int time) {
		if(time == 1)
			this.time1Score.setText("Time 1: " + score);
		else
			this.time2Score.setText("Time 2: " + score);
	}
	
	public void changePlayValue(String valor) {
		this.playValue.setText("Valor jogo: " + valor);
	}
	
	public void changeTurn(String name) {
		this.turnLabel.setText("Vez: " + name);
	}
}
