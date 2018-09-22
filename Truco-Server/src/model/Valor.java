package model;

public enum Valor {

	quatro(1), cinco(2), seis(3), sete(4), dama(5), valete(6), rei(7), as(8), dois(9), tres(10);

	private int ranking;

	private Valor(int ranking) {

		this.ranking = ranking;
	}
	
	public int getRanking() {
		return ranking;
	}
}
