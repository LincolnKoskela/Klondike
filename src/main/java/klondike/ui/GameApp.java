package klondike.ui;
import klondike.*;

import java.util.ArrayList;
import java.util.List;
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

    // ---------- fields -----------------
    private static List<CardView> selectedStack = new ArrayList<>();


    // ---------- Start ---------------
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

        // click: move one card from stock -> waste
        stockView.setOnMouseClicked(e -> {
            Card drawn = board.getStock().draw();
            if (drawn != null) {
                board.getWaste().push(drawn); 
                stockView.redraw(); // refresh stock view
                wasteView.redraw(); // refresh waste view

            }
        });


        wasteView.setOnMouseClicked(e -> {
            
        });

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

    /**
     * This function handles clicking a card in a pile, preping it for a possible move.
     * When card is clicked, any previous selections are cleared. If the click is a tableau pile, 
     * get all the cards from that card downwards.
     * 
     * @param clicked is the CardView node the user clicked
     * @param pile represents which pile the card belongs to
     * @param index represents position in a tableau pile. 
     */
    public static void handleCardSelection(CardView clicked, Pile pile, int index) {
        for (CardView cv : selectedStack) {
            cv.setStyle(""); // remove styling, clear list
        }
        selectedStack.clear();

        if (pile instanceof Tableau) { // is the pile a tableau
            Tableau tab = (Tableau) pile;

            List<Card> stack = tab.sublist(index, tab.size());

            System.out.println("Selected Stack: " + stack);
        }
    }
    public static void main(String[] args) {
        launch(args); 
    }
}
