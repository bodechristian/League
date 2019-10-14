package application;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CntrlEditRoles {
	@FXML
	VBox vb;

	@FXML
	public void initialize() {
		for (Roles role : Roles.values()) {
			if (!role.equals(Roles.ALL)) {
				CheckBox cb = new CheckBox(role.toString());
				cb.setId("checkbox");
				if (Main.curChamp.champ.roles.contains(role)) {
					cb.setSelected(true);
				}
				vb.getChildren().add(cb);
			}
		}
		Button b = new Button("Done");
		b.setPrefHeight(30);
		b.setId("button");
		b.setOnAction(event -> finish(event));
		vb.getChildren().add(b);
	}

	public void finish(Event event) {
		Main.curChamp.champ.roles.clear();
		for (Node n : vb.getChildren()) {
			if (n.getClass().equals(CheckBox.class)) {
				if (((CheckBox) n).isSelected()) {
					Main.curChamp.champ.roles.add(Roles.valueOf(((CheckBox) n).getText().toUpperCase()));
				}
			}
		}
		Main.openChamp(Main.curChamp.champ);
		((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
	}
}
