package klondike.sandbox;
import klondike.*;

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.*;
import javafx.scene.layout.*;

public class GamingApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane(); // top level root node 

        HBox foundations = new HBox(40);
        foundations.getChildren().addAll(
            new Label("F1"),
            new Label("F2"),
            new Label("F3"),
            new Label("F4")
        );

        HBox tableaus = new HBox(40);
        tableaus.getChildren().addAll(
            new Label("T1"),
            new Label("T2"),
            new Label("T3"),
            new Label("T4"),
            new Label("T5"),
            new Label("T6"),
            new Label("T7")
        );

        HBox stockWaste = new HBox(60);
        stockWaste.getChildren().addAll(
            new Label("Stock"),
            new Label("Waste")
        );

        root.setTop(foundations);
        root.setCenter(tableaus);
        root.setBottom(stockWaste);

        Scene scene = new Scene(root, 1200, 800, Color.BISQUE);
        root.setStyle("-fx-background-color: transparent;");
        primaryStage.setTitle("Sandbox Solitaire");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
