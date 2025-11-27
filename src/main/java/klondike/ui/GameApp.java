package klondike.ui;
import klondike.*;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

/**
 * JavaFX Launcher
 */
public class GameApp extends Application {

    private static CardView selectedCard = null;

    @Override
    public void start(Stage primaryStage) {
        // Root layout
        BorderPane root = new BorderPane();

        GameEngine engine = new GameEngine(true);
        engine.dealNewGame();
        Board board = engine.getBoard();

        // ----- StockWaste Pile *****
        HBox stockWaste = new HBox(50);
        PileView stockView = new PileView(board.getStock(), 0);
        PileView wasteView = new PileView(board.getWaste(), 0);

        StackPane stockCell = new StackPane(new CardSlot("STOCK"), stockView);
        StackPane wasteCell = new StackPane(new CardSlot("WASTE"), wasteView);

        stockCell.setAlignment(Pos.CENTER);
        wasteCell.setAlignment(Pos.CENTER);
        stockWaste.getChildren().addAll(stockCell, wasteCell);


        // ----- Foundations -----
        HBox foundationRow = new HBox(50); 
        foundationRow.setAlignment(Pos.TOP_LEFT);
        
        for (Card.Suit suit : Card.Suit.values()) {
            Foundation f = board.getFoundation(suit);
            PileView view = new PileView(f, 0); 
            
            CardSlot slot = new CardSlot(suit.name());

            StackPane cell = new StackPane(slot, view);
            cell.setAlignment(Pos.TOP_CENTER);
            foundationRow.getChildren().add(cell);
        }

        // ----- Tableaus -------
        HBox tableauRow = new HBox(40);
        tableauRow.setAlignment(Pos.TOP_CENTER);
        tableauRow.setPadding(new Insets(120, 0, 0, 0));

        for (int col = 1; col <= 7; col++) {
            Tableau tab = board.getTableau(col);
            PileView view = new PileView(tab, 20);
            tableauRow.getChildren().add(view);
        }

        root.setTop(foundationRow);
        root.setBottom(stockWaste);
        root.setCenter(tableauRow);

        // Scene + Stage
        Scene scene = new Scene(root, 1200, 800);
        primaryStage.setTitle("Klondike Layout");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void handleCardSelection(CardView clicked) {
        // if clicking same card twice -> deselect
        if (selectedCard == clicked) {
            clicked.setStyle("");
            selectedCard = null;
            return;
        }

        // if another card was selected -> clear old
        if (selectedCard != null) {
            selectedCard.setStyle("");
        }

        // select new card
        clicked.setStyle("-fx-border-color: gold; -fx-border-width: 3;");
        selectedCard = clicked;
    }
    public static void main(String[] args) {
        launch(args); // inside application class tht sets up program as javafx application
    }
}
