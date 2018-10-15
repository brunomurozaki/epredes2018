package misc;

import java.io.IOException;
import java.util.Scanner;

import controller.ApplicationController;
import controller.Communication;

public class Main {

	private static boolean isRunning = true;
	
	public static void main(String[] args) {
		ApplicationController.getInstance().startApp();
		//ApplicationController.getInstance().startMainWindow();
		//ApplicationController.getInstance().waitingRoom();
	}

}
