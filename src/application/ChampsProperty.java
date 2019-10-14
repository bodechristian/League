package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class ChampsProperty extends Observable {
	public List<Champ> champs;
	
	public ChampsProperty() {
		champs = new ArrayList<Champ>();
	}
	
	public void addChamp(Champ champ) {
		champs.add(champ);
		setChanged();
		notifyObservers(champ);
	}
}
