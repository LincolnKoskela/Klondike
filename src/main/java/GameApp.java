import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;


public class GameApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Root layout
        BorderPane root = new BorderPane();

        // Foundations row (top)
        HBox foundations = new HBox(50); // @param - spacing between children
        foundations.getChildren().addAll(
            new Label("F1"),
            new Label("F2"),
            new Label("F3"),
            new Label("F4")
        );

        // Tableau row (center)
        HBox tableau = new HBox(80); // add more spacing for columns
        tableau.getChildren().addAll(
            new Label("T1"),
            new Label("T2"),
            new Label("T3"),
            new Label("T4"),
            new Label("T5"),
            new Label("T6"),
            new Label("T7")
        );

        HBox stockWaste = new HBox(50);
        stockWaste.getChildren().addAll(
            new Label("Stock"),
            new Label("Waste")
        );

        root.setTop(foundations);
        root.setCenter(tableau);
        root.setBottom(stockWaste);

        // Scene + Stage
        Scene scene = new Scene(root, 1200, 800);
        primaryStage.setTitle("Klondike Layout");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args); // inside application class tht sets up program as javafx application
    }
}
