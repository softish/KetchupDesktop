package view;

import controller.Controller;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.util.Pair;

import java.util.Optional;

public class KetchupDesktopView extends BorderPane {

    private Button resetButton;
    private Button changeStateButton;
    private Button signInButton;
    private Label timerLabel;

    public KetchupDesktopView() {
        initView();
    }

    private void initView() {
        resetButton = new Button("Reset");
        changeStateButton = new Button("Start");
        signInButton = new Button("Sign in");

        timerLabel = new Label("00:00:00");
        timerLabel.setStyle("-fx-font-size: 42px; -fx-font-weight: bold;");

        FlowPane timerButtonsPane = new FlowPane();
        timerButtonsPane.getChildren().add(resetButton);
        timerButtonsPane.getChildren().add(changeStateButton);
        timerButtonsPane.setHgap(10);
        timerButtonsPane.setPadding(new Insets(0, 0, 25, 0));
        timerButtonsPane.setAlignment(Pos.CENTER);

        FlowPane userButtonsPane = new FlowPane();
        userButtonsPane.getChildren().add(signInButton);
        userButtonsPane.setAlignment(Pos.TOP_RIGHT);
        userButtonsPane.setPadding(new Insets(25, 25, 25, 25));

        this.setCenter(timerLabel);
        this.setBottom(timerButtonsPane);
        this.setTop(userButtonsPane);

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
        signInButton.setOnAction(event -> controller.loginHandler());

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
        Platform.runLater(() -> showAlert("Time out Dialog", "Time out!"));
    }

    public void showErrorDialog(String title, String message) {
        Platform.runLater(() -> showAlert(title, message));
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

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

    public Optional<Pair<String, String>> showLoginDialog() {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Sign In Dialog");
        dialog.setHeaderText(null);

        ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField username = new TextField();
        username.setPromptText("Username");
        PasswordField password = new PasswordField();
        password.setPromptText("Password");

        grid.add(new Label("Username:"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(password, 1, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(username.getText(), password.getText());
            }
            return null;
        });

        return dialog.showAndWait();
    }
}
