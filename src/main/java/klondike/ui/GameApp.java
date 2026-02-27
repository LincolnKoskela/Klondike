package klondike.ui;

import javafx.application.*;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.*;

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
