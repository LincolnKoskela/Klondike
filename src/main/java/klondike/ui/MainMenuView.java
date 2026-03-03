package klondike.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import klondike.GameEngine;

public class MainMenuView extends StackPane {
    
    private Label title;

    public MainMenuView(Stage stage) {

        title = new Label("Klondike Solitaire");
        styleTitle();

        MainMenuList mainMenu = new MainMenuList(
            // start game
            () -> {
                GameEngine engine = new GameEngine(true);
                engine.dealNewGame();
                BoardView board = new BoardView(engine);
                SideMenu sideMenu = new SideMenu(
                    () -> board.doUndo(),
                    () -> board.doNewGame(),
                    () -> board.pauseGame(), 
                    () -> stage.getScene().setRoot(this)
                );

                // if (board.isPaused()) {
                //     sideMenu.disableNewGameButton();
                // }

                StackPane gameRoot = new StackPane(board, sideMenu);
                StackPane.setAlignment(sideMenu, Pos.TOP_RIGHT);
                gameRoot.setStyle("-fx-background-color: darkgreen;");

                stage.getScene().setRoot(gameRoot);

            },

            // setting view
            () -> {
                SettingsView settingsView = new SettingsView(() -> stage.getScene().setRoot(this));
                stage.getScene().setRoot(settingsView);
            },
            
            // stats
            () -> {
                StatView statView = new StatView(() -> stage.getScene().setRoot(this));
                stage.getScene().setRoot(statView);
            },
            
            // how to play display
            () -> {
                HowToPlayView howTo = new HowToPlayView(() -> stage.getScene().setRoot(this));
                stage.getScene().setRoot(howTo);
            }, 
            
            // Exit application
            () -> 
            {
                stage.close();
            });

        StackPane container = new StackPane();
        VBox menulayout = new VBox(50);
        menulayout.setAlignment(Pos.CENTER);

        menulayout.getChildren().addAll(title, mainMenu);
        container.getChildren().add(menulayout);
        container.setPadding(new Insets(0, 0, 200, 0));

        setStyle("-fx-background-color: radial-gradient(center 50% 30%, radius 60%, #0f7a0f, #013300)");
        getChildren().addAll(container);
    }   

    private void styleTitle() {
        title.setStyle(
            "-fx-font-size: 90px;" + 
            "-fx-font-weight: bold;" + 
            "-fx-text-fill: linear-gradient(to bottom, #cfcfcf 0%, white 50%, #cfcfcf 100%);"
        );
    }

    public MainMenuView getMainMenuView() {
        return this;
    }
}
