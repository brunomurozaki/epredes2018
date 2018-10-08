package misc;

import java.io.IOException;
import java.util.Scanner;

import controller.ApplicationController;
import controller.Communication;

public class Main {

	private static boolean isRunning = true;
	
	public static void main(String[] args) {

		ApplicationController.getInstance().startApp();

		/*try {
			System.out.println("Start program: ");
			
			Scanner sc = new Scanner(System.in);
			String code = "";
			
			while(isRunning) {
				code = sc.nextLine();
			
				if(code.equals("end")) {
					Communication.getInstance().stopCommunication();
					isRunning = false;
				} else if (code.equals("connect")) {
					Communication.getInstance();
				} else if(code.equals("register")) {
					Communication.getInstance().registerUser(sc.nextLine());
				} else {
					//Communication.getInstance().sendMessage(code);
				}
			}
			
			sc.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

}
