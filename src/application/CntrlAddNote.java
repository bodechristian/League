package application;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class CntrlAddNote {
	@FXML
	TextArea taNote;
	public void finish(Event event) {
		Main.curChamp.champ.notes.add(taNote.getText());
		Main.openChamp(Main.curChamp.champ);
		((Stage)((Node)event.getSource()).getScene().getWindow()).close();
	}
}
