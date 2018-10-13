package view;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import controller.ApplicationController;
import controller.Communication;
import util.Messages;

public class MainWindow extends JFrame {

	private static final int WIDTH = 600;
	private static final int HEIGHT = 600;
	
	private JScrollPane scrollPane;
	private JList<String> list;
	
	private JLabel infoLabel;
	private JButton playRoom;
	private JTextPane chatPane;
	private JTextPane messagePane;

	
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
		
//		// Window data
//		this.setSize(WIDTH, HEIGHT);
//		this.setLocationRelativeTo(null);
//		this.setTitle("Truco");
//		this.setLayout(null);
//		
//		// List data
//		this.list = new JList<>();
//		this.list.setSize(560, 400);
//		this.list.setLocation(0, 0);
//		
//		// Scroll pane data
//		this.scrollPane = new JScrollPane(this.list);
//		this.scrollPane.setLayout(null);
//		this.scrollPane.setSize(560, 400);
//		this.scrollPane.setLocation(10, 50);
//		this.scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//		
//		// Label Data
//		this.infoLabel = new JLabel("Rooms:");
//		this.infoLabel.setSize(100, 30);
//		this.infoLabel.setLocation(10, 10);
//		
//		
//		// Button Data
//		this.playRoom = new JButton("Play!");
//		this.playRoom.setSize(200, 50);
//		this.playRoom.setLocation(370, 500);
//		this.playRoom.setEnabled(false);
//		
//		this.add(this.infoLabel);
//		this.add(scrollPane);
//		this.add(this.playRoom);
		
		// Window data
		this.setSize(WIDTH, HEIGHT);
		this.setLocationRelativeTo(null);
		this.setTitle("Truco");
		this.setLayout(null);
		
		// textArea data
		this.chatPane = new JTextPane();
		this.chatPane.setSize(WIDTH - 40, HEIGHT - 500);
		this.chatPane.setLocation(10, HEIGHT - 200);
		this.chatPane.setEditable(false);
		
		// scrollPane data
		this.scrollPane = new JScrollPane(chatPane);
		this.scrollPane.setSize(WIDTH - 40, HEIGHT - 500);
		this.scrollPane.setLocation(10, HEIGHT - 200);
		this.scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		// textArea data
		this.messagePane = new JTextPane();
		this.messagePane.setSize(WIDTH - 40, HEIGHT - 575);
		this.messagePane.setLocation(10, HEIGHT - 75);
		this.messagePane.setEditable(false);

		this.add(this.scrollPane);
		this.add(this.messagePane);
		
		initializeEvents();
		
	}
	
	
	public void initializeEvents() {
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
		
		this.messagePane.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				try {
					//Se o usuário apertar o enter, enviar a mensagem para o servidor, para que o mesmo envie a mensagem para todos os usuários
						if (e.getKeyCode()==KeyEvent.VK_ENTER) {
							Communication.getInstance().sendMessage(Messages.CHAT);
							Communication.getInstance().sendMessage(messagePane.getText().replaceAll("\n", ""));
							messagePane.setText(null);
						}
					} catch (UnknownHostException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			}

			@Override
			public void keyTyped(KeyEvent e) {}
		});
	}
	
	public void showMessage(String msg) {
		this.chatPane.setText(this.chatPane.getText() + msg);
	}
	
}
