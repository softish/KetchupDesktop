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

    public static void main(String[] args) {
        setTargetViaArg(args);
        launch(args);
    }

    private static void setTargetViaArg(String[] args) {
        if(args.length == 1) {
            try {
                timeTargetMillis = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.err.println("Argument must be an integer value");
            }
        }
    }

    /**
     * Starts the application components.
     *
     * @param primaryStage arguments supplied to the application by JavaFX
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        SceneManager.getInstance().setPrimaryStage(primaryStage);
        KetchupDesktopView ketchupDesktopView = new KetchupDesktopView();
        SignInView signInView = new SignInView();
        SceneManager.getInstance().addScene(SceneName.Ketchup, ketchupDesktopView);
        SceneManager.getInstance().addScene(SceneName.Auth, signInView);
        TimerModel timerModel = new TimerModel(timeTargetMillis);
        Controller controller = new Controller(ketchupDesktopView, signInView, timerModel);
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
