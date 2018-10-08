package view;

import java.awt.Color;
import java.awt.Toolkit;import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import controller.ApplicationController;

public class MainWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int WIDTH = 500;
	private static final int HEIGHT = 500;

	private JButton startServerBt;
	private JScrollPane scrollPane;
	private JTextPane textArea;

	public MainWindow() {
		initializeComponents();
		logData("Program started!");
	}
	
	public void startWindow() {
		this.setVisible(true);
	}

	private void initializeComponents() {
		// Window data
		this.setSize(WIDTH, HEIGHT);
		this.setLayout(null);
		this.setLocationRelativeTo(null);
		
		// Button data
		this.startServerBt = new JButton("Start Server");
		this.startServerBt.setSize(150, 50);
		this.startServerBt.setLocation(175, 400);
		
		// textArea data
		this.textArea = new JTextPane();
		this.textArea.setSize(WIDTH - 40, HEIGHT - 125);
		this.textArea.setLocation(0, 0);
		this.textArea.setEditable(false);
		
		// scrollPane data
		this.scrollPane = new JScrollPane(textArea);
		this.scrollPane.setSize(WIDTH - 40, HEIGHT - 125);
		this.scrollPane.setLocation(10, 10);
		this.scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		//this.scrollPane.add(this.textArea);
		this.add(this.scrollPane);
		this.add(this.startServerBt);
		
		initializeWindowEvents();
		initializeComponentEvents();
	}
	
	public void logErrorData(String data) {
		StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.RED);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);
        
        this.textArea.setCharacterAttributes(aset, false);
        logData(data);
        
        aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.BLACK);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);
        
        this.textArea.setCharacterAttributes(aset, false);
        
	}
	
	public void logData(String data) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		
		String log = dateFormat.format(date) + " - " + data + "\n";
		this.textArea.setText(this.textArea.getText() + log);
	}
	
	private void initializeWindowEvents() {
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				super.windowClosing(e);
				System.exit(0);
			}
		});
	}

	private void initializeComponentEvents() {
		this.startServerBt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					ApplicationController.getInstance().startServer();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}
}
