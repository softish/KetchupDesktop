package view;

import controller.Controller;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;

public class KetchupDesktopView extends BorderPane {

    private Button resetButton;
    private Button changeStateButton;
    private Label timerLabel;

    public KetchupDesktopView() {
        initView();
    }

    private void initView() {
        resetButton = new Button("Reset");
        changeStateButton = new Button("Start");

        timerLabel = new Label("00:00:00");
        timerLabel.setStyle("-fx-font-size: 42px; -fx-font-weight: bold;");

        FlowPane buttonsPane = new FlowPane();
        buttonsPane.getChildren().add(resetButton);
        buttonsPane.getChildren().add(changeStateButton);
        buttonsPane.setHgap(10);
        buttonsPane.setStyle("-fx-padding: 25;");
        buttonsPane.setAlignment(Pos.CENTER);

        this.setCenter(timerLabel);
        this.setBottom(buttonsPane);

        enableDarkTheme();
    }

    public void enableDarkTheme() {
        this.setStyle("-fx-background: #1E1E1E;");
        timerLabel.setStyle("-fx-font-size: 42px; -fx-font-weight: bold; -fx-fill: #DCDCDC;");
    }

    public void setTimeLabel(String text) {
        timerLabel.setText(text);
    }

    public void addEventHandlers(Controller controller) {
        changeStateButton.setOnAction(event -> controller.changeTimerState());
        resetButton.setOnAction(event -> controller.resetTimer());

        KeyCombination keyCodeCombination = new KeyCodeCombination(KeyCode.DOWN, KeyCombination.ALT_DOWN);
        this.getScene().addEventHandler(KeyEvent.KEY_RELEASED, event -> {
            if (keyCodeCombination.match(event)) {
                controller.changeTimerState();
            }
        });
    }

    public void setChangeStateButtonText(String text) {
        changeStateButton.setText(text);
    }

    public void updateTimeLabel(String timeLeft) {
        Platform.runLater(() -> setTimeLabel(timeLeft));
    }

    public void displayTimeOut() {
        Platform.runLater(() -> showTimeOutAlert());
    }

    private void showTimeOutAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Time out");
        alert.setHeaderText(null);
        alert.setContentText("Time out");

        alert.showAndWait();
    }

    public void enableChangeStateButton() {
        changeStateButton.setDisable(false);
    }

    public void disableChangeStateButton() {
        changeStateButton.setDisable(true);
    }

    public void enableResetButton() {
        resetButton.setDisable(false);
    }

    public void disableResetButton() {
        resetButton.setDisable(true);
    }
}
