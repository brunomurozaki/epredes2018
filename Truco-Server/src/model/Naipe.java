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
	
	public char getNaipeChar() {
	
		switch(ranking) {
		case 1:
			return 'D';
		case 2:
			return 'S';
		case 3:
			return 'H';
		default:
			return 'C';
		}
	}
}
