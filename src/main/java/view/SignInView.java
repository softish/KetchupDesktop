package view;

import controller.Controller;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import java.util.Optional;

/**
 * This class represents the sign in view.
 */
public class SignInView extends BorderPane {

    private Button signInButton;
    private Button registerButton;

    public SignInView() {
        initView();
    }

    private void initView() {
        signInButton = new Button("Sign in");
        registerButton = new Button("Register");

        this.setCenter(getUserButtonsPane());
        this.setStyle("-fx-background: #1E1E1E;");
    }

    private FlowPane getUserButtonsPane() {
        FlowPane userButtonsPane = new FlowPane();
        userButtonsPane.getChildren().add(signInButton);
        userButtonsPane.getChildren().add(registerButton);
        userButtonsPane.setAlignment(Pos.CENTER);
        userButtonsPane.setHgap(10);
        userButtonsPane.setPadding(new Insets(25, 25, 25, 25));
        return userButtonsPane;
    }

    /**
     * Binds handler methods to the buttons in the views.
     *
     * @param controller the controller where the handlers are defined
     */
    public void addEventHandlers(Controller controller) {
        signInButton.setOnAction(event -> controller.loginHandler());
        registerButton.setOnAction(event -> controller.registerHandler());
    }

    public Optional<Pair<String, String>> showLoginDialog() {
        return createDialogWithTwoField("Sign In Dialog", "Login").showAndWait();
    }

    public Optional<Pair<String, String>> showRegisterDialog() {
        return createDialogWithTwoField("Register Dialog", "Register").showAndWait();
    }

    private Dialog<Pair<String, String>> createDialogWithTwoField(String dialogTitle, String buttonText) {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle(dialogTitle);
        dialog.setHeaderText(null);
        dialog.getDialogPane().setStyle("-fx-background: #1E1E1E; -fx-fill: #DCDCDC;");

        ButtonType confirmationButton = new ButtonType(buttonText, ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(confirmationButton, ButtonType.CANCEL);

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

        Node confirmationButtonInPane = dialog.getDialogPane().lookupButton(confirmationButton);
        confirmationButtonInPane.setDisable(true);

        username.textProperty().addListener((observable, oldValue, newValue) -> confirmationButtonInPane.setDisable(newValue.trim().isEmpty() || password.getText().trim().isEmpty()));
        password.textProperty().addListener((observable, oldValue, newValue) -> confirmationButtonInPane.setDisable(newValue.trim().isEmpty() || username.getText().trim().isEmpty()));

        username.requestFocus();

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == confirmationButton) {
                return new Pair<>(username.getText(), password.getText());
            }
            return null;
        });

        return dialog;
    }
}
