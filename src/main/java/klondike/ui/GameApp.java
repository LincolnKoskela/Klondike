package klondike.ui;

import javafx.application.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.*;

public class GameApp extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane(); // root for top level node
        Scene scene = new Scene(root, 1200, 800, Color.DARKGREEN);
        root.setStyle("-fx-background-color: transparent;");

        // test cardSlot 
        root.getChildren().addAll(
            new CardSlot("Waste")
        );

        primaryStage.setTitle("Klondike Solitaire");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
