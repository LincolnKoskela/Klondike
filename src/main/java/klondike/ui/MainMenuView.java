package klondike.ui;

import java.util.Stack;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import klondike.GameEngine;

public class MainMenuView {
    
    private StackPane root;

    public MainMenuView(Stage stage) {
        root = new StackPane();

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

            root.getChildren().add(mainMenu);
    }   

    public StackPane getRoot() {
        return root;
    }
}
