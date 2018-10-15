package view;

import java.awt.Color;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class DeckPanel extends JPanel {

	private JLabel labelName;
	private String name;
	private static final int WIDTH = 180;
	private static final int HEIGHT = 120;
	public HashMap<String, CardPanel> cardMap;
	private boolean isDealer;
	private int maxSize;

	public DeckPanel(String name, boolean isDealer) {
		this.isDealer = isDealer;
		this.name = name;
		this.cardMap = new HashMap<>();

		if (isDealer)
			maxSize = 4;
		else
			maxSize = 3;

		initializeComponents();
	}

	public void addCard(String cardName) {

		if (cardMap.keySet().size() == maxSize)
			return;

		if (cardMap.get(cardName) != null) {
			return;
		}

		boolean isDefault = false;

		if (cardName.equals("red_back")) {
			isDefault = true;
			cardName += cardMap.keySet().size();
		}

		CardPanel panel = new CardPanel(cardName, isDefault, isDealer);

		if (isDealer) {
			panel.setLocation(cardMap.keySet().size() * 40, 30);
		} else {
			panel.setLocation(cardMap.keySet().size() * 60, 30);
		}

		this.add(panel);
		this.repaint();

		cardMap.put(cardName, panel);
	}

	public void removeCard(String cardName) {
		System.out.println("Removendo a carta " + cardName);
		CardPanel panelCard = cardMap.get(cardName);
		
		System.out.println("Removendo a cardPanel " + panelCard.getName());
		
		this.remove(panelCard);
		cardMap.remove(cardName);
		
		this.repaint();
	}

	public void clearPanel() {
		cardMap.clear();
		this.removeAll();
		this.repaint();
	}

	private void initializeComponents() {

		this.setSize(WIDTH, HEIGHT);
		this.setBackground(Color.green);
		this.setLayout(null);

		this.labelName = new JLabel("<html><b>" + this.name + "<b></html>");
		this.labelName.setSize(150, 30);
		this.add(labelName);
		
		this.repaint();

	}

	public String getName() {
		return name;
	}

}
