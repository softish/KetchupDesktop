package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

public class KetchupDesktopView extends BorderPane {

    private Button resetButton;
    private Button changeStateButton;

    public KetchupDesktopView() {
        initView();
    }

    private void initView() {
        resetButton = new Button("Reset");
        changeStateButton = new Button("Start");

        Label label = new Label("00:00:00");
        label.setStyle("-fx-font-size: 42px; -fx-font-weight: bold;");

        FlowPane buttonsPane = new FlowPane();
        buttonsPane.getChildren().add(resetButton);
        buttonsPane.getChildren().add(changeStateButton);
        buttonsPane.setHgap(10);
        buttonsPane.setAlignment(Pos.CENTER);

        this.setCenter(label);
        this.setBottom(buttonsPane);
    }
}
