package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import controller.ApplicationController;

public class CardPanel extends JPanel{

	private Image image;
	private static final int WIDTH = 60;
	private static final int HEIGHT = 90;
	private String name;
	private boolean isDealer;
	
	public CardPanel(String name, boolean defaultCard, boolean isDealer) {
		try {
			this.isDealer = isDealer;
			this.name = name;
			
			if(defaultCard)
				image = ImageIO.read(new File("resources/red_back.png"));
			else
				image = ImageIO.read(new File("resources/" + name + ".png"));
			
			this.setBackground(Color.GREEN);
			
			if(isDealer) {
				this.setSize(WIDTH-20, HEIGHT-20);
			} else {
				this.setSize(WIDTH, HEIGHT);
			}
			
			image = image.getScaledInstance(this.getWidth(),this.getHeight(),Image.SCALE_SMOOTH);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		initializeEvents();
		System.out.println("CardPanel: " + name);
		
	}
	
	private void initializeEvents() {
		if(!isDealer) {
			this.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					super.mouseClicked(e);
					//System.out.println(name);
					ApplicationController.getInstance().play(name);
				}
			});	
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		g.drawImage(image, 0, 0, this);
	}
	
}
