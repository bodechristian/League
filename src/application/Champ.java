package application;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Button;

public class Champ {
	public String name;
	public List<ChampRating> deezKidsGetRekt = new ArrayList<ChampRating>();
	public List<ChampRating> threats = new ArrayList<ChampRating>();
	public List<String> notes = new ArrayList<String>();
	public List<Roles> roles = new ArrayList<Roles>();
	public Button b;
	
	public Champ(String name) {
		this.name = name;
	}
	
	public Champ(String name, List<ChampRating> deezKidsGetRekt, List<ChampRating> threats, List<String> notes, Button b) {
		this.name = name;
		this.deezKidsGetRekt = deezKidsGetRekt;
		this.threats = threats;
		this.notes = notes;
		this.b = b;
	}
}
