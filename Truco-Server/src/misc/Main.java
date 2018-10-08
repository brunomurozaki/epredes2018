package misc;

import java.io.IOException;
import java.util.Scanner;

import controller.ApplicationController;
import controller.Client;
import controller.Server;
import view.MainWindow;

public class Main {

	private static boolean keepAlive = true;
	
	public static void main(String[] args) throws IOException {
		
		
		ApplicationController.getInstance().startApp();
		
		/*System.out.println("Server started!");

		Scanner sc = new Scanner(System.in);
		String code = "";
		
		try {
			while(keepAlive) {
				code = sc.nextLine();
				
				if(code.equals("start")) {
					Server srv = Server.getInstance();
					srv.startServer();
					
				} else if(code.equals("list")) {
					System.out.println("Temos um total de " + Server.getInstance().getClientList().size() + " clientes conectados");
				} else if(code.equals("end")) {
					keepAlive = false;
					Server.getInstance().stopServer();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sc.close();
		*/
	}
}
