package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.effect.MotionBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class CntrlOverview {
	@FXML
	StackPane overviewWindow;
	@FXML
	ComboBox<Roles> cbRoles;
	@FXML
	HBox hbTools;

	private boolean firstTime = true;

	@FXML
	public void initialize() throws IOException {
		makeSaveButton();
		readChamps();

		// set checkbox values
		cbRoles.getItems().addAll(Roles.values());
		cbRoles.setValue(Roles.ALL);
	}

	private void makeSaveButton() {
		// save button
		Button btnSave = new Button("Save");
		btnSave.setId("button");
		btnSave.setPrefHeight(25);
		btnSave.setOnAction(event -> {
			try {
				Main.writeChampsToFile();
				System.out.println(overviewWindow.getChildren().get(0).getLayoutX() + " & "
						+ overviewWindow.getChildren().get(0).getLayoutY());
				System.out.println(overviewWindow.getChildren().get(1).getLayoutX() + " & "
						+ overviewWindow.getChildren().get(1).getLayoutY());
				System.out.println(overviewWindow.getChildren().get(14).getLayoutX() + " & "
						+ overviewWindow.getChildren().get(14).getLayoutY());
				System.out.println(overviewWindow.getChildren().get(18).getLayoutX() + " & "
						+ overviewWindow.getChildren().get(18).getLayoutY());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		hbTools.getChildren().add(btnSave);
	}

	/**
	 * reads the .txt with the champs and fills the list "champs" in Main that means
	 * making buttons, filling out the champ -name -threats -recks -notes
	 * 
	 * @throws IOException
	 */
	private void readChamps() throws IOException {
		// read the things from file and make champs
		BufferedReader reader = new BufferedReader(new FileReader(new File("src/icons/champs.txt")));
		String line = reader.readLine();
		while (line != null) {
			String[] values = line.split(";");
			Champ tempChamp = new Champ(values[0]);

			String[] croles = values[1].split(",");
			for (String str : croles) {
				if (!str.equals("")) {
					tempChamp.roles.add(Roles.valueOf(str.toUpperCase()));
				}
			}

			String[] recks = values[2].split(",");
			for (String str : recks) {
				if (!str.equals("")) {
					String[] sth = str.split("/");
					tempChamp.deezKidsGetRekt
							.add(new ChampRating(sth[0], Double.parseDouble(sth[1]), Integer.parseInt(sth[2])));
				}
			}

			String[] threats = values[3].split(",");
			for (String str : threats) {
				if (!str.equals("")) {
					String[] sth = str.split("/");
					tempChamp.threats
							.add(new ChampRating(sth[0], Double.parseDouble(sth[1]), Integer.parseInt(sth[2])));
				}
			}

			String[] notes = values[4].split("/");
			for (String str : notes) {
				if (!str.equals("")) {
					tempChamp.notes.add(str);
				}
			}

			Button b = new Button();
			try {
				Image img = new Image(CntrlOverview.class.getResourceAsStream("/icons/" + tempChamp.name + ".png"), 50,
						50, true, true);
				b.setGraphic(new ImageView(img));
			} catch (NullPointerException e) {
				System.out.println("No pic for " + tempChamp.name);
			}
			b.setId("button");
			b.setOnAction(eventt -> Main.openChamp(tempChamp));
			tempChamp.b = b;

			Main.champs.add(tempChamp);
			line = reader.readLine();
		}
		reader.close();
	}

	public void cbChanged() {
		overviewWindow.getChildren().clear();

		int index = 0;
		for (Champ c : Main.champs) {
			if (c.roles.contains(cbRoles.getValue())
					&& c.name.toLowerCase().startsWith(Main.nameSelector.toLowerCase())) {
				overviewWindow.getChildren().add(c.b);
				/**
				 * 14 buttons in a row
				 * 68 button width with 5 spacing
				 * 1080 pane width
				 * 60 button height with 5 spacing
				 * 700 pane height
				 */
				if (Main.screenController.leftOverview) {
					translateFromMiddle(c.b, 0, 0, (index % 14 * (68 + 5)) - (1080 / 2 - 68 / 2),
							((int) Math.floor(index / 14) * (60 + 5)) - (700 / 2 - 60 / 2));
				} else {
					translateFromMiddle(c.b, (index % 14 * (68 + 5)) - (1080 / 2 - 68 / 2),
							((int) Math.floor(index / 14) * (60 + 5)) - (700 / 2 - 60 / 2));
				}
				index++;
			}
		}
		Main.screenController.leftOverview = false;
		firstTime = false;
	}
	
	
	
	/**
	 * ANIMATIONS & EFFECTS
	 */
	private void translateFromMiddle(Button b, int toX, int toY) {
		TranslateTransition tt = new TranslateTransition(Duration.millis(800));
		tt.setToX(toX);
		tt.setToY(toY);
		tt.setNode(b);
		fadeInEffect(b);
		tt.setInterpolator(Interpolator.EASE_OUT);
		tt.play();
	}

	private void translateFromMiddle(Button b, int fromX, int fromY, int toX, int toY) {
		TranslateTransition tt = new TranslateTransition(Duration.millis(800));
		tt.setFromX(fromX);
		tt.setFromY(fromY);
		tt.setToX(toX);
		tt.setToY(toY);
		tt.setNode(b);
		fadeInEffect(b);
		tt.setInterpolator(Interpolator.EASE_OUT);
		tt.play();
	}

	private void spawnAfterEachOther(List<Button> lob) {
		Timeline tl = new Timeline(new KeyFrame(Duration.millis(500 / (lob.size() + 1)), ae -> {
			if (!lob.isEmpty()) {
				Button b = lob.remove(0);
				overviewWindow.getChildren().add(b);
			}
		}));
		tl.setCycleCount(lob.size());
		tl.play();
	}

	private void motionBlur(Button b) {
		MotionBlur mb = new MotionBlur();
		mb.setRadius(60);
		b.setEffect(mb);
		Timeline tlmb = new Timeline(new KeyFrame(Duration.millis(200), new KeyValue(mb.radiusProperty(), 0)));
		tlmb.play();
	}

	private void fadeInEffect(Button b) {
		FadeTransition ft = new FadeTransition(Duration.millis(2000));
		ft.setFromValue(0.1f);
		ft.setToValue(1f);
		ft.setNode(b);
		ft.play();
	}
}
