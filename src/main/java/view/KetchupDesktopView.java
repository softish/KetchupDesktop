package view;

import controller.Controller;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

import java.util.Optional;

/**
 * This class represents the ketchup timer view.
 */
public class KetchupDesktopView extends BorderPane {

    private static final String BACKGROUND_COLOR = "-fx-background: #1E1E1E;";

    private Button resetButton;
    private Button changeStateButton;
    private Button signOutButton;
    private Label timerLabel;
    private Button setTaskButton;
    private Label selectedTaskLabel;
    private Label currentTaskLabel;
    private Label currentUserLabel;
    private Label signedInUserLabel;

    private static final String TASK_NOT_SET = "[not set]";

    public KetchupDesktopView() {
        initView();
    }

    private void initView() {
        initGuiComponents();
        composeGuiComponentLayout();
        enableDarkTheme();
    }

    private void initGuiComponents() {
        resetButton = new Button("Reset");
        changeStateButton = new Button("Start");
        signOutButton = new Button("Sign out");
        timerLabel = new Label("00:00:00");
        setTaskButton = new Button("Set task");
        currentTaskLabel = new Label("Task: ");
        selectedTaskLabel = new Label(TASK_NOT_SET);
        selectedTaskLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        currentUserLabel = new Label("");
        signedInUserLabel = new Label("User:");
    }

    private FlowPane getTimerButtonsPane() {
        FlowPane timerButtonsPane = new FlowPane();
        timerButtonsPane.getChildren().add(resetButton);
        timerButtonsPane.getChildren().add(changeStateButton);
        timerButtonsPane.setHgap(10);
        timerButtonsPane.setPadding(new Insets(0, 0, 25, 0));
        timerButtonsPane.setAlignment(Pos.CENTER);
        return timerButtonsPane;
    }

    private FlowPane getSignOutButtonPane() {
        FlowPane signOutButtonPane = new FlowPane();
        signOutButtonPane.setPadding(new Insets(25, 25, 25, 25));
        signOutButtonPane.setHgap(10);
        signOutButtonPane.getChildren().add(signedInUserLabel);
        signOutButtonPane.getChildren().add(currentUserLabel);
        signOutButtonPane.getChildren().add(signOutButton);
        return signOutButtonPane;
    }

    private FlowPane getCenterPane() {
        FlowPane timerLabelPane = new FlowPane();
        timerLabelPane.getChildren().add(timerLabel);
        timerLabelPane.setVgap(10);
        timerLabelPane.setAlignment(Pos.CENTER);

        FlowPane taskLabelPane = new FlowPane();
        taskLabelPane.getChildren().addAll(currentTaskLabel, selectedTaskLabel);
        taskLabelPane.setVgap(10);
        taskLabelPane.setAlignment(Pos.CENTER);

        FlowPane taskPane = new FlowPane();
        taskPane.setHgap(10);
        taskPane.setVgap(10);
        taskPane.setAlignment(Pos.CENTER);
        taskPane.getChildren().add(setTaskButton);
        taskPane.getChildren().add(taskLabelPane);

        FlowPane centerPane = new FlowPane();
        centerPane.setOrientation(Orientation.VERTICAL);
        centerPane.setVgap(10);
        centerPane.setAlignment(Pos.CENTER);
        centerPane.getChildren().add(timerLabelPane);
        centerPane.getChildren().add(taskPane);
        return centerPane;
    }

    private void composeGuiComponentLayout() {
        this.setTop(getSignOutButtonPane());
        this.setCenter(getCenterPane());
        this.setBottom(getTimerButtonsPane());
    }

    private void enableDarkTheme() {
        this.setStyle(BACKGROUND_COLOR);
        timerLabel.setStyle("-fx-font-size: 42px; -fx-font-weight: bold; -fx-fill: #DCDCDC;");
    }

    public void setTimeLabel(String text) {
        timerLabel.setText(text);
    }

    public void addEventHandlers(Controller controller) {
        changeStateButton.setOnAction(event -> controller.changeTimerState());
        resetButton.setOnAction(event -> controller.resetTimer());
        signOutButton.setOnAction(event -> controller.signOutHandler());
        setTaskButton.setOnAction(event -> controller.addTaskHandler());

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
        Platform.runLater(() -> {
            Optional<String> tag = showTimeOutDialog();
            tag.ifPresent(this::updateTask);
        });
    }

    public void showErrorDialog(String title, String message) {
        Platform.runLater(() -> showAlert(title, message));
    }

    private Optional<String> showTimeOutDialog() {
        TextInputDialog dialog = new TextInputDialog(getTaskIfSet());
        dialog.setTitle("Time out");
        dialog.setHeaderText(null);
        dialog.setContentText("Time out! Next session task:");
        dialog.getDialogPane().setStyle(BACKGROUND_COLOR);

        return dialog.showAndWait();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.getDialogPane().setStyle(BACKGROUND_COLOR);

        alert.showAndWait();
    }

    public void updateTask(String task) {
        Platform.runLater(() -> setTask(task));
    }

    private void setTask(String task) {
        selectedTaskLabel.setText(task);
    }

    public String getTask() {
        return selectedTaskLabel.getText();
    }

    public boolean isTaskSet() {
        return !selectedTaskLabel.getText().equals(TASK_NOT_SET);
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

    public void enableSetTaskButton() {
        setTaskButton.setDisable(false);
    }

    public void disableSetTaskButton() {
        setTaskButton.setDisable(true);
    }

    public Optional<String> showAddTaskDialog() {
        TextInputDialog dialog = new TextInputDialog(getTaskIfSet());
        dialog.setTitle("Add task Dialog");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter task:");
        dialog.getDialogPane().setStyle(BACKGROUND_COLOR);

        return dialog.showAndWait();
    }

    private String getTaskIfSet() {
        return selectedTaskLabel.getText().equals(TASK_NOT_SET) ? "" : selectedTaskLabel.getText();
    }

    public void resetTaskLabel() {
        selectedTaskLabel.setText(TASK_NOT_SET);
    }

    public void setCurrentUser(String currentUser) {
        this.currentUserLabel.setText(currentUser);
    }
}
