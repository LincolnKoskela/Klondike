package klondike.sandbox;
import klondike.*;

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.control.ListView;

public class GamingApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane(); // top level root node 

        // ----------------sidebar menu custom----------------
        ListView<String> sideMenu = new ListView<>();
        sideMenu.getItems().addAll("Home", "Profile", "Settings", "Logout");
        sideMenu.setPrefSize(140, 120);
        sideMenu.setFixedCellSize(24); // each row height

        sideMenu.setStyle(
            "-fx-background-color: #262626;" + 
            "-fx-control-inner-background: #262626;" + 
            "-fx-border-color: #172a94b6;" + 
            "-fx-border-width: 1;" + 
            "-fx-padding: 0;"
        );

        // ---------------------------------------------------------



        HBox foundations = new HBox(40);
        foundations.getChildren().addAll(
            new CardSlot("F1"),
            new CardSlot("F2"),
            new CardSlot("F3"),
            new CardSlot("F4")
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
        root.setRight(sideMenu);

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
