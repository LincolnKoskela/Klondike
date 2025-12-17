package klondike.sandbox;
import klondike.*;
import klondike.Card.Rank;
import klondike.Card.Suit;
import klondike.ui.CardView;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.stage.*;
import javafx.scene.layout.*;

public class GamingApp extends Application {
    

    @Override
    public void start(Stage primaryStage) {
        BorderPane board = new BorderPane();
        StackPane root = new StackPane(board); // top level root node 
        SideMenu menu = new SideMenu(); // vbox
        
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
