package model;

public class Carta {

	Valor valor;
	Naipe naipe;

	public Carta(Valor valor, Naipe naipe) {
		super();
		this.valor = valor;
		this.naipe = naipe;
	}

	public Naipe getNaipe() {
		return naipe;
	}

	public void setNaipe(Naipe naipe) {
		this.naipe = naipe;
	}

	public Valor getValor() {
		return valor;
	}

	public void setValor(Valor valor) {
		this.valor = valor;
	}

	@Override
	public String toString() {

		StringBuilder nomeCarta = new StringBuilder();

		nomeCarta.append(getValor());
		nomeCarta.append(" de ");
		nomeCarta.append(getNaipe());

		return nomeCarta.toString();
	}

}
