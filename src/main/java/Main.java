import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.KetchupDesktopView;

/**
 * Bootstraps the application.
 */
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Starts the application components.
     *
     * @param primaryStage arguments supplied to the application by JavaFX
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        KetchupDesktopView ketchupDesktopView = new KetchupDesktopView();
        Scene scene = new Scene(ketchupDesktopView, 480, 640);

        primaryStage.setTitle("KetchupDesktop");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
