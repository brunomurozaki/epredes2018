package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import util.Messages;

public class Communication {

	private Socket tcpSocket;
	private DatagramSocket udpSocket;

	private static final int TCP_SERVER_PORT = 6789;
	private static final int UDP_SERVER_PORT = 9876;

	private BufferedReader reader;
	private PrintWriter writer;
	private static Communication instance;

	private Thread receiveTCPThread = null;
	private Thread askForListThread = null;

	private boolean isRunning = true;

	public Communication() throws UnknownHostException, IOException {
		tcpSocket = new Socket(InetAddress.getByName("127.0.0.1"), TCP_SERVER_PORT);
		udpSocket = new DatagramSocket();
		reader = new BufferedReader(new InputStreamReader(tcpSocket.getInputStream()));
		writer = new PrintWriter(tcpSocket.getOutputStream());

		startListeningMessages();
	}

	// Envio mensagem de insercao de usuario no server
	public void registerUser(String name) {
		sendMessage(Messages.INSERT);
		sendMessage(name);
	}
	
	// Envio de mensagem de espera de sala
	public void enterRoom() {
		sendMessage(Messages.ROOM);
	}

	// Envio uma mensagem qualquer ao server
	public void sendMessage(String message) {
		writer.println(message);
		writer.flush();
	}

	// Encerra a thread de recebimento de mensagem
	public void stopCommunication() {
		sendMessage(Messages.DISCONNECT);
		isRunning = false;
	}

	// Escuta as mensagens vindas do servidor
	private void startListeningMessages() {
		startTCPListening();
		startUDPListening();
	}

	// Thread que a cada 100 milisegundos envia um pedido ao Server da lista
	// por meio de uma mensagem UDP
	private void startUDPListening() {
		if (askForListThread != null) {
			System.err.println("Thread UDP de receber mensagens ja iniciada");
			return;
		}

		askForListThread = new Thread("UDP Thread") {
			@Override
			public void run() {
				super.run();

				byte[] sendedMessage = Messages.SEND_ONLINE_USERS.getBytes();
				byte[] receiveMessage = new byte[256];
				DatagramPacket sendPacket, receivePacket;

				while (isRunning) {
					try {
						Thread.sleep(1000);
						sendPacket = new DatagramPacket(sendedMessage, sendedMessage.length,
								InetAddress.getByName("127.0.0.1"), UDP_SERVER_PORT);
						
						udpSocket.send(sendPacket);
						
						receivePacket = new DatagramPacket(receiveMessage, receiveMessage.length);
						
				        udpSocket.receive(receivePacket);
				        
				        String received = new String(
				        		receivePacket.getData(), 0, receivePacket.getLength());
				        ApplicationController.getInstance().updateOnlineList(received);
						
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};

		askForListThread.start();
	}

	private void startTCPListening() {
		if (receiveTCPThread != null) {
			System.err.println("Thread de receber mensagens ja iniciada");
			return;
		}

		receiveTCPThread = new Thread("ReceiveThread") {
			@Override
			public void run() {
				super.run();
				try {
					while (isRunning) {
						String message = reader.readLine();

						if (message.equals(Messages.INSERT)) {
							String res = reader.readLine();
							loginTreatment(res);
						} else if(message.equals(Messages.ROOM)) {
							String res = reader.readLine();
							roomTreatment(res);
						} else if(message.equals(Messages.START_GAME)) {
							String names = reader.readLine();
							startGameTreatment(names);
						} else if (message.equals(Messages.CHAT)) {
							String res = reader.readLine();
							ApplicationController.getInstance().showMessage(res + "\n");
						} else {
							if (message.equals(Messages.PLAY)) {
								String res = reader.readLine();
								ApplicationController.getInstance().showMessage(res + "\n");
								ApplicationController.getInstance().enableChat();
							}
						}
					}

					tcpSocket.close();
				} catch (IOException ex) {

				}
			}
		};

		receiveTCPThread.start();
	}
	
	private void startGameTreatment(String names) {
		ApplicationController.getInstance().initGame(names);
	}
	
	private void roomTreatment(String res) {
		int number = Integer.parseInt(res);
		
		ApplicationController.getInstance().changeWaitingMessage(number);
	}
	
	private void loginTreatment(String res) {
		if (res.equals(Messages.ACK)) {
			ApplicationController.getInstance().successfulLogin();
		} else if (res.equals(Messages.NOK)) {
			ApplicationController.getInstance().failedLogin();
		} else {
			System.err.println("Protocolo invalido!");
		}
	}

	public static Communication getInstance() throws UnknownHostException, IOException {
		if (instance == null) {
			instance = new Communication();
		}
		return instance;
	}

}
