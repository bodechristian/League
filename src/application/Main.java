package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class Main extends Application {
	public static ScreenController screenController;
	public static Stage stage;
	public static List<Champ> champs = new ArrayList<Champ>();
	public static List<Button> lob = new ArrayList<Button>();
	public static ChampProperty curChamp = new ChampProperty();
	public static String nameSelector = "";
	public static FXMLLoader cntrlOverview = new FXMLLoader();
	public static FXMLLoader cntrlChampview = new FXMLLoader();
	public static Timeline tlSelector;

	public static void main(String[] args) {
		launch(args);
	}

	public static void openChamp(Champ champ) {
		curChamp.setChamp(champ);
		screenController.activate("championview");
	}

	public static String[][] colorPalette = { { "acceptGreen", "#276128" }, { "afkRed", "#9C3213" },
			{ "bgLightBlue", "#001D34" }, { "bgDarkBlueGOOD", "#020D19" }, { "bgDarkGreenMEH", "#121B1A" },
			{ "bgLightGreenSHIT", "#223424" }, { "fontLightBlue", "#09C7E3" }, { "writingWhite", "#F0E6D2" },
			{ "borderGold", "#785A28" }, { "msgYellow", "#C89B3C" }, { "borderBlue", "#248A99" },
			{ "btnGrey", "#1E2327" }, { "fontYellow", "#EDBD2B" }, { "fontBlue", "#A8C4C7" },
			{ "fontWhite", "#B2B09B" } };

	@Override
	public void start(Stage primaryStage) throws IOException {
		tlSelector = new Timeline(new KeyFrame(Duration.seconds(2)));
		tlSelector.setOnFinished(event -> {
			nameSelector = "";
			((CntrlOverview) cntrlOverview.getController()).cbChanged();
		});

		Scene scene = new Scene(cntrlOverview.load(getClass().getResource("Overview.fxml").openStream()));
		scene.getStylesheets().add(getClass().getResource("league.css").toExternalForm());
		scene.setOnKeyPressed(event -> {
			if (event.getCode().isLetterKey() && screenController.isActivated("overview")) {
				nameSelector += (event.getCode().toString().toLowerCase());
				((CntrlOverview) cntrlOverview.getController()).cbChanged();
				if (tlSelector.getStatus().equals(Animation.Status.RUNNING))
					tlSelector.jumpTo(Duration.ZERO);
				else
					tlSelector.play();
			}
		});

		primaryStage.setScene(scene);
		stage = primaryStage;
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				try {
					writeChampsToFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.exit(0);
			}
		});
		screenController = new ScreenController(scene);
		screenController.addScreen("overview", cntrlOverview.getRoot());
		screenController.addScreen("championview", cntrlChampview.load(getClass().getResource("ChampionView.fxml").openStream()));
		// delay first time activate to wait for everything to load, and so that the user can see the animation
		(new Timeline(new KeyFrame(Duration.millis(1000), e -> screenController.activate("overview")))).play();
		primaryStage.show();
		resize(stage);
	}

	public static void writeChampsToFile() throws IOException {
		BufferedWriter f = new BufferedWriter(new FileWriter(new File("src/icons/champs.txt")));
		for (Champ c : champs) {
			f.write(c.name);
			f.write(";");

			boolean first = true;
			for (Roles role : c.roles) {
				if (!first)
					f.write(",");
				else
					first = false;
				f.write(role.toString());
			}
			f.write(";");

			first = true;
			for (ChampRating cr : c.deezKidsGetRekt) {
				if (!first)
					f.write(",");
				else
					first = false;
				f.write(cr.name + "/" + cr.rating + "/" + cr.noRatings);
			}
			f.write(";");

			first = true;
			for (ChampRating cr : c.threats) {
				if (!first)
					f.write(",");
				else
					first = false;
				f.write(cr.name + "/" + cr.rating + "/" + cr.noRatings);
			}
			f.write(";");

			first = true;
			for (String str : c.notes) {
				if (!first)
					f.write("/");
				else
					first = false;
				f.write(str);
			}
			f.write(";");
			f.write("end");
			f.newLine();
		}
		f.close();
	}

	public static void resize(Stage curStage) {
		curStage.setMinHeight(0);
		curStage.setMinWidth(0);
		curStage.sizeToScene();
		curStage.setMinHeight(curStage.getHeight());
		curStage.setMinWidth(curStage.getWidth());
	}

	public static void setTitle(String name) {
		stage.setTitle(name);
	}
}
