package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import controller.ApplicationController;

public class MainWindow extends JFrame {

	private static final int WIDTH = 240;
	private static final int HEIGHT = 500;
	
	private JScrollPane scrollPane;
	private JList<String> onlineUsersList;
	private DefaultListModel<String> listModel;
	
	private JLabel infoLabel;
	private JButton playButton;
	private JButton exitButton;
	private JTextPane chatPane;
	private JTextPane messagePane;
	
	private JScrollPane onlineUsersPanel;

	
	public MainWindow() {
		initializeComponents();
		initializeEvents();
	}
	
	public void showWindow() {
		this.setVisible(true);
	}
	
	public void hideWindow() {
		this.setVisible(false);
	}
	
	public void enableChat() {
		this.messagePane.setEditable(true);
	}
	
	private void initializeComponents() {
		
		// Window data
		this.setSize(WIDTH, HEIGHT);
		this.setLocationRelativeTo(null);
		this.setTitle("Truco");
		this.setLayout(null);
		
		// List model data
		this.listModel = new DefaultListModel<String>();
		
		// List data
		this.onlineUsersList = new JList<>();
		this.onlineUsersList.setSize(560, 300);
		this.onlineUsersList.setLocation(0, 0);
		this.onlineUsersList.setModel(listModel);
		
		// Scroll pane data
//		this.scrollPane = new JScrollPane(this.list);
//		this.scrollPane.setLayout(null);
//		this.scrollPane.setSize(560, 400);
//		this.scrollPane.setLocation(10, 50);
//		this.scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		// Label Data
		this.infoLabel = new JLabel("Bem vindo " + ApplicationController.getInstance().getClientName() + " ao Truco Client!");
		this.infoLabel.setSize(300, 30);
		this.infoLabel.setLocation(10, 10);
		
		
		// Button Data
		this.playButton = new JButton("Jogar!");
		this.playButton.setSize(200, 50);
		this.playButton.setLocation(10, 350);
		this.playButton.setEnabled(true);
		
		// Exit Button data
		this.exitButton = new JButton("Sair");
		this.exitButton.setSize(200, 50);
		this.exitButton.setLocation(10, 400);
		this.exitButton.setEnabled(true);
		
		this.add(this.infoLabel);
//		this.add(scrollPane);
		this.add(this.playButton);
		this.add(exitButton);
		
		// Window data
		this.setSize(WIDTH, HEIGHT);
		this.setLocationRelativeTo(null);
		this.setTitle("Truco");
		this.setLayout(null);
		
		JLabel onlineTitle = ApplicationController.generateSimpleLabel("Usuarios online:");
		onlineTitle.setLocation(10, 40);
		
		this.onlineUsersPanel = new JScrollPane(onlineUsersList);
		this.onlineUsersPanel.setSize(200, 300);
		this.onlineUsersPanel.setLocation(10, 70);
		
		this.add(onlineTitle);
		this.add(this.onlineUsersPanel);
		
		// textArea data
//		this.chatPane = new JTextPane();
//		this.chatPane.setSize(WIDTH - 40, HEIGHT - 500);
//		this.chatPane.setLocation(10, HEIGHT - 200);
//		this.chatPane.setEditable(false);
//		
		// scrollPane data
//		this.scrollPane = new JScrollPane(chatPane);
//		this.scrollPane.setSize(WIDTH - 40, HEIGHT - 500);
//		this.scrollPane.setLocation(10, HEIGHT - 200);
//		this.scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		// textArea data
//		this.messagePane = new JTextPane();
//		this.messagePane.setSize(WIDTH - 40, HEIGHT - 575);
//		this.messagePane.setLocation(10, HEIGHT - 75);
//		this.messagePane.setEditable(false);

//		this.add(this.scrollPane);
		//this.add(this.messagePane);
		
		initializeEvents();
		
	}
	
	public void updateOnlineUsersList(String list) {
		String[] splitedString = list.split(";");
		
		listModel.removeAllElements();

		for(String str : splitedString) {
			listModel.addElement(str);
		}
	}
	
	public void initializeEvents() {
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
		
		this.playButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ApplicationController.getInstance().waitingRoom();
			}
		});
		
		this.exitButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
//		this.messagePane.addKeyListener(new KeyListener() {
//
//			@Override
//			public void keyPressed(KeyEvent e) {
//			}
//
//			@Override
//			public void keyReleased(KeyEvent e) {
//				try {
//					//Se o usuário apertar o enter, enviar a mensagem para o servidor, para que o mesmo envie a mensagem para todos os usuários
//						if (e.getKeyCode()==KeyEvent.VK_ENTER) {
//							Communication.getInstance().sendMessage(Messages.CHAT);
//							Communication.getInstance().sendMessage(messagePane.getText().replaceAll("\n", ""));
//							messagePane.setText(null);
//						}
//					} catch (UnknownHostException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					} catch (IOException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
//			}
//
//			@Override
//			public void keyTyped(KeyEvent e) {}
//		});
	}
	
	public void showMessage(String msg) {
		//this.chatPane.setText(this.chatPane.getText() + msg);
	}
	
}
