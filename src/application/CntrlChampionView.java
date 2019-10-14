package application;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Observable;
import java.util.Observer;

import javafx.animation.Animation;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class CntrlChampionView implements Observer {
	@FXML
	FlowPane fpGetRekt, fpThreats, fpNotes, fpButtons;
	@FXML
	Label lbChamp;
	@FXML
	Button btnDelete;
	@FXML
	GridPane gridshit;

	ScaleTransition st;
	RotateTransition rt;

	@FXML
	public void initialize() {
		Main.curChamp.addObserver(this);
	}

	public void startAnimation(Pane p) {
		for (Node n : p.getChildren()) {
			if (n instanceof Pane) {
				startAnimation((Pane) n);
			} else {
				anmt360andGrow(n).play();
			}
		}
	}


	@Override
	public void update(Observable o, Object arg) {
		// Champ
		lbChamp.setText(Main.curChamp.champ.name);
		lbChamp.setFont(Font.font(35));
		fpGetRekt.getChildren().clear();

		// Rekt
		Label lb = new Label("Recks");
		lb.setId("header");
		lb.prefWidthProperty().bind(fpButtons.widthProperty());
		lb.setAlignment(Pos.CENTER);
		lb.setFont(Font.font(25));
		lb.setPrefHeight(lbChamp.getHeight());
		fpGetRekt.getChildren().add(lb);
		for (ChampRating cr : Main.curChamp.champ.deezKidsGetRekt) {
			HBox hb = new HBox();
			hb.setId("hbox");
			hb.setSpacing(12);
			hb.setAlignment(Pos.CENTER);
			hb.prefWidthProperty().bind(fpButtons.widthProperty());
			Button b = new Button(cr.name);
			b.setPrefHeight(25);
			b.setId("recks");
			for (Champ c : Main.champs) {
				if (c.name.equals(cr.name)) {
					b.setOnAction(event -> Main.openChamp(c));
				}
			}
			lb = new Label(new DecimalFormat("##.#").format(cr.getRating()));
			lb.setId("rating");
			lb.setPrefHeight(25);
			lb.setPrefWidth(25);
			lb.setAlignment(Pos.CENTER);
			hb.getChildren().addAll(b, lb);
			fpGetRekt.getChildren().add(hb);
		}

		// Threats
		fpThreats.getChildren().clear();
		lb = new Label("Threats");
		lb.setId("header");
		lb.setAlignment(Pos.CENTER);
		lb.prefWidthProperty().bind(fpButtons.widthProperty());
		lb.setFont(Font.font(25));
		lb.setPrefHeight(lbChamp.getHeight());
		fpThreats.getChildren().add(lb);
		for (ChampRating cr : Main.curChamp.champ.threats) {
			HBox hb = new HBox();
			hb.setSpacing(12);
			hb.setAlignment(Pos.CENTER);
			hb.prefWidthProperty().bind(fpButtons.widthProperty());
			Button b = new Button(cr.name);
			b.setId("threat");
			b.setPrefHeight(25);
			for (Champ c : Main.champs) {
				if (c.name.equals(cr.name)) {
					b.setOnAction(event -> Main.openChamp(c));
				}
			}
			lb = new Label(new DecimalFormat("##.#").format(cr.getRating()));
			lb.setAlignment(Pos.CENTER);
			lb.setPrefHeight(25);
			lb.setPrefWidth(25);
			lb.setId("rating");
			hb.getChildren().addAll(b, lb);
			fpThreats.getChildren().add(hb);
		}

		// Notes
		fpNotes.getChildren().clear();
		for (String str : Main.curChamp.champ.notes) {
			lb = new Label(str);
			lb.setAlignment(Pos.CENTER);
			lb.setId("note");
			lb.setWrapText(true);
			lb.setMaxWidth(700);
			fpNotes.getChildren().add(lb);
		}
		if (Main.curChamp.champ.notes.size() == 0)
			btnDelete.setDisable(true);
		else
			btnDelete.setDisable(false);
	}

	public void backToOverview(Event event) {
		Main.screenController.activate("overview");
	}

	public void addReck() throws IOException {
		Stage popup = new Stage();
		popup.initModality(Modality.APPLICATION_MODAL);
		Scene scene = new Scene(FXMLLoader.load(getClass().getResource("addReckPopup.fxml")));
		scene.getStylesheets().add(getClass().getResource("league.css").toExternalForm());
		popup.setScene(scene);
		popup.setTitle("Add a " + Main.curChamp.champ.name + " recks ...");
		popup.show();
	}

	public void addThreat() throws IOException {
		Stage popup = new Stage();
		popup.initModality(Modality.APPLICATION_MODAL);
		Scene scene = new Scene(FXMLLoader.load(getClass().getResource("addThreatPopup.fxml")));
		scene.getStylesheets().add(getClass().getResource("league.css").toExternalForm());
		popup.setScene(scene);
		popup.setTitle("Add a threat to " + Main.curChamp.champ.name);
		popup.show();
	}

	public void addNote() throws IOException {
		Stage popup = new Stage();
		popup.initModality(Modality.APPLICATION_MODAL);
		Scene scene = new Scene(FXMLLoader.load(getClass().getResource("addNotePopup.fxml")));
		scene.getStylesheets().add(getClass().getResource("league.css").toExternalForm());
		popup.setScene(scene);
		popup.setTitle("Add a threat to " + Main.curChamp.champ.name);
		popup.show();
	}

	public void deleteNote() throws IOException {
		Stage popup = new Stage();
		popup.initModality(Modality.APPLICATION_MODAL);
		Scene scene = new Scene(FXMLLoader.load(getClass().getResource("deleteNotePopup.fxml")));
		scene.getStylesheets().add(getClass().getResource("league.css").toExternalForm());
		popup.setScene(scene);
		popup.setTitle("Remove a Note");
		popup.show();
	}

	public void editRoles() throws IOException {
		Stage popup = new Stage();
		popup.initModality(Modality.APPLICATION_MODAL);
		Scene scene = new Scene(FXMLLoader.load(getClass().getResource("editRolesPopup.fxml")));
		scene.getStylesheets().add(getClass().getResource("league.css").toExternalForm());
		popup.setScene(scene);
		popup.setTitle("Edit Roles");
		popup.show();
	}
		
	private ParallelTransition anmt360andGrow(Node n) {
		ParallelTransition pt = new ParallelTransition(n, anmt360(n), anmtGrow(n)); 
		return pt;
	}
	
	private RotateTransition anmt360(Node n) {
		rt = new RotateTransition(Duration.millis(800));
		rt.setNode(n);
		rt.setFromAngle(0);
		rt.setToAngle(360);
		return rt;
	}
	
	private ScaleTransition anmtGrow(Node n) {
		st = new ScaleTransition(Duration.millis(800));
		st.setFromX(0);
		st.setFromY(0);
		st.setToX(1);
		st.setToY(1);
		st.setNode(n);
		return st;
	}
}
