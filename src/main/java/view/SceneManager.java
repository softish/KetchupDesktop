package view;

import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Created by softish on 2017-10-16.
 */
public class SceneManager {

    private double width = 360;
    private double height = 640;

    private Stage primaryStage;
    private static SceneManager sceneManager;

    private SceneManager() {
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

    public void activateScene(Parent parent) {
        Scene scene = new Scene(parent, width, height);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
