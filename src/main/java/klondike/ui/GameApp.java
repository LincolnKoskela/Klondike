package klondike.ui;

import javafx.application.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.*;
import klondike.GameEngine;
import javafx.scene.control.Button;

public class GameApp extends Application {
    
    @Override
    public void start(Stage primaryStage) {

        MainMenuView menu = new MainMenuView(primaryStage);

        Scene scene = new Scene(menu.getRoot(), 1200, 800, Color.DARKGREEN);

        primaryStage.setTitle("Klondike Solitaire");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
