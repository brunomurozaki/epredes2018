package misc;

import java.io.IOException;

import controller.ApplicationController;

public class Main {

	private static boolean keepAlive = true;
	
	public static void main(String[] args) throws IOException {
		ApplicationController.getInstance().startApp();
	}
}
