package klondike.ui;

import javafx.scene.layout.StackPane;
import javafx.stage.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;

public class StatView extends StackPane {

    private Button back;

    public StatView (Runnable onBack) {
        back = new Button("Back to Menu");
        styleButton();
        setStyle("-fx-background-color: #4097be");

        getChildren().add(back);
        back.setOnAction(e -> onBack.run());
    }

    private void styleButton() {
        back.setStyle(
            "-fx-background-color: #2c17e2;" + 
            "-fx-border-color: #555;" +
            "-fx-border-width: 1;" + 
            "-fx-font-size: 12px;" + 
            "-fx-font-weight: bold;" + 
            "-fx-text-fill: white;"
        );

        back.setOnMouseEntered(e -> back.setStyle(
            "-fx-background-color: #333333;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 12px;"
        ));

        back.setOnMouseExited(e -> back.setStyle(
            "-fx-background-color: #2c17e2;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 12px;"
        ));

        StackPane.setAlignment(back, Pos.TOP_LEFT);
        StackPane.setMargin(back, new Insets(20, 0, 0, 20));
    }
    
}
