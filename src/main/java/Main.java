import controller.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.TimerModel;
import view.KetchupDesktopView;

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
        KetchupDesktopView ketchupDesktopView = new KetchupDesktopView();
        Scene scene = new Scene(ketchupDesktopView, 360, 640);
        TimerModel timerModel = new TimerModel(timeTargetMillis);
        Controller controller = new Controller(ketchupDesktopView, timerModel);
        ketchupDesktopView.addEventHandlers(controller);

        primaryStage.setTitle("KetchupDesktop");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> {
            event.consume();
            controller.exitApplication();
            primaryStage.close();
        });
    }
}
