package misc;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import controller.ApplicationController;

public class Main {
	
	public static void main(String[] args) {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ApplicationController.getInstance().startApp();
	}

	/*
	 * TODO: 
	 * - Truco
	 * - Mensagem de fim de jogo
	 * - ini
	 * */
	
}
