package application;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CntrlAddChamp {
	@FXML
	TextField tfName;
	public void finish(Event event) {
		Main.champs.add(new Champ(tfName.getText()));
		((Stage)((Node)event.getSource()).getScene().getWindow()).close();
	}
}
