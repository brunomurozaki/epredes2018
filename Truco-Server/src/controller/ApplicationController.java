package controller;

import java.io.IOException;

import view.MainWindow;

public class ApplicationController {

	private MainWindow mainWindow;
	private Server server;
	
	private static ApplicationController instance;
	
	public ApplicationController() throws IOException {
		mainWindow = new MainWindow();
		server = Server.getInstance();
	}
	
	public void startApp() {
		mainWindow.startWindow();
	}
	
	public void startServer() throws IOException {
		logData("Starting the server...");
		if(server.startServer())
			logData("Server started!");
		else 
			logData("Error on starting the server...");
	}
	
	public void stopServer() throws IOException {
		server.stopServer();
	}
	
	public void logData(String data) {
		this.mainWindow.logData(data);
	}
	
	
	
	public static ApplicationController getInstance() throws IOException {
		if(instance == null)
			instance = new ApplicationController();
		return instance;
	}
}