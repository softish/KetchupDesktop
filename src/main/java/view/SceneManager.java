package view;

import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by softish on 2017-10-16.
 */
public class SceneManager {

    private double width = 360;
    private double height = 640;

    private Map<String, Scene> sceneMap;
    private Stage primaryStage;
    private static SceneManager sceneManager;

    private SceneManager() {
        sceneMap = new HashMap<>();
    }

    public static SceneManager getInstance() {
        return sceneManager == null ? sceneManager = new SceneManager() : sceneManager;
    }

    private void initWindowDimensions() {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        width = primaryScreenBounds.getHeight() / 3;
        height = primaryScreenBounds.getWidth() / 3;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void activateScene(String sceneName) {
        primaryStage.setScene(sceneMap.get(sceneName));
        primaryStage.show();
    }

    public void addScene(String sceneName, Parent parent) {
        sceneMap.put(sceneName, new Scene(parent, width, height));
    }
}
