package misc;

import java.io.IOException;
import java.util.Scanner;

import controller.Communication;

public class Main {

	private static boolean isRunning = true;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
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
				} else {
					Communication.getInstance().sendMessage(code);
				}
			}
			
			sc.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
