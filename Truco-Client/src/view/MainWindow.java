package view;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;

public class MainWindow extends JFrame {

	private static final int WIDTH = 600;
	private static final int HEIGHT = 600;
	
	private JScrollPane scrollPane;
	private JList<String> list;
	
	private JLabel infoLabel;
	private JButton playRoom;
	
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
	
	private void initializeComponents() {
		
		// Window data
		this.setSize(WIDTH, HEIGHT);
		this.setLocationRelativeTo(null);
		this.setTitle("Truco");
		this.setLayout(null);
		
		// List data
		this.list = new JList<>();
		this.list.setSize(560, 400);
		this.list.setLocation(0, 0);
		
		// Scroll pane data
		this.scrollPane = new JScrollPane(this.list);
		this.scrollPane.setLayout(null);
		this.scrollPane.setSize(560, 400);
		this.scrollPane.setLocation(10, 50);
		this.scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		// Label Data
		this.infoLabel = new JLabel("Rooms:");
		this.infoLabel.setSize(100, 30);
		this.infoLabel.setLocation(10, 10);
		
		
		// Button Data
		this.playRoom = new JButton("Play!");
		this.playRoom.setSize(200, 50);
		this.playRoom.setLocation(370, 500);
		this.playRoom.setEnabled(false);
		
		this.add(this.infoLabel);
		this.add(scrollPane);
		this.add(this.playRoom);
		
	}
	
	
	public void initializeEvents() {
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
	}
	
}
