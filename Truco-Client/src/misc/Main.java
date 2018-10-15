package misc;

import java.io.IOException;
import java.util.Scanner;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import controller.ApplicationController;
import controller.Communication;

public class Main {

	private static boolean isRunning = true;
	
	public static void main(String[] args) {
		ApplicationController.getInstance().startApp();
		//ApplicationController.getInstance().startMainWindow();
		//ApplicationController.getInstance().waitingRoom();
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//ApplicationController.getInstance().initGame("Fulano;Beltrano;Ciclano;Eu");
	}

}
