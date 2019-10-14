package application;

import java.util.HashMap;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class ScreenController {
    private HashMap<String, Pane> screenMap = new HashMap<>();
    private Scene main;
    private String curActivated;
    public boolean leftOverview;

    public ScreenController(Scene main) {
        this.main = main;
        curActivated = "";
        leftOverview = false;
    }

    protected void addScreen(String name, Pane pane){
         screenMap.put(name, pane);
    }

    protected void removeScreen(String name){
        screenMap.remove(name);
    }
    
    protected boolean isActivated(String name) {
    	if(name.equals(curActivated))
    		return true;
    	return false;
    }

    protected void activate(String name){
    	if(name.equals("overview")) {
    		leftOverview = true;
    		((CntrlOverview)Main.cntrlOverview.getController()).cbChanged();
    	} else if(name.equals("championview")) {
    		((CntrlChampionView)Main.cntrlChampview.getController()).startAnimation(((CntrlChampionView)Main.cntrlChampview.getController()).gridshit);
    	}
    	Main.setTitle(name);
        main.setRoot(screenMap.get(name));
        curActivated = name;
        Main.resize(Main.stage);
    }
    
    protected Pane getPane(String name) {
    	return screenMap.get(name);
    }
}
