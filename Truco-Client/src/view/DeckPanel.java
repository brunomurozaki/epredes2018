package view;

import java.awt.Color;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class DeckPanel extends JPanel{

	private JLabel labelName;
	private String name;
	private static final int WIDTH = 180;
	private static final int HEIGHT = 120;
	
	
	public HashMap<String, CardPanel> cardMap;
	
	
	public DeckPanel(String name) {
		this.name = name;
		this.cardMap = new HashMap<>();
		
		initializeComponents();
	}
	
	public void addCard(String name) {
		if(cardMap.keySet().size() == 3)
			return;
		
		if(cardMap.get(name) != null) {
			return;
		}
		
		CardPanel panel = new CardPanel(name);
		panel.setLocation(cardMap.keySet().size() * 60, 30);
		this.add(panel);
		
		cardMap.put(name, panel);
	}
	
	private void initializeComponents(){
		
		this.setSize(WIDTH, HEIGHT);
		this.setBackground(Color.green);
		this.setLayout(null);
		
		this.labelName = new JLabel("<html><b>" + this.name + "<b></html>");
		this.labelName.setSize(150, 30);
		this.add(labelName);
		
		// Remover isso depois
		this.addCard("10C");
		this.addCard("10D");
		this.addCard("7D");
	}
	
	
}
