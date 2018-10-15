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
	
	public char getTranslatedCard() {
		switch(ranking) {
		case 1:
			return '4';
		case 2:
			return '5';
		case 3:
			return '6';
		case 4:
			return '7';
		case 5: 
			return 'Q';
		case 6: 
			return 'J';
		case 7:
			return 'K';
		case 8:
			return 'A';
		case 9: 
			return '2';
		default: 
			return '3';
		}
	}
}
