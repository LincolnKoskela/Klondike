package klondike.ui;

import java.util.Stack;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import klondike.GameEngine;

public class MainMenuView {
    
    private StackPane root;
    private Label title;

    public MainMenuView(Stage stage) {
        root = new StackPane();

        title = new Label("Klondike Solitaire");
        styleTitle();

        MainMenuList mainMenu = new MainMenuList(
            () -> {
                GameEngine engine = new GameEngine(true);
                engine.dealNewGame();
                BoardView board = new BoardView(engine);
                SideMenu sideMenu = new SideMenu(
                    () -> board.doUndo(),
                    () -> board.doNewGame(),
                    () -> board.pauseGame()
                );

                StackPane gameRoot = new StackPane(board, sideMenu);
                StackPane.setAlignment(sideMenu, Pos.TOP_RIGHT);
                gameRoot.setStyle("-fx-background-color: darkgreen;");

                stage.getScene().setRoot(gameRoot);

            },
            () -> {
                SettingsView settingsView = new SettingsView(stage);
                stage.getScene().setRoot(settingsView);
            },
            
            () -> {
                StatView statView = new StatView(stage);
                stage.getScene().setRoot(statView);
            },
            
            () -> {
                HowToPlayView howTo = new HowToPlayView(stage);
                stage.getScene().setRoot(howTo);
            }, 
            
            () -> 
            {
                stage.close();
            });

            StackPane.setAlignment(title, Pos.TOP_CENTER);

            root.setStyle("-fx-background-color: Darkgreen;");
            root.getChildren().addAll(mainMenu, title);
    }   

    public StackPane getRoot() {
        return root;
    }

    private void styleTitle() {
        title.setStyle(
            "-fx-font-size: 90px;" + 
            "-fx-font-weight: bold;" + 
            "-fx-text-fill: white;"
        );
    }
}
