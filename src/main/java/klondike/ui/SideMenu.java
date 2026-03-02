package klondike.ui;

import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;

public class SideMenu extends VBox {
    private Button undo;
    private Button restartGame;
    private Button pause;
    private Button exit;

    public SideMenu(Runnable onUndo, Runnable onNewGame, Runnable onPause, 
        Runnable onExit) {
        this.undo = new Button("UNDO");
        this.restartGame = new Button("New Game");
        this.pause = new Button("Pause Game");
        this.exit = new Button("Exit Game");

        applyStyle();
        configureLayout();

        undo.setOnAction(e -> onUndo.run());
        restartGame.setOnAction(e -> onNewGame.run());
        pause.setOnAction(e -> onPause.run());
        exit.setOnAction(e -> onExit.run());
        
    }
    
    /**
     * Style the buttons
     * https://openjfx.io/javadoc/21/javafx.graphics/javafx/scene/doc-files/cssref.html
     */
    private void applyStyle() {
        setStyle(
            "-fx-background-color: #262626;" + 
            "-fx-border-color: #555;" + 
            "-fx-border-width: 1;"
        );

        for (Button b : new Button[] {undo, restartGame, pause, exit}) {
            b.setMaxWidth(Double.MAX_VALUE);
            b.setStyle(
                "-fx-background-color: #262626;" + 
                "-fx-font-size: 12px;" + 
                "-fx-font-weight: bold;" + 
                "-fx-text-fill: white;"
            );

            b.setOnMouseEntered(e -> b.setStyle(
                "-fx-background-color: rgb(9, 107, 9);" + 
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
        setSpacing(6);
        setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        setPrefWidth(160);
        getChildren().addAll(undo, restartGame, pause, exit);
    }
}
