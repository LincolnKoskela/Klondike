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
