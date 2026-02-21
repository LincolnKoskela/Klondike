package klondike.ui;

import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * VBOX on the MainMenuView
 */
public class MainMenuList extends VBox {
    private Button startGame;
    private Button settings;
    private Button stats;
    private Button howToPlay;
    private Button quit;

    public MainMenuList(Runnable onStart, Runnable onSettings,
        Runnable onStats, Runnable onHowTo, Runnable onQuit) {
        
            this.startGame = new Button("Start Game");
            this.settings = new Button("Settings");
            this.stats = new Button("Career Stats");
            this.howToPlay = new Button("How to Play Klondike");
            this.quit = new Button("Quit");

            applyStyle();
            configureLayout();

            startGame.setOnAction(e -> onStart.run());
            settings.setOnAction(e -> onSettings.run());
            stats.setOnAction(e -> onStats.run());
            howToPlay.setOnAction(e -> onHowTo.run());
            quit.setOnAction(e -> onQuit.run());
    }

    private void applyStyle() {
        setStyle(
            "-fx-background-color: #262626;" + 
            "-fx-border-color: #555;" + 
            "-fx-border-width: 1;"
        );

        for (Button b : new Button[] {startGame, settings, stats, howToPlay, quit}) {
            b.setMaxWidth(Double.MAX_VALUE);
            b.setStyle(
                "-fx-background-color: #262626;" +
                "-fx-font-size: 12px;" + 
                "-fx-font-weight: bold;" + 
                "-fx-text-fill: white;"
            );

            b.setOnMouseEntered(e -> b.setStyle(
                "-fx-background-color: #9b3c3cff;" + 
                "-fx-font-weight: bold;" +
                "-fx-text-fill: white;" + 
                "-fx-font-size: 12px;" 
            ));

            b.setOnMouseExited(e -> b.setStyle(
                "-fx-background-color: #262626;" + 
                "-fx-text-fill: white;" + 
                "-fx-font-size: 12px;"
            ));
        }
    }

    private void configureLayout() {
        setSpacing(6); // verticle space
        setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        setPrefWidth(160);
        getChildren().addAll(startGame, settings, stats, howToPlay, quit);
    }
}
