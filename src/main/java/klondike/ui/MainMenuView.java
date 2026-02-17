package klondike.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import klondike.GameEngine;

public class MainMenuView {
    
    private StackPane root;

    public MainMenuView(Stage stage) {
        root = new StackPane();

        Button startButton = new Button("Start Game");

        startButton.setOnAction(e -> {

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
        });   

        root.getChildren().add(startButton);
    }

    public StackPane getRoot() {
        return root;
    }
}
