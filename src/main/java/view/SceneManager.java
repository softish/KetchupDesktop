package view;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.EnumMap;
import java.util.Map;

/**
 * This class manages the views of the application.
 */
public class SceneManager {

    private static final double WIDTH = 360;
    private static final double HEIGHT = 640;

    private final Map<SceneName, Scene> sceneMap;
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
