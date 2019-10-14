package application;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CntrlAddReck {
	@FXML
	TextField tfName;
	@FXML
	Spinner<Integer> spnRating;

	public void finish(Event event) {
		boolean found = false;
		for (ChampRating cr : Main.curChamp.champ.deezKidsGetRekt) {
			if (cr.name.equals(tfName.getText())) {
				cr.addRating(spnRating.getValueFactory().getValue().intValue());
				found = true;
			}
			
		}
		if(!found) {
			Main.curChamp.champ.deezKidsGetRekt
				.add(new ChampRating(tfName.getText(), spnRating.getValueFactory().getValue().doubleValue(), 1));
		}
		found = false;
		
		for (Champ c : Main.champs) {
			if (c.name.equals(tfName.getText())) {
				for (ChampRating cr : c.threats) {
					if (cr.name.equals(Main.curChamp.champ.name)) {
						cr.addRating(spnRating.getValueFactory().getValue().intValue());
						found = true;
					}
				}
				if (!found)
					c.threats.add(new ChampRating(Main.curChamp.champ.name,
							spnRating.getValueFactory().getValue().doubleValue(), 1));
			}
		}
		Main.openChamp(Main.curChamp.champ);
		((Stage)((Node)event.getSource()).getScene().getWindow()).close();
	}
}
