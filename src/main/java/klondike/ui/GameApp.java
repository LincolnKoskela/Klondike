package klondike.ui;
import klondike.*;
import klondike.Card.Rank;
import klondike.Card.Suit;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;

/**
 * JavaFX Launcher
 */
public class GameApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Root layout
        BorderPane root = new BorderPane();

        // Foundations row (top)
        HBox foundations = new HBox(50); // @param - spacing between children
        foundations.getChildren().addAll(
            new CardSlot("F1"),
            new CardSlot("F2"),
            new CardSlot("F3"),
            new CardSlot("F4")
        );

        // Tableau row (center)
        HBox tableau = new HBox(80); // add more spacing for columns
        tableau.getChildren().addAll(
            new CardSlot("T1"),
            new CardSlot("T2"),
            new CardSlot("T3"),
            new CardSlot("T4"),
            new CardSlot("T5"),
            new CardSlot("T6"),
            new CardSlot("T7")
        );

        HBox stockWaste = new HBox(50);
        stockWaste.getChildren().addAll(
            new Label("Stock"),
            new Label("Waste")
        );

        root.setTop(foundations);
        root.setCenter(tableau);
        root.setBottom(stockWaste);


        /// Test Card View ///
        Card test = new Card(Rank.ACE, Suit.HEARTS);
        test.flip();
        root.setCenter(new CardView(test));

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
