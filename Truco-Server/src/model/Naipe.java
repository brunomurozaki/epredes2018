package model;

public enum Naipe {

	ouro(1), espadas(2), copas(3), paus(4);

	private int ranking;

	private Naipe(int ranking) {

		this.ranking = ranking;
	}

	public int getRanking() {
		return ranking;
	}
}
