package klondike.ui;

import javafx.application.*;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.*;
import klondike.GameEngine;

public class GameApp extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        GameEngine engine = new GameEngine(true);
        engine.dealNewGame();

        BoardView board = new BoardView(engine);

        StackPane root = new StackPane(board);
        Scene scene = new Scene(root, 1200, 800, Color.DARKGREEN);
        root.setStyle("-fx-background-color: transparent;");

        primaryStage.setTitle("Klondike Solitaire");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
