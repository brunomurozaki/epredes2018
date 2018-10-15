package view;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import controller.ApplicationController;
import util.Messages;

public class GameWindow extends JFrame {

	private static final int WIDTH = 900;
	private static final int HEIGHT = 600;
	
	private JPanel gamePane;
	private JScrollPane chatPane;
	private JTextArea chatHistory;
	private JTextField messageField;
	private JButton sendButton;
	private ScorePanel scorePanel;
	
	private DeckPanel frontPanel;
	private DeckPanel leftPanel;
	private DeckPanel rightPanel;
	private DeckPanel minePanel;
	
	private DeckPanel dealerPanel;
	
	private String names;
	
	public GameWindow(String names) {
		this.names = names;
		initializeComponents();
		initializeEvents();
	}
	
	public void receiveChatMessage(String message, String type) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		
		String typeMessage = "";
		
		if(type.equals(Messages.TEAM)) {
			typeMessage = " [TEAM] ";
		}
		
		String log = dateFormat.format(date) + typeMessage + " - " + message + "\n";
		this.chatHistory.append(log);
	}
	
	private void sendChatMessage() {
		String text = this.messageField.getText();
		String type = Messages.PUBLIC;
		
		ApplicationController.getInstance().sendChatMessage(text, type);
	}
	
	private void initializeEvents() {
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				super.windowClosing(e);
				
				System.exit(0);
				
			}
		});
		
		this.messageField.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				sendChatMessage();
				messageField.setText("");
			}
		});
		
		this.sendButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				sendChatMessage();
				messageField.setText("");
			}
		});
		
		
	}
	
	public void initializeComponents() {
		this.setSize(WIDTH, HEIGHT);
		this.setLayout(null);
		this.setLocationRelativeTo(null);
		this.setTitle("Truco!");
		
		this.gamePane = new JPanel();
		this.gamePane.setSize(560, 540);
		this.gamePane.setLocation(10, 10);
		this.gamePane.setLayout(null);
		this.gamePane.setBackground(Color.GREEN);
		
		this.chatHistory = new JTextArea();
		this.chatHistory.setSize(300, 490);
		this.chatHistory.setLocation(0, 0);
		this.chatHistory.setEditable(false);
		this.chatHistory.setFont(this.chatHistory.getFont().deriveFont(12f));
		
		this.chatPane = new JScrollPane(chatHistory);
		this.chatPane.setSize(300, 490);
		this.chatPane.setLocation(580, 10);
		this.chatPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		this.messageField = new JTextField();
		this.messageField.setSize(245, 30);
		this.messageField.setLocation(580, 500);
		
		this.sendButton = new JButton("Send");
		this.sendButton.setSize(56, 30);
		this.sendButton.setLocation(825, 500);
		
		this.scorePanel = new ScorePanel();
		this.scorePanel.setLocation(480, 0);
		
		//CardPanel panel = new CardPanel("10C");
		//this.setLocation(0, 0);
		
		this.add(this.gamePane);
		this.add(this.chatPane);
		this.add(this.messageField);
		this.add(this.sendButton);
		
		
		this.gamePane.add(scorePanel);
		
		// remover isto depois
		//this.startGame();
	}
	
	public void startGame() {
		String[] splittedNames = this.names.split(";");
		
		minePanel = new DeckPanel(splittedNames[0]);
		minePanel.setLocation(190, 410);
		
		rightPanel = new DeckPanel(splittedNames[1]);
		rightPanel.setLocation(380, 210);
		
		leftPanel = new DeckPanel(splittedNames[2]);
		leftPanel.setLocation(0, 210);
		
		frontPanel = new DeckPanel(splittedNames[3]);
		frontPanel.setLocation(190, 0);
		
		dealerPanel = new DeckPanel("Mesa");
		dealerPanel.setLocation(190, 210);
		
		this.gamePane.add(minePanel);
		this.gamePane.add(rightPanel);
		this.gamePane.add(leftPanel);
		this.gamePane.add(frontPanel);
		this.gamePane.add(dealerPanel);
	}
	
	public void showWindow() {
		this.setVisible(true);
	}
}
