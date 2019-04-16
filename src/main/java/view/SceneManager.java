package view;

import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.EnumMap;

/**
 * This class manages the views of the application.
 */
public class SceneManager {

    private double width = 360;
    private double height = 640;

    private final EnumMap<SceneName, Scene> sceneMap;
    private Stage primaryStage;
    private static SceneManager sceneManager;

    private SceneManager() {
        sceneMap = new EnumMap<>(SceneName.class);
    }

    public static SceneManager getInstance() {
        if (sceneManager == null) {
            sceneManager = new SceneManager();
        }
        return sceneManager;
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

    /**
     * Sets the currently active scene based on the provided name
     *
     * @param sceneName the scene to show
     */
    public void activateScene(SceneName sceneName) {
        primaryStage.setScene(sceneMap.get(sceneName));
        primaryStage.show();
    }

    /**
     * Initialization of scenes.
     * Adds the provided view to the set of displayable scenes.
     *
     * @param sceneName the name of the scene to add
     * @param parent    the view that represents the scene
     */
    public void addScene(SceneName sceneName, Parent parent) {
        sceneMap.put(sceneName, new Scene(parent, width, height));
    }
}
