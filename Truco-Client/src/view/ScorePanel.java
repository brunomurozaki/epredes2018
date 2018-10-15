package view;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScorePanel extends JPanel {

	private static final int WIDTH = 100;
	private static final int HEIGHT = 80;
	
	private JLabel time1Score;
	private JLabel time2Score;
	
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
		
		this.add(time1Score);
		this.add(time2Score);
	}
	
	public void changeScore(int score, int time) {
		if(time == 1)
			this.time1Score.setText("Time 1: " + score);
		else
			this.time2Score.setText("Time 2: " + score);
	}
}
