package view;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by softish on 2017-10-16.
 */
public class SceneManager {

    private static final int width = 360;
    private static final int height = 640;

    private Stage primaryStage;
    private static SceneManager sceneManager;

    private SceneManager() {
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

    public void activateScene(Parent parent) {
        Scene scene = new Scene(parent, width, height);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
