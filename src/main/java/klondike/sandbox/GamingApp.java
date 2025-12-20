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
        GameEngine engine = new GameEngine(true);
        engine.dealNewGame();

        BoardView boardView = new BoardView(engine); // pane
        SideMenu menu = new SideMenu(); // vbox        

        StackPane root = new StackPane(boardView, menu); // top level root node, menu second child
        StackPane.setAlignment(menu, Pos.TOP_RIGHT);

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
