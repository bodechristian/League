package application;

import java.util.Observable;

public class ChampProperty extends Observable {
	public Champ champ;
	
	public ChampProperty() {
		this.champ = new Champ("");
	}
	
	public void setChamp(Champ champ) {
		this.champ = champ;
		setChanged();
		notifyObservers();
	}
}
