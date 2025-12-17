package klondike.sandbox;
import klondike.*;
import klondike.Card.Rank;
import klondike.Card.Suit;
import klondike.ui.CardView;

import java.util.Stack;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.*;
import javafx.scene.layout.*;

public class GamingApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        BorderPane board = new BorderPane();

        StackPane root = new StackPane(board); // top level root node 

        Button home = new Button("Home");
        Button profile = new Button("Profile");
        Button settings = new Button("Settings");
        Button logout = new Button("Logout");

        /**
         * Style the buttons
         * https://openjfx.io/javadoc/21/javafx.graphics/javafx/scene/doc-files/cssref.html
         */
        for (Button b : new Button[]{home, profile, settings, logout}) {
            b.setMaxWidth(Double.MAX_VALUE);
            b.setStyle(
                "-fx-background-color: #262626;" + 
                "-fx-text-fill: white;" + 
                "-fx-text-font-size: 12px;"
            );

            b.setOnMouseEntered(e -> b.setStyle(
                "-fx-background-color: #333333;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 12px;" +
                "-fx-padding: 10 16;" +
                "-fx-background-radius: 4;"
            ));

            b.setOnMouseExited(e -> b.setStyle(
                "-fx-background-color: #262626;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 12px;" +
                "-fx-padding: 10 16;" +
                "-fx-background-radius: 4;"
            ));
        }
        /*-------------------------------------------------------- */

        // ----------------------- menu --------------------------
        VBox menu = new VBox(6, home, profile, settings, logout);
        menu.setStyle(
            "-fx-background-color: #262626;" + 
            "-fx-border-color: #555;" + 
            "-fx-border-width: 1;"
        );
        menu.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        menu.setPrefWidth(160);
        root.getChildren().add(menu);
        StackPane.setAlignment(menu, Pos.TOP_RIGHT);
        // -----------------------------------------------------------

        Card test = new Card(Rank.ACE, Suit.SPADES);
        test.flip();
        StackPane card = new StackPane(new CardView(test));
        root.getChildren().add(card);
        card.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);


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
