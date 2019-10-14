package application;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;

public class CntrlDeleteNote {
	@FXML
	Spinner<Integer> spn;
	
	@FXML
	public void initialize() {
		((SpinnerValueFactory.IntegerSpinnerValueFactory)(spn.getValueFactory())).setMax(Main.curChamp.champ.notes.size()-1);
	}
	
	public void finish(Event event) {
		Main.curChamp.champ.notes.remove(spn.getValueFactory().getValue().intValue());
		Main.openChamp(Main.curChamp.champ);
		((Stage)((Node)event.getSource()).getScene().getWindow()).close();
	}
}
