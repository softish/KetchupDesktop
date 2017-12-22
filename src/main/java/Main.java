import controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;
import model.TimerModel;
import view.KetchupDesktopView;
import view.SceneManager;
import view.SceneName;
import view.SignInView;

/**
 * Bootstraps the application.
 */
public class Main extends Application {

    private static int timeTargetMillis = 20 * 60 * 1000;
    private static boolean devMode;

    public static void main(String[] args) {
        parseArgs(args);
        launch(args);
    }

    private static void parseArgs(String[] args) {
        for (String arg : args) {
            if (arg.equalsIgnoreCase("dev")) {
                enableDevelopmentMode();
            } else {
                setTarget(arg);
            }
        }
    }

    private static void enableDevelopmentMode() {
        devMode = true;
    }

    private static void setTarget(String arg) {
        try {
            timeTargetMillis = Integer.parseInt(arg);
        } catch (NumberFormatException e) {
            System.err.println("Argument must be an integer value");
        }
    }

    /**
     * Starts the application components.
     *
     * @param primaryStage arguments supplied to the application by JavaFX
     */
    @Override
    public void start(Stage primaryStage) {
        SceneManager.getInstance().setPrimaryStage(primaryStage);
        KetchupDesktopView ketchupDesktopView = new KetchupDesktopView();
        SignInView signInView = new SignInView();
        SceneManager.getInstance().addScene(SceneName.Ketchup, ketchupDesktopView);
        SceneManager.getInstance().addScene(SceneName.Auth, signInView);
        TimerModel timerModel = new TimerModel(timeTargetMillis);
        Controller controller = new Controller(ketchupDesktopView, signInView, timerModel, devMode);
        ketchupDesktopView.addEventHandlers(controller);
        signInView.addEventHandlers(controller);

        primaryStage.setTitle("KetchupDesktop");
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(event -> {
            event.consume();
            controller.exitApplication();
            primaryStage.close();
        });
    }
}
