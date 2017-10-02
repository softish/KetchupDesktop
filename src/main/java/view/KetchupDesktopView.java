package view;

import controller.Controller;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import model.Observer;

public class KetchupDesktopView extends BorderPane implements Observer {

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
        buttonsPane.setAlignment(Pos.CENTER);

        this.setCenter(timerLabel);
        this.setBottom(buttonsPane);
    }

    public void setTimeLabel(String text) {
        timerLabel.setText(text);
    }

    public void addEventHandlers(Controller controller) {
        EventHandler changeTimerStateHandler = (EventHandler<ActionEvent>) event -> {
            controller.changeTimerState();
        };

        changeStateButton.setOnAction(changeTimerStateHandler);

        EventHandler resetHandler = (EventHandler<ActionEvent>) event -> {
            controller.resetTimer();
        };

        resetButton.setOnAction(resetHandler);
    }

    @Override
    public void update(String timeLeft) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                setTimeLabel(timeLeft);
            }
        });
    }
}
