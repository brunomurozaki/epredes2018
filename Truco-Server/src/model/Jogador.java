package model;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Jogador {

	private String nome;
	private ArrayList<Carta> cartas;
	private Socket socket;
	private DataOutputStream outToClient;
	private BufferedReader inFromCliente;

	public Jogador(String nome, Socket socket, DataOutputStream outToClient, BufferedReader inFromClient) {

		this.nome = nome;
		cartas = new ArrayList<>();
		this.outToClient = outToClient;
		this.inFromCliente = inFromClient;
	}

	public void recebeCarta(Carta carta) {

		cartas.add(carta);
	}

	public String getNome() {

		return nome;
	}

	@Override
	public String toString() {

		return nome + ": " + cartas;
	}

	public String getCartas() {

		return cartas.toString();
	}

	public Carta removeCarta(Carta carta) {
		
		//cartas.remove(carta);
		return cartas.remove(0);
	}
	
	public void entregarCartas() {
		
		cartas.clear();
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public DataOutputStream getOutToClient() {
		return outToClient;
	}

	public void setOutToClient(DataOutputStream outToClient) {
		this.outToClient = outToClient;
	}

	public BufferedReader getInFromCliente() {
		return inFromCliente;
	}

	public void setInFromCliente(BufferedReader inFromCliente) {
		this.inFromCliente = inFromCliente;
	}
}
